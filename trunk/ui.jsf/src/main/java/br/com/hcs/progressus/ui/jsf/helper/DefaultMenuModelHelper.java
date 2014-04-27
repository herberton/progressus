package br.com.hcs.progressus.ui.jsf.helper;

import java.io.Serializable;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSeparator;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.Submenu;

import br.com.hcs.progressus.enumerator.MenuLevel;
import br.com.hcs.progressus.enumerator.Separator;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.server.jpa.entity.ItemMenuEntity;
import br.com.hcs.progressus.server.jpa.entity.MenuEntity;
import br.com.hcs.progressus.server.jpa.entity.PermissionEntity;
import br.com.hcs.progressus.server.jpa.entity.UserEntity;
import br.com.hcs.progressus.server.jpa.entity.ViewEntity;

@Slf4j
public final class DefaultMenuModelHelper implements Serializable {

	private static final long serialVersionUID = -123667730972504441L;

	public static final DefaultMenuModel getDefaultMenuModel(List<MenuEntity> menuList, MenuLevel menuLevel) {
		try {
			return DefaultMenuModelHelper.getDefaultMenuModel(null, menuList, menuLevel);
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static final DefaultMenuModel getDefaultMenuModel(UserEntity user, List<MenuEntity> menuList, MenuLevel menuLevel) {
		
		try {
			
			boolean isCheckPermission = user != null;
			
			DefaultMenuModel defaultMenuModel = new DefaultMenuModel();
			
			for (MenuEntity menu : menuList) {
				
				if (isCheckPermission && !user.havePermission(menu)) {
					continue;
				}
				
				defaultMenuModel.addElement(DefaultMenuModelHelper.getSubMenu(user, menu, menuLevel));
			}
			
			return defaultMenuModel;
			
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	
	public static final DefaultSubMenu getSubMenu(UserEntity user, MenuEntity menu, MenuLevel menuLevel) {
		
		try {
			
			boolean isCheckPermission = user != null;
			
			DefaultSubMenu submenu = DefaultMenuModelHelper.getSubMenu(menu);
			
			for (MenuEntity childMenu : menu.getChildMenuList()) {
				
				if (isCheckPermission && !user.havePermission(childMenu)) {
					continue;
				}
				
				submenu.addElement(DefaultMenuModelHelper.getSubMenu(user, childMenu, menuLevel));
			}
			
			for (ItemMenuEntity itemMenu : menu.getItemMenuList()) {
				
				if (isCheckPermission && !user.havePermission(itemMenu)) {
					continue;
				}
				
				if (itemMenu.getSeparator().equals(Separator.BEFORE)) {
					DefaultMenuModelHelper.addSeparator(submenu);
				}
				
				if (menuLevel.equals(MenuLevel.MENU_ITEM)) {
				
					submenu.addElement(DefaultMenuModelHelper.getMenuItem(itemMenu.getView()));
					
					if (itemMenu.getSeparator().equals(Separator.AFTER)) {
						DefaultMenuModelHelper.addSeparator(submenu);
					}
					
					continue;
				}
				
				submenu.addElement(DefaultMenuModelHelper.getElement(itemMenu.getView(), menuLevel));
				
				if (itemMenu.getSeparator().equals(Separator.AFTER)) {
					DefaultMenuModelHelper.addSeparator(submenu);
				}
			}
			
			return submenu;
			
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	public static final DefaultSubMenu getSubMenu(MenuEntity menu) {
		
		try {
			String menuName = "";
			
			try {
				menuName = I18NHelper.getText(menu.getName());	
			} catch (ProgressusException e) {
				menuName = menu.getName();
			}
			
			return 
				DefaultMenuModelHelper
					.getSubMenu(
						DefaultMenuModelHelper.getSubMenuID(menu),
						menu.getIcon(),
						menuName
					);
			
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	public static final DefaultSubMenu getSubMenu(String id, String icon, String label) {
		
		try {
			
			DefaultSubMenu submenu = new DefaultSubMenu();
			
			submenu.setId(id);
			submenu.setIcon(icon);
			submenu.setLabel(label);
			
			return submenu;
			
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	public static final Submenu getSubMenu(ViewEntity view, MenuLevel level) {
		
		try {
			
			String viewName = "";
			
			try {
				viewName = I18NHelper.getText(view.getName());
			} catch (ProgressusException e) {
				viewName = view.getName();
			}
			
			DefaultSubMenu submenu =
				DefaultMenuModelHelper
					.getSubMenu(
						DefaultMenuModelHelper.getMenuItemID(view),
						DefaultMenuModelHelper.getIcon(view),
						viewName
					);
			
			for (ViewEntity childView : view.getChildViewList()) {
				submenu.addElement(DefaultMenuModelHelper.getElement(childView, level));
			}
			
			if (level.equals(MenuLevel.PERMISSION)) {
				
				for (PermissionEntity permission : view.getPermissionList()) {
					submenu.addElement(DefaultMenuModelHelper.getMenuItem(permission));
				}
					
			}
			
			return submenu;
			
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	
	public static final DefaultMenuItem getMenuItem(ViewEntity view) {
		
		try {
			
			String viewName = "";
			
			try {
				viewName = I18NHelper.getText(view.getName());
			} catch (ProgressusException e) {
				viewName = view.getName();
			}
			
			String viewDescription = "";
			
			try {
				viewDescription = I18NHelper.getText(view.getDescription());
			} catch (ProgressusException e) {
				viewDescription = view.getName();
			}
			
			return 
				DefaultMenuModelHelper
					.getMenuItem(
						DefaultMenuModelHelper.getMenuItemID(view), 
						DefaultMenuModelHelper.getIcon(view), 
						viewName, 
						viewDescription, 
						DefaultMenuModelHelper.getOutcome(DefaultMenuModelHelper.getModuleName(view), view.getName())
					);
			
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	public static final DefaultMenuItem getMenuItem(String id, String icon, String value, String title, String outcome) {
		
		try {
			
			DefaultMenuItem menuItem = new DefaultMenuItem();
			
			menuItem.setId(id);
			menuItem.setIcon(icon);
			menuItem.setValue(value);
			menuItem.setTitle(title);
			menuItem.setOutcome(outcome);
			
			return menuItem;
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		
		return null;
	}

	public static final DefaultMenuItem getMenuItem(PermissionEntity permission) {
		
		try {
			String permissionName = "";
			
			try {
				permissionName = I18NHelper.getText(permission.getName());
			} catch (ProgressusException e) {
				permissionName = permission.getName();
			}
			
			return 
				DefaultMenuModelHelper
					.getMenuItem(
						DefaultMenuModelHelper.getMenuItemID(permission), 
						DefaultMenuModelHelper.getIcon(permission), 
						permissionName, 
						permissionName, 
						null
					);
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	public static final MenuElement getElement(ViewEntity view, MenuLevel menuLevel) {
		
		try {
			if (menuLevel.equals(MenuLevel.VIEW)) {
				
				if (view.getChildViewList().size() > 0) {
					
					DefaultSubMenu submenu =
						DefaultMenuModelHelper
							.getSubMenu(
								DefaultMenuModelHelper.getMenuItemID(), 
								DefaultMenuModelHelper.getIcon(view), 
								String.format("%s (%s %s)", I18NHelper.getText(view.getName()), view.getChildViewList().size() + 1, I18NHelper.getText("views"))
							);
					
					submenu.addElement(DefaultMenuModelHelper.getMenuItem(view));
					
					for (ViewEntity childView : view.getChildViewList()) {
						submenu.addElement(DefaultMenuModelHelper.getElement(childView, menuLevel));
					}
					
					return submenu;
				}

				return DefaultMenuModelHelper.getMenuItem(view);
			}
			
			return DefaultMenuModelHelper.getSubMenu(view, menuLevel);
			
		} catch (ProgressusException e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		
		return null;
	}
	
	
	public static final void addSeparator(DefaultSubMenu defaultSubMenu) {
		try {
			defaultSubMenu.addElement(new DefaultSeparator());
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
	}

	
	public static final String getOutcome(String moduleName, String viewName) {
		try {
			return 
				String.format("/%s/%s", moduleName, viewName.replace(".", Setting.SEPARATOR.toString()));
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	
	public static final String getModuleName(ViewEntity view) {
		try {
			return 
				view.getParentView() != null ?
					DefaultMenuModelHelper.getModuleName(view.getParentView()) :
					DefaultMenuModelHelper.getModuleName(view.getItemMenu().getParentMenu());
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	public static final String getModuleName(MenuEntity menu) {
		try {
			return
				menu.getParentMenu() != null ?
					DefaultMenuModelHelper.getModuleName(menu.getParentMenu()) :
					menu.getName();
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	
	public static final String getSubMenuID(MenuEntity menu) {
		try {
			return 
				Setting.WEB_MENU_ID_FORMAT.toString()
					.replace("[menu_id]", menu.getId().toString())
					.replace("[menu_name]", menu.getName())
					.replace("[math.random]", new Double(Math.random()).toString().replace(".", ""));
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	public static final String getMenuItemID(ViewEntity view) {
		return 
			Setting.WEB_VIEW_ID_FORMAT.toString()
				.replace("[view_id]", view.getId().toString())
				.replace("[view_name]", view.getName())
				.replace("[math.random]", new Double(Math.random()).toString().replace(".", ""));
	}

	public static final String getMenuItemID() {
		try {
			return 
				Setting.WEB_ITEM_MENU_ID_PREFIX.toString()
					.concat(Setting.SEPARATOR.toString())
						.concat(new Double(Math.random()).toString().replace(".", ""));
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	public static final String getMenuItemID(PermissionEntity permission) {
		try {
			return 
				Setting.WEB_PERMISSION_ID_FORMAT.toString()
					.replace("[permission_id]", permission.getId().toString())
					.replace("[permission_name]", permission.getName())
					.replace("[math.random]", new Double(Math.random()).toString().replace(".", ""));
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	
	public static final String getIcon(ViewEntity view) {
		try {
			if (view.getParentView() == null) {
				return 
					view.getItemMenu() == null ? 
						Setting.WEB_VIEW_ICON_DEFAULT.toString() : 
						view.getItemMenu().getIcon();
			}
			return DefaultMenuModelHelper.getIcon(view.getParentView());
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	public static final String getIcon(PermissionEntity permission) {
		try {
			return Setting.WEB_PERMISSION_ICON_DEFAULT.toString();
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		return "";
	}

	
}	
