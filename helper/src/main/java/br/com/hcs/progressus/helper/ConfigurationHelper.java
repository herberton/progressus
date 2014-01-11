package br.com.hcs.progressus.helper;

import java.io.Serializable;

public class ConfigurationHelper implements Serializable {

	private static final long serialVersionUID = 5050188194641355349L;
	
	// COMMON
	public static final String COMMON_SEPARATOR = "";
	
	// UI.WEB
	public static final String UI_WEB_MENU_ID_PREFIX = "";
	public static final String UI_WEB_VIEW_ID_PREFIX = "";
	public static final String UI_WEB_PERMISSION_ID_PREFIX = "";
	public static final String UI_WEB_ITEM_MENU_ID_PREFIX = "";
	public static final String UI_WEB_DYNAMIC_ACTIONS_RENDERKIT = "HTML_BASIC";
	public static final String UI_WEB_URL_DEFAULT_PARAMETERS = "";
	public static final String UI_WEB_PACKAGE_I18N_PROPERTIES_BASE_NAME = "";
	public static final String UI_WEB_SESSION_DEFAULT_MENU_MODEL = "";
	
	// JNDI
	public static String JNDI_EJB_LOOKUP = "java:global/ear/progressus.ejb/[class_simpleName]![class_name]";
}
