package br.com.hcs.progressus.server.ejb.sb.bo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.ItemMenuBORemote;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.MenuBORemote;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.ViewBORemote;
import br.com.hcs.progressus.client.ejb.sb.dao.MenuDAOLocal;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.server.jpa.entity.ItemMenuEntity;
import br.com.hcs.progressus.server.jpa.entity.MenuEntity;
import br.com.hcs.progressus.server.jpa.entity.PermissionEntity;
import br.com.hcs.progressus.server.jpa.entity.ViewEntity;

@Slf4j
@NoArgsConstructor
@Stateless
public class MenuBO extends ProgressusBOEntity<MenuEntity> implements MenuBORemote {
	
	private static final long serialVersionUID = 3042628261407347761L;
	
	
	@EJB
	@Getter(AccessLevel.PRIVATE)
	private ItemMenuBORemote itemMenuBO;
	@EJB
	@Getter(AccessLevel.PRIVATE)
	private ViewBORemote viewBO;
	
	
	@Override
	public List<MenuEntity> selectList() throws ProgressusException {
		try {
			return ((MenuDAOLocal)this.getDAO()).selectList();
		} catch (Exception e) {
			MenuBO.log.error(e.getMessage(), e);
		}
		return new ArrayList<>();
	}
	
	
	@Override
	public List<MenuEntity> selectTreeList() throws ProgressusException {
		try {
			List<MenuEntity> menuTreeList = this.selectList(); 
			this.print(menuTreeList);
			return menuTreeList;
		} catch (Exception e) {
			MenuBO.log.error(e.getMessage(), e);
		}
		return new ArrayList<>();
	}
	
	@Override
	public List<MenuEntity> saveTreeList(List<MenuEntity> treeList) throws ProgressusException {
		try {
			return ((MenuDAOLocal)this.getDAO()).saveTreeList(treeList);
		} catch (ProgressusException pe) {
			throw pe;
		}catch (Exception e) {
			MenuBO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("saveTreeList");
		}
	}
	
	
	private void print(List<MenuEntity> menuTreeList) {
		if (menuTreeList == null) {
			return;
		}
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("\tMODULE STRUCTURE:");
		System.out.println("\t\t[");
		for (MenuEntity menuTree : menuTreeList) {
			this.print("\t\t\t", menuTree);
		}
		System.out.println("\t\t]");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}
	
	private void print(String tab, MenuEntity menu) {
		
		System.out.println(tab + "+ " + menu.getName());
		tab +="\t";
		
		for (MenuEntity child : menu.getChildMenuList()) {
			this.print(tab, child);
		}
		
		for (ItemMenuEntity child : menu.getItemMenuList()) {			
			this.print(tab, child.getView());
		}
	}
	
	private void print(String tab, ViewEntity view) {
		
		String itemMenuIcon =
			view.getItemMenu() == null ?
				"" :
				" [" + view.getItemMenu().getIcon() + "]";
		
		System.out.println(tab + "- " + view.getFullName() + itemMenuIcon);
		tab +="\t";
		
		for (PermissionEntity child : view.getPermissionList()) {
			System.out.println(tab + ": " + child.getFullName());
		}
		
		for (ViewEntity child : view.getChildViewList()) {
			this.print(tab, child);
		}
	}
}
