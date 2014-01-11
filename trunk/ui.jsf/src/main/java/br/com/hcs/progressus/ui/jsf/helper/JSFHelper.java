package br.com.hcs.progressus.ui.jsf.helper;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.hcs.progressus.enumerator.Language;

public class JSFHelper implements Serializable {

	private static final long serialVersionUID = -835929172494155533L;
	
	public static FacesContext getFacesContext() { 
		return FacesContext.getCurrentInstance();
	}
	public static Application getApplication() { 
		FacesContext facesContext = JSFHelper.getFacesContext();
		if (facesContext == null) {
			return null;
		}
		return facesContext.getApplication(); 
	}
	public static NavigationHandler getNavigationHandler(){
		Application application = JSFHelper.getApplication();
		if (application == null) {
			return null;
		}
		return application.getNavigationHandler();
	}
	public static UIViewRoot getUIViewRoot(){ 
		FacesContext facesContext = JSFHelper.getFacesContext();
		if (facesContext == null) {
			return null;
		}
		return facesContext.getViewRoot(); 
	}
	public static ExternalContext getExternalContext(){
		FacesContext facesContext = JSFHelper.getFacesContext();
		if (facesContext == null) {
			return null;
		}
		return facesContext.getExternalContext();
	}
	public static HttpServletRequest getHttpServletRequest() {
		ExternalContext externalContext = JSFHelper.getExternalContext();
		if (externalContext == null) {
			return null;
		}
		return (HttpServletRequest)externalContext.getRequest();
	}
	public static Locale getLocale(){ 
		UIViewRoot uiViewRoot = JSFHelper.getUIViewRoot();
		if (uiViewRoot == null) {
			return Language.getDefault().getLocale();
		}
		return uiViewRoot.getLocale(); 
	}
	
	
	public static Map<String, Object> getSessionMap(){
		ExternalContext externalContext = JSFHelper.getExternalContext();
		if (externalContext == null) {
			return null;
		}
		return externalContext.getSessionMap();
	}
	public static void putSessionAttribute(Map<? extends String, ? extends Object> mapValues){
		Map<String, Object> sessionMap = JSFHelper.getSessionMap();
		if (sessionMap == null) {
			return;
		}
		sessionMap.putAll(mapValues);
	}
	public static void putSessionAttribute(String key, Object value){
		Map<String, Object> sessionMap = JSFHelper.getSessionMap();
		if (sessionMap == null) {
			return;
		}
		sessionMap.put(key, value);
	}
	public static void removeSessionAttribute(String key) {
		Map<String, Object> sessionMap = JSFHelper.getSessionMap();
		if (sessionMap == null) {
			return;
		}
		sessionMap.remove(key);
	}
	public static Object getSessionAtribute(String key){
		Map<String, Object> sessionMap = JSFHelper.getSessionMap();
		if (sessionMap == null) {
			return null;
		}
		return sessionMap.get(key);
	}
	
	
	public static Map<String, Object> getApplicationMap(){
		ExternalContext externalContext = JSFHelper.getExternalContext();
		if (externalContext == null) {
			return null;
		}
		return externalContext.getApplicationMap();
	}
	public static void putApplicationAttribute(Map<? extends String, ? extends Object> mapValues){
		Map<String, Object> applicationMap = JSFHelper.getApplicationMap();
		if (applicationMap == null) {
			return;
		}
		applicationMap.putAll(mapValues);
	}
	public static void putApplicationAttribute(String key, Object value){
		Map<String, Object> applicationMap = JSFHelper.getApplicationMap();
		if (applicationMap == null) {
			return;
		}
		applicationMap.put(key, value);
	}
	public static void removeApplicationAttribute(String key) {
		Map<String, Object> applicationMap = JSFHelper.getApplicationMap();
		if (applicationMap == null) {
			return;
		}
		applicationMap.remove(key);
	}
	public static Object getApplicationAtribute(String key){
		Map<String, Object> applicationMap = JSFHelper.getApplicationMap();
		if (applicationMap == null) {
			return null;
		}
		return applicationMap.get(key);
	}
	
	
	public static void handleNavigation(String outcome){
		JSFHelper.handleNavigation(null, outcome);
	}
	public static void handleNavigation(String fromAction, String outcome){
		JSFHelper.handleNavigation(JSFHelper.getFacesContext(), fromAction, outcome);
	}
	public static void handleNavigation(FacesContext facesContext, String fromAction, String outcome){
		try {
			if (facesContext == null) {
				return;
			}
			NavigationHandler navigationHandler = JSFHelper.getNavigationHandler();
			if (navigationHandler == null) {
				return;
			} 
			navigationHandler.handleNavigation(facesContext, fromAction, outcome);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
