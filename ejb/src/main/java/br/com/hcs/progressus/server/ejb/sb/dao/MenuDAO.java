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
			
		} catch (Exception e) {
			MenuDAO.log.error(e.getMessage(), e);
		}
		
		return new ArrayList<>();
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
			MenuDAO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("saveTreeList");
		}
	}
	
	
	private MenuEntity saveTree(MenuEntity tree) throws ProgressusException {
		
		try {
			
			List<MenuEntity> childMenuList = tree.getChildMenuList();
			tree.setChildMenuList(null);
			
			List<ItemMenuEntity> itemMenuList = tree.getItemMenuList();
			tree.setItemMenuList(null);
			
			if (tree.hasId()) {
				
				tree = this.getBO().select(tree);
				
				if (tree == null) {
					throw new UnableToCompleteOperationException("saveTreeList", "entityNotFound");
				}
				
			} else {			
				tree = this.insert(tree, false);
			}
			
			
			for (MenuEntity child : childMenuList) {
				
				child.setParentMenu(null);
				
				child = this.saveTree(child);
				
				tree.addChildMenu(child);
				
			}
			
			for (ItemMenuEntity itemMenu : itemMenuList) {
				
				itemMenu.setParentMenu(null);
				
				ViewEntity view = itemMenu.getView();
				
				view.setItemMenu(null);
				itemMenu.setView(null);
				
				view = this.saveView(view);
				
				if (itemMenu.hasId()) {
					itemMenu = this.getItemMenuBO().select(itemMenu);
				} else {
					itemMenu = this.insertEntity(itemMenu, false);
				}
				
				itemMenu.setView(view);
				tree.addItemMenu(itemMenu);
				
				itemMenu = this.updateEntity(itemMenu, false);
				
			}
					
			return this.update(tree, false);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			MenuDAO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("saveTreeList");
		}
	}
	
	private ViewEntity saveView(ViewEntity view) throws ProgressusException {
		
		try {
			
			List<ViewEntity> childViewList = view.getChildViewList();
			view.setChildViewList(null);
			
			List<PermissionEntity> permissionList = view.getPermissionList();
			view.setPermissionList(null);
			
			if (view.hasId()) {
				view = this.getViewBO().select(view);
			} else {
				view = this.insertEntity(view, false);
			}
			
			for (ViewEntity child : childViewList) {
				
				child.setParentView(null);
				
				child = this.saveView(child);
			
				view.addChildView(child);
			}
			
			
			for (PermissionEntity permission : permissionList) {
				
				permission.setView(null);
				
				if (permission.hasId()) {
					permission = this.getPermissionBO().select(permission);
				}else{
					permission = this.insertEntity(permission, false);
				}
				
				view.addPermision(this.updateEntity(permission, false));
			}
			
			return this.updateEntity(view, false);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			MenuDAO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("saveTreeList");
		}
	}
	

	private List<MenuEntity> sort(List<MenuEntity> menuList){
		try {
			Collections.sort((List<MenuEntity>)menuList);
			for (MenuEntity menu : menuList) {
				
				Map<String, Object> parameterMap = new HashMap<>();
				parameterMap.put("parentMenu.id", menu.getId());
				
				menu.setChildMenuList(this.getBO().selectList(parameterMap));
				menu.setItemMenuList(this.getItemMenuBO().selectList(parameterMap));
				
				Collections.sort((List<ItemMenuEntity>)menu.getItemMenuList());
				
				for (ItemMenuEntity item : menu.getItemMenuList()) {
					this.sort(item.getView());
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
