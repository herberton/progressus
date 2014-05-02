package br.com.hcs.progressus.server.ejb.sb.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.UserTransaction;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.ItemMenuBORemote;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.PermissionBORemote;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.ViewBORemote;
import br.com.hcs.progressus.client.ejb.sb.dao.MenuDAOLocal;
import br.com.hcs.progressus.enumerator.EntityStatus;
import br.com.hcs.progressus.enumerator.OrderByType;
import br.com.hcs.progressus.enumerator.WhereClauseOperator;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.helper.JPQLHelper;
import br.com.hcs.progressus.server.jpa.entity.ItemMenuEntity;
import br.com.hcs.progressus.server.jpa.entity.MenuEntity;
import br.com.hcs.progressus.server.jpa.entity.PermissionEntity;
import br.com.hcs.progressus.server.jpa.entity.ViewEntity;
import br.com.hcs.progressus.to.OrderByTO;
import br.com.hcs.progressus.to.WhereClauseTO;
import br.com.hcs.progressus.to.WhereTO;

@Slf4j
@NoArgsConstructor
@Stateless
public class MenuDAO extends ProgressusDAO<MenuEntity> implements MenuDAOLocal {

	private static final long serialVersionUID = 2465531694742733110L;

	@EJB
	@Getter(AccessLevel.PRIVATE)
	private ViewBORemote viewBO;
	@EJB
	@Getter(AccessLevel.PRIVATE)
	private ItemMenuBORemote itemMenuBO;
	@EJB
	@Getter(AccessLevel.PRIVATE)
	private PermissionBORemote permissionBO;
	
	@Override
	public List<MenuEntity> selectList() throws ProgressusException {
		try {
			
			Map<String, Object> parameterMap = new HashMap<>();
			
			parameterMap.put("entityStatus", EntityStatus.ACTIVE);
	    	
	    	WhereTO where = JPQLHelper.getWhere(parameterMap);
	    	where.addClause(new WhereClauseTO("parentMenu", WhereClauseOperator.IS_NULL, true));
	    	
	    	OrderByTO orderBy = new OrderByTO("id", OrderByType.ASC);
	    	
	    	String jpql = JPQLHelper.getSelect(MenuEntity.class, where, orderBy);
			
			List<MenuEntity> moduleList = super.createTypedQuery(MenuEntity.class, parameterMap, jpql).getResultList();
			
			return this.sort(moduleList);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("selectList", e);
		}
	}

	@Override
	public List<MenuEntity> saveTreeList(List<MenuEntity> treeList) throws ProgressusException {
		
		if(CollectionHelper.isNullOrEmpty(treeList)) {
			return new ArrayList<>();
		}
		
		UserTransaction userTransaction = null;
		
		try {
			
			userTransaction = this.beginTransaction();
			
			Iterator<MenuEntity> iterator = treeList.iterator();
			for (int i = 0; iterator.hasNext(); i++) {
				treeList.set(i, this.saveTree(iterator.next()));
			}
			
			this.commitTransaction(userTransaction);
			
			return treeList;
			
		} catch (ProgressusException pe) {
			this.rollbackTransaction(userTransaction);
			throw pe;
		} catch (Exception e) {
			this.rollbackTransaction(userTransaction);
			throw new UnableToCompleteOperationException("saveTreeList", e);
		}
	}
	
	
	private MenuEntity saveTree(MenuEntity menu) throws ProgressusException {
		
		try {
			
			List<MenuEntity> childMenuList = menu.getChildMenuList();
			menu.setChildMenuList(null);
			
			List<ItemMenuEntity> itemMenuList = menu.getItemMenuList();
			menu.setItemMenuList(null);
			
			menu = this.insertOrSelectEntity(menu, false);
			
			menu.setChildMenuList(null);
			menu.setItemMenuList(null);
			
			menu = this.updateEntity(menu, false);
			
			for (MenuEntity childMenu : childMenuList) {
				childMenu.setParentMenu(null);
				menu.addChildMenu(this.saveTree(childMenu));
			}
			
			for (ItemMenuEntity itemMenu : itemMenuList) {
				
				itemMenu.setParentMenu(null);
				
				List<ViewEntity> childViewList = itemMenu.getChildViewList();
				itemMenu.setChildViewList(null);
				
				List<PermissionEntity> permissionList = itemMenu.getPermissionList();
				itemMenu.setPermissionList(null);
				
				itemMenu = this.insertOrSelectEntity(itemMenu, false);
				
				itemMenu.setChildViewList(null);
				itemMenu.setPermissionList(null);
				
				itemMenu = this.updateEntity(itemMenu, false);
				
				for (ViewEntity childView : childViewList) {
					childView.setParentView(null);
					itemMenu.addChildView(this.saveView(childView));
				}
				
				for (PermissionEntity permission : permissionList) {
					permission.setView(null);
					itemMenu.addPermission(this.insertOrSelectEntity(permission, false));
				}
				
				menu.addItemMenu(itemMenu);
				
				itemMenu = this.updateEntity(itemMenu, false);
				
			}
					
			return this.update(menu, false);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("selectList", e);
		}
	}
	
	private ViewEntity saveView(ViewEntity view) throws ProgressusException {
		
		try {
			
			List<ViewEntity> childViewList = view.getChildViewList();
			view.setChildViewList(null);
			
			List<PermissionEntity> permissionList = view.getPermissionList();
			view.setPermissionList(null);
			
			view = this.insertOrSelectEntity(view, false);
			
			view.setChildViewList(null);
			view.setPermissionList(null);
			
			view = this.updateEntity(view,  false);
			
			for (ViewEntity child : childViewList) {
				child.setParentView(null);
				view.addChildView(this.saveView(child));
			}
			
			for (PermissionEntity permission : permissionList) {
				permission.setView(null);
				view.addPermission(this.insertOrSelectEntity(permission, false));
			}
			
			return this.updateEntity(view, false);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("saveTreeList", e);
		}
	}
	

	private List<MenuEntity> sort(List<MenuEntity> menuList) {
		try {
			Collections.sort((List<MenuEntity>)menuList);
			for (MenuEntity menu : menuList) {
				
				Map<String, Object> parameterMap = new HashMap<>();
				parameterMap.put("parentMenu.id", menu.getId());
				
				menu.setChildMenuList(this.getBO().selectList(parameterMap));
				menu.setItemMenuList(this.getItemMenuBO().selectList(parameterMap));
				
				Collections.sort((List<ItemMenuEntity>)menu.getItemMenuList());
				
				for (ItemMenuEntity item : menu.getItemMenuList()) {
					this.sort(item);
				}
				this.sort((List<MenuEntity>)menu.getChildMenuList());
			}
		} catch (Exception e) {
			MenuDAO.log.error(e.getMessage(), e);
		}
		return menuList;
	}
	
	private void sort(ViewEntity view) {
		try {
			
			Collections.sort((List<PermissionEntity>)view.getPermissionList());
			
			Map<String, Object> parameterMap = new HashMap<>();
			parameterMap.put("parentView.id", view.getId());
			
			view.setChildViewList(this.getViewBO().selectList(parameterMap));
			
			Collections.sort((List<ViewEntity>)view.getChildViewList());
			for (ViewEntity child : view.getChildViewList()) {
				this.sort(child);
			}
			
		} catch (Exception e) {
			MenuDAO.log.error(e.getMessage(), e);
		}
	}

}
