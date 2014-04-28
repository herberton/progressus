package br.com.hcs.progressus.enumerator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.helper.StringHelper;

@Slf4j
public enum Setting {
	
	SEPARATOR("_"),
	
	
	SERVER_KEY_ADMIN_LOGIN("hcs.progressus.admin.login"),
	SERVER_KEY_ADMIN_PASSWORD("hcs.progressus.admin.password"),
	
	
	WEB_PACKAGE_I18N_RESOURCE_BUNDLE("br.com.hcs.progressus.ui.jsf.i18n.application"),
	
	WEB_APPLICATION_MENU_TREE_LIST("menuList"),
	
	WEB_DYNAMIC_ACTIONS_RENDERKIT("HTML_BASIC"),
	
	WEB_MANAGED_BEAN_PACKAGE("br.com.hcs.progressus.ui.jsf.mb"),
	
	WEB_MENU_ID_PREFIX("id_menu"),
	WEB_MENU_ICON_FORMAT("icon_menu_[menu_name]"),
	WEB_MENU_ID_FORMAT(String.format("%s%s[menu_id]%s[menu_name]%s[math.random]", WEB_MENU_ID_PREFIX.toString(), SEPARATOR.toString(), SEPARATOR.toString(), SEPARATOR.toString())),
	
	WEB_ITEM_MENU_ID_PREFIX("id_item_menu"),
	WEB_ITEM_MENU_ICON_FORMAT("icon_item_menu_[item_menu_name]"),
	
	WEB_VIEW_ID_PREFIX("id_view"),
	WEB_VIEW_ID_FORMAT(String.format("%s%s[view_id]%s[view_name]%s[math.random]", WEB_VIEW_ID_PREFIX.toString(), SEPARATOR.toString(), SEPARATOR.toString(), SEPARATOR.toString())),
	WEB_VIEW_ICON_DEFAULT("view_icon"),
	
	WEB_PERMISSION_ID_PREFIX("id_permission"),
	WEB_PERMISSION_ID_FORMAT(String.format("%s%s[permisison_id]%s[permisison_name]%s[math.random]", WEB_PERMISSION_ID_PREFIX.toString(), SEPARATOR.toString(), SEPARATOR.toString(), SEPARATOR.toString())),
	WEB_PERMISSION_ICON_DEFAULT("permission_icon"),
	
	WEB_SESSION_DEFAULT_MENU_MODEL("defaultMenuModel"),
	
	I18N_NOT_FOUND_MESSAGE("???[i18n]???"),
	
	EJB_JNDI_LOOKUP("java:global/ear/progressus.ejb/[class_simpleName]![class_name]");
	
	
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private String value;
	
	
	private Setting(String value) {
		this.setValue(value);
	}
	
	
	@Override
	public String toString() {
		try {
			return StringHelper.isNullOrEmpty(this.getValue()) ? "" : this.getValue();
		} catch (Exception e) {
			Setting.log.error(e.getMessage(), e);
		}
		return "";
	}
}
