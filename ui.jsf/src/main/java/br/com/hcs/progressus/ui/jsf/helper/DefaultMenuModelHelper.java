package br.com.hcs.progressus.ui.jsf.helper;

import java.io.Serializable;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSeparator;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;

import br.com.hcs.progressus.enumerator.MenuLevel;
import br.com.hcs.progressus.enumerator.Separator;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.server.jpa.entity.ItemMenuEntity;
import br.com.hcs.progressus.server.jpa.entity.MenuEntity;
import br.com.hcs.progressus.server.jpa.entity.PermissionEntity;
import br.com.hcs.progressus.server.jpa.entity.UserEntity;
import br.com.hcs.progressus.server.jpa.entity.ViewEntity;

@Slf4j
public final class DefaultMenuModelHelper implements Serializable {

	private static final long serialVersionUID = -123667730972504441L;

	
	public static final DefaultMenuModel getDefaultMenuModel(List<MenuEntity> menuList, MenuLevel menuLevel) throws ProgressusException {
		try {
			return DefaultMenuModelHelper.getDefaultMenuModel(null, menuList, menuLevel);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getDefaultMenuModel", e);
		}
	}
	
	public static final DefaultMenuModel getDefaultMenuModel(UserEntity user, List<MenuEntity> menuList, MenuLevel menuLevel) throws ProgressusException {
		
		try {
			
			DefaultMenuModel defaultMenuModel = new DefaultMenuModel();
			
			for (MenuEntity menu : menuList) {
				
				if (!DefaultMenuModelHelper.havePermission(user, menu)) {
					continue;
				}
				
				defaultMenuModel.addElement(DefaultMenuModelHelper.getSubMenu(user, menu, menuLevel));
			}
			
			return defaultMenuModel;
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getDefaultMenuModel", e);
		}
	}
		
	
	public static final DefaultSubMenu getSubMenu(UserEntity user, MenuEntity menu, MenuLevel menuLevel) throws ProgressusException {
		
		try {
			
			DefaultSubMenu submenu = DefaultMenuModelHelper.getSubMenu(menu);
			
			for (MenuEntity childMenu : menu.getChildMenuList()) {
				
				if (!DefaultMenuModelHelper.havePermission(user, childMenu)) {
					continue;
				}
				
				submenu.addElement(DefaultMenuModelHelper.getSubMenu(user, childMenu, menuLevel));
			}
			
			for (ItemMenuEntity itemMenu : menu.getItemMenuList()) {
				
				if (!DefaultMenuModelHelper.havePermission(user, itemMenu)) {
					continue;
				}
				
				if (itemMenu.getSeparator().equals(Separator.BEFORE)) {
					DefaultMenuModelHelper.addSeparator(submenu);
				}
				
				if (menuLevel.equals(MenuLevel.MENU_ITEM)) {
				
					submenu.addElement(DefaultMenuModelHelper.getMenuItem(itemMenu));
					
					if (itemMenu.getSeparator().equals(Separator.AFTER)) {
						DefaultMenuModelHelper.addSeparator(submenu);
					}
					
					continue;
				}
				
				submenu.addElement(DefaultMenuModelHelper.getElement(itemMenu, menuLevel));
				
				if (itemMenu.getSeparator().equals(Separator.AFTER)) {
					DefaultMenuModelHelper.addSeparator(submenu);
				}
			}
			
			return submenu;
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSubMenu", e);
		}
	}
	
	public static final DefaultSubMenu getSubMenu(MenuEntity menu) throws ProgressusException {
		
		try {
			return 
				DefaultMenuModelHelper
					.getSubMenu(
						DefaultMenuModelHelper.getSubMenuID(menu),
						menu.getIcon(),
						I18NHelper.getText(menu.getName())
					);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSubMenu", e);
		}
	}
	
	public static final DefaultSubMenu getSubMenu(String id, String icon, String label) throws ProgressusException {
		
		try {
			
			DefaultSubMenu submenu = new DefaultSubMenu();
			
			submenu.setId(id);
			submenu.setIcon(icon);
			submenu.setLabel(label);
			
			return submenu;
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSubMenu", e);
		}
	}
	
	public static final DefaultSubMenu getSubMenu(ViewEntity view, MenuLevel level) throws ProgressusException {
		
		try {
			
			DefaultSubMenu submenu =
				DefaultMenuModelHelper
					.getSubMenu(
						DefaultMenuModelHelper.getMenuItemID(view),
						DefaultMenuModelHelper.getIcon(view),
						I18NHelper.getText(view.getName())
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
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSubMenu", e);
		}
	}
	
	
	public static final DefaultMenuItem getMenuItem(ViewEntity view) throws ProgressusException {
		
		try {
			
			return 
				DefaultMenuModelHelper
					.getMenuItem(
						DefaultMenuModelHelper.getMenuItemID(view), 
						DefaultMenuModelHelper.getIcon(view), 
						I18NHelper.getText(view.getName()), 
						I18NHelper.getText(StringHelper.isNullOrEmpty(view.getDescription()) ? view.getName() : view.getDescription()), 
						DefaultMenuModelHelper.getOutcome(DefaultMenuModelHelper.getModuleName(view), view.getName())
					);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getMenuItem", e);
		}
	}
	
	public static final DefaultMenuItem getMenuItem(String id, String icon, String value, String title, String outcome) throws ProgressusException {
		
		try {
			
			DefaultMenuItem menuItem = new DefaultMenuItem();
			
			menuItem.setId(id);
			menuItem.setIcon(icon);
			menuItem.setValue(value);
			menuItem.setTitle(title);
			menuItem.setOutcome(outcome);
			
			return menuItem;
		
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getMenuItem", e);
		}
	}

	public static final DefaultMenuItem getMenuItem(PermissionEntity permission) throws ProgressusException {
		
		try {
			return 
				DefaultMenuModelHelper
					.getMenuItem(
						DefaultMenuModelHelper.getMenuItemID(permission), 
						DefaultMenuModelHelper.getIcon(permission), 
						I18NHelper.getText(permission.getName()), 
						I18NHelper.getText(permission.getName()), 
						null
					);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getMenuItem", e);
		}
	}
	
	
	public static final MenuElement getElement(ViewEntity view, MenuLevel menuLevel) throws ProgressusException {
		
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
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("geElement", e);
		}
	}
	
	
	public static final void addSeparator(DefaultSubMenu defaultSubMenu) throws ProgressusException {
		try {
			defaultSubMenu.addElement(new DefaultSeparator());
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("addSeparator", e);
		}
	}

	
	public static final String getOutcome(String moduleName, String viewName) throws ProgressusException {
		try {
			return 
				String.format("/%s/%s", moduleName, viewName);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getOutcome", e);
		}
	}
	
	
	public static final String getModuleName(ViewEntity view) throws ProgressusException {
		try {
			return 
				view instanceof ItemMenuEntity ?
					DefaultMenuModelHelper.getModuleName(((ItemMenuEntity)view).getParentMenu()) :
					DefaultMenuModelHelper.getModuleName(view.getParentView());
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getModuleName", e);
		}
	}
	
	public static final String getModuleName(MenuEntity menu) throws ProgressusException {
		try {
			return
				menu.getParentMenu() != null ?
					DefaultMenuModelHelper.getModuleName(menu.getParentMenu()) :
					menu.getName();
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getModuleName", e);
		}
	}
	
	
	public static final String getSubMenuID(MenuEntity menu) throws ProgressusException {
		try {
			return 
				Setting.WEB_MENU_ID_FORMAT.toString()
					.replace("[menu_id]", menu.getId().toString())
					.replace("[menu_name]", menu.getName())
					.replace("[math.random]", new Double(Math.random()).toString().replaceAll("\\.", ""));
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSubMenuID", e);
		}
	}
	
	public static final String getMenuItemID(ViewEntity view) throws ProgressusException {
		try {
			return 
				Setting.WEB_VIEW_ID_FORMAT.toString()
					.replace("[view_id]", view.getId().toString())
					.replace("[view_name]", view.getName())
					.replace("[math.random]", new Double(Math.random()).toString().replaceAll("\\.", ""));
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getMenuItemID", e);
		}
	}

	public static final String getMenuItemID() throws ProgressusException {
		try {
			return 
				Setting.WEB_ITEM_MENU_ID_PREFIX.toString()
					.concat(Setting.SEPARATOR.toString())
						.concat(new Double(Math.random()).toString().replaceAll("\\.", ""));
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getMenuItemID", e);
		}
	}
	
	public static final String getMenuItemID(PermissionEntity permission) throws ProgressusException {
		try {
			return 
				Setting.WEB_PERMISSION_ID_FORMAT.toString()
					.replace("[permission_id]", permission.getId().toString())
					.replace("[permission_name]", permission.getName())
					.replace("[math.random]", new Double(Math.random()).toString().replaceAll("\\.", ""));
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getMenuItemID", e);
		}
	}
	
	
	public static final String getIcon(ViewEntity view) {
		try {
			if (view instanceof ItemMenuEntity) {
				return ((ItemMenuEntity)view).getIcon();
			}
			return DefaultMenuModelHelper.getIcon(view.getParentView());
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	public static final String getIcon(PermissionEntity permission) throws ProgressusException {
		try {
			return Setting.WEB_PERMISSION_ICON_DEFAULT.toString();
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getIcon", e);
		}
	}

	
	private static final boolean havePermission(UserEntity user, MenuEntity menu) {
		try {
			return  user == null ? true : user.havePermission(menu);
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		return false;
	}
	
	private static final boolean havePermission(UserEntity user, ViewEntity view) {
		try {
			return  user == null ? true : user.havePermission(view);
		} catch (Exception e) {
			DefaultMenuModelHelper.log.error(e.getMessage(), e);
		}
		return false;
	}
}	
