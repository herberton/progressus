package br.com.hcs.progressus.server.ejb.sb.bo.process;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.annotation.View;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.MenuBORemote;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.UserBORemote;
import br.com.hcs.progressus.client.ejb.sb.bo.process.ApplicationBOLocal;
import br.com.hcs.progressus.enumerator.Separator;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.server.jpa.entity.ItemMenuEntity;
import br.com.hcs.progressus.server.jpa.entity.MenuEntity;
import br.com.hcs.progressus.server.jpa.entity.PermissionEntity;
import br.com.hcs.progressus.server.jpa.entity.UserEntity;
import br.com.hcs.progressus.server.jpa.entity.UserPreferenceEntity;
import br.com.hcs.progressus.server.jpa.entity.ViewEntity;

@Slf4j
@NoArgsConstructor
@Stateless
public class ApplicationBO extends ProgressusBOProcess implements ApplicationBOLocal {
	private static final long serialVersionUID = 5377264477230409581L;
	
	@Getter(AccessLevel.PRIVATE)
    @EJB
    private MenuBORemote menuBO;
	@Getter(AccessLevel.PRIVATE)
    @EJB
    private UserBORemote userBO;

	
	@Override
	public void createAdministratorUser() throws ProgressusException {
		
		try {
			
			String login = System.getProperty(Setting.SERVER_KEY_ADMIN_LOGIN.toString());
			
			if (this.getUserBO().select(login) == null) {
				
				UserEntity user = new UserEntity(login);
				user.setPassword("password");
				
				user.setAdministrator(true);
				
				user.setPreference(UserPreferenceEntity.getDefault());
				
				this.getUserBO().save(user);
			}
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ApplicationBO.log.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void createMenuTree(Set<Class<?>> viewClazzSet) throws ProgressusException {
		
		try {
			
			if (this.getMenuBO().count() > 0) {
				return;
			}
			
			List<ViewEntity> viewList = this.newViewList(viewClazzSet);
			
			List<MenuEntity> moduleList = new ArrayList<>();
			
			for(Class<?> viewClazz : viewClazzSet) {
				
				View view = viewClazz.getAnnotation(View.class);
				
				if (view.name().isEmpty()) {
					continue;
				}
				
				String viewName = view.name().replace(".", Setting.SEPARATOR.toString());
				String viewMenu = view.menu().replace(".", Setting.SEPARATOR.toString());
				
				if (viewName.contains(Setting.SEPARATOR.toString())) {
					continue;
				}
				
				MenuEntity module = new MenuEntity(view.module());
				module = this.findModuleInList(moduleList, module);
				
				if (module == null) {
					module = new MenuEntity(view.module());
					moduleList.add(module);
				}
				
				module = this.getMenuTree(module, viewMenu, viewName, view.separator(), viewList);
				
				moduleList.set(moduleList.indexOf(module), module);
				
			}
			
			this.getMenuBO().saveTreeList(moduleList);
			
		} catch (Exception e) {
			ApplicationBO.log.error(e.getMessage(), e);
		}
		
	}
	

	@Override
	public List<MenuEntity> selectMenuTreeList() throws ProgressusException {
		try {
			return this.getMenuBO().selectTreeList();
		} catch (Exception e) {
			ApplicationBO.log.error(e.getMessage(), e);
		}
		return new ArrayList<MenuEntity>();
	}
	
	
	
	private MenuEntity findModuleInList(List<MenuEntity> moduleList, MenuEntity moduleSearched) throws ProgressusException {
		
		try {
			
			if(moduleList.contains(moduleSearched)) {
				return moduleList.get(moduleList.indexOf(moduleSearched));
			}
			
		} catch (Exception e) {
			ApplicationBO.log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	
	private MenuEntity getMenuTree(MenuEntity menu, String menuPath, String viewName, Separator viewSeparator, List<ViewEntity> viewList) throws ProgressusException {
		
		try {
			if (StringHelper.isNullOrEmpty(menuPath)) {
				return this.newItemMenu(menu, viewName, viewSeparator, viewList);
			}
			
			String newMenuName =
				menuPath.contains(Setting.SEPARATOR.toString()) ?
					menuPath.substring(0, menuPath.indexOf(Setting.SEPARATOR.toString())) :
					menuPath;
					
			
			menuPath = 
				menuPath.contains(Setting.SEPARATOR.toString()) ?
					menuPath.substring(menuPath.indexOf(Setting.SEPARATOR.toString()) + 1, menuPath.length()) :
					"";
			
			MenuEntity childMenu = new MenuEntity(newMenuName);
			
			if(menu.getChildMenu(childMenu) == null){
				menu.addChildMenu(childMenu);
			} else {
				childMenu = menu.getChildMenu(childMenu);
			}
			
			
			childMenu = this.getMenuTree(childMenu, menuPath, viewName, viewSeparator, viewList);
			menu.setChildMenu(childMenu, false);
		} catch (Exception e) {
			ApplicationBO.log.error(e.getMessage(), e);
		}
		
		return menu;
	}
	
	
	private MenuEntity newItemMenu(MenuEntity menu, String viewName, Separator viewSeparator, List<ViewEntity> viewList) throws ProgressusException {
		
		try {
			
			ItemMenuEntity itemMenu = new ItemMenuEntity();
			itemMenu.setIcon(this.getMenuItemIcon(viewName));
			itemMenu.setSeparator(viewSeparator);
			
			ViewEntity view = this.findViewInList(viewList, new ViewEntity(viewName)); 
			itemMenu.setView(view);
			
			menu.addItemMenu(itemMenu);
			
			return menu;
			
		} catch (Exception e) {
			ApplicationBO.log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	@Override
	public List<ViewEntity> newViewList(Set<Class<?>> viewClazzSet) throws ProgressusException {
		
		List<ViewEntity> viewList = new ArrayList<>();
		
		try {
			
			for(Class<?> viewClazz : viewClazzSet) {
				
				View view = viewClazz.getAnnotation(View.class);
				
				if (StringHelper.isNullOrEmpty(view.name())) {
					continue;
				}
				
				String viewName = view.name().replace(".", Setting.SEPARATOR.toString());
				
				ViewEntity newView = ApplicationBO.newView(viewName, view.description(), view.permissions());
				
				ApplicationBO.addViewInList(viewList, newView);
			}
			
		} catch (Exception e) {
			ApplicationBO.log.error(e.getMessage(), e);
		}
		
		return viewList;
	}
	
	@Override
	public ViewEntity newView(Class<?> viewClazz) throws ProgressusException {
		try {
			Set<Class<?>> viewClazzSet = new HashSet<>();
			viewClazzSet.add(viewClazz);
			return this.newViewList(viewClazzSet).get(0);
		} catch (Exception e) {
			ApplicationBO.log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	private static final ViewEntity newView(String name, String description, String[] permissions) throws ProgressusException {
		
		try {
			
			String newName =
				name.contains(Setting.SEPARATOR.toString()) ?
					name.split(Setting.SEPARATOR.toString())[0] :
					name;
			
			description = StringHelper.isNullOrEmpty(description) ? newName : description;
					
			ViewEntity view = 
				name.contains(Setting.SEPARATOR.toString()) ?
					new ViewEntity(newName, null) :
					new ViewEntity(newName, description, ApplicationBO.getPermissionArray(permissions));
			
			if (name.contains(Setting.SEPARATOR.toString())) {
				
				newName = name.substring(name.indexOf(Setting.SEPARATOR.toString()) + 1, name.length());
				
				ViewEntity childView = ApplicationBO.newView(newName, description, permissions);
				
				childView.setParentView(view);
			}
				
			return view;
			
		} catch (Exception e) {
			ApplicationBO.log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	private static PermissionEntity[] getPermissionArray(String[] permissionArray) throws ProgressusException {
		List<PermissionEntity> permisisonList = new ArrayList<>();
		try {
			for (String permission : permissionArray) {
				PermissionEntity entity = new PermissionEntity(permission);
				permisisonList.add(entity);
			}
		} catch (Exception e) {
			ApplicationBO.log.error(e.getMessage(), e);
		}
		return permisisonList.toArray(new PermissionEntity[]{}); 
	}
	
	
	private String getMenuItemIcon(String viewName) throws ProgressusException {
		try {
			return 
				Setting.WEB_ITEM_MENU_ICON_FORMAT.toString().replace("[item_menu_name]", viewName);
		} catch (Exception e) {
			ApplicationBO.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	
	private  ViewEntity findViewInList(List<ViewEntity> viewList, ViewEntity viewSearched) throws ProgressusException {
		
		try {
			if(viewList.contains(viewSearched)) {
				return viewList.get(viewList.indexOf(viewSearched));
			}
			
			for (ViewEntity view : viewList) {
				ViewEntity viewFound = this.findViewInList(view.getChildViewList(), viewSearched);
				if (viewFound != null) {
					return viewFound;
				}
			}
		} catch (Exception e) {
			ApplicationBO.log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	
	private static final void addViewInList(List<ViewEntity> list, ViewEntity view) throws ProgressusException {
		
		try {
			
			if(!list.contains(view)) {
				list.add(view);	
				return;
			}
			
			int index = list.indexOf(view);
			ViewEntity foundView = list.get(index);
			
			if (view.getChildViewList().size() <= 0) {
				foundView.setDescription(view.getDescription());
				foundView.setPermissionList(view.getPermissionList());
				list.set(index, foundView);
				return;
			}
			
			for (ViewEntity childView : view.getChildViewList()) {
				ApplicationBO.addViewInList((List<ViewEntity>)foundView.getChildViewList(), childView);
				childView.setParentView(foundView);
			}
			
		} catch (Exception e) {
			ApplicationBO.log.error(e.getMessage(), e);
		}
	}
}
