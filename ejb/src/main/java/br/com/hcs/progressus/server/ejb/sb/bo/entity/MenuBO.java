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
			throw new UnableToCompleteOperationException("saveTreeList", e);
		}
	}
	
	
	private void print(List<MenuEntity> menuTreeList) throws ProgressusException {
		if (menuTreeList == null) {
			return;
		}
		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("\n-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		stringBuffer.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		stringBuffer.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		stringBuffer.append("MODULE STRUCTURE:\n");
		stringBuffer.append("[\n");
		for (MenuEntity menuTree : menuTreeList) {
			this.print(stringBuffer, "  ", menuTree);
		}
		stringBuffer.append("]\n");
		stringBuffer.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		stringBuffer.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		stringBuffer.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		
		MenuBO.log.info(stringBuffer.toString());
	}
	
	private void print(StringBuffer stringBuffer, String tab, MenuEntity menu) throws ProgressusException {
		
		stringBuffer.append(tab);
		stringBuffer.append("+ ");
		stringBuffer.append(menu.toString());
		
		stringBuffer.append("\n");
		
		tab += "  ";
		
		for (MenuEntity child : menu.getChildMenuList()) {
			this.print(stringBuffer, tab, child);
		}
		
		stringBuffer.append("\n");
		
		for (ItemMenuEntity child : menu.getItemMenuList()) {			
			this.print(stringBuffer, tab, child);
		}
	}
	
	private void print(StringBuffer stringBuffer, String tab, ViewEntity view) throws ProgressusException {
		
		stringBuffer.append(tab);
		stringBuffer.append("- ");
		stringBuffer.append(view.toString());
		
		if (view instanceof ItemMenuEntity) {
			stringBuffer.append("  [  ");
			stringBuffer.append(((ItemMenuEntity)view).getIcon());
			stringBuffer.append("  ]");
		} 
		
		stringBuffer.append("\n");
		
		tab +="  ";
		
		for (PermissionEntity permission : view.getPermissionList()) {
			stringBuffer.append(tab);
			stringBuffer.append(": ");
			stringBuffer.append(permission.toString());
			stringBuffer.append("\n");
		}
		
		stringBuffer.append("\n");
		
		for (ViewEntity child : view.getChildViewList()) {
			this.print(stringBuffer, tab, child);
		}
	}
}
