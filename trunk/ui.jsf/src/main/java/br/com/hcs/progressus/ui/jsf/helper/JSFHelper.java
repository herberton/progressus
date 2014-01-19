package br.com.hcs.progressus.ui.jsf.helper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.hcs.progressus.enumerator.Language;
import br.com.hcs.progressus.helper.MapHelper;
import br.com.hcs.progressus.helper.StringHelper;

public class JSFHelper implements Serializable {

	
	private static final long serialVersionUID = -835929172494155533L;
	private static final Logger logger = LoggerFactory.getLogger(JSFHelper.class);
	
	
	public static FacesContext getFacesContext() { 
		
		try {
			return FacesContext.getCurrentInstance();
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		}
		
		return null;
	}
	public static Application getApplication() { 
		
		try {
			
			FacesContext facesContext = JSFHelper.getFacesContext();
			
			if (facesContext == null) {
				return null;
			}
			
			return facesContext.getApplication();
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		} 
		
		return null;
	}
	public static NavigationHandler getNavigationHandler(){
		
		try {
			
			Application application = JSFHelper.getApplication();
			if (application == null) {
				return null;
			}
			
			return application.getNavigationHandler();
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		}
		
		return null;
	}
	public static UIViewRoot getUIViewRoot(){ 
		
		try {
			
			FacesContext facesContext = JSFHelper.getFacesContext();
			
			if (facesContext == null) {
				return null;
			}
			
			return facesContext.getViewRoot();
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		} 
		
		return null;
	}
	public static ExternalContext getExternalContext(){
		
		try {
			
			FacesContext facesContext = JSFHelper.getFacesContext();
			if (facesContext == null) {
				return null;
			}
			return facesContext.getExternalContext();
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		} 
		
		return null;
	}
	public static HttpServletRequest getHttpServletRequest() {
		
		try {
			
			ExternalContext externalContext = JSFHelper.getExternalContext();
			if (externalContext == null) {
				return null;
			}
			
			return (HttpServletRequest)externalContext.getRequest();
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		} 
		
		return null;
	}
	public static Locale getLocale(){ 
		
		try {
			
			UIViewRoot uiViewRoot = JSFHelper.getUIViewRoot();
			if (uiViewRoot == null) {
				return Language.getDefault().getLocale();
			}
			
			return uiViewRoot.getLocale();
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		} 
		
		return null;
	}
	
	
	public static String getExpressionLanguage(Class<?> clazz) {
		try {
			return "#{" + StringHelper.getI18N(clazz) + "}";
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		}
		return null;
	}
	public static <T> T getContainerInstance(Class<T> clazz) {
		try {
			FacesContext facesContext = JSFHelper.getFacesContext();
			if (facesContext == null) {
				return null;
			}
			Application application = JSFHelper.getApplication();
			if (application == null) {
				return null;
			}
			return application.evaluateExpressionGet(facesContext, JSFHelper.getExpressionLanguage(clazz), clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static Map<String, Object> getSessionMap(){
		
		try {
			
			ExternalContext externalContext = JSFHelper.getExternalContext();
			if (externalContext == null) {
				return null;
			}
			return externalContext.getSessionMap();
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		} 
		
		return null;
	}
	public static void putSessionAttribute(Map<? extends String, ? extends Object> mapValues){
		
		try {
			
			Map<String, Object> sessionMap = JSFHelper.getSessionMap();
			
			if (sessionMap == null) {
				return;
			}
			
			sessionMap.putAll(mapValues);
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		} 
		
	}
	public static void putSessionAttribute(String key, Object value){
		
		try {
			
			key = key == null ? key : key.replaceAll(" ", "");
			
			if (StringHelper.isNullOrEmpty(key)) {
				return;
			}
			
			Map<String, Object> sessionMap = JSFHelper.getSessionMap();
			if (sessionMap == null) {
				return;
			}
			
			sessionMap.put(key, value);
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		} 
	}
	public static void removeSessionAttribute(String key) {
		
		try {
			
			key = key == null ? key : key.replaceAll(" ", "");
			
			if (StringHelper.isNullOrEmpty(key)) {
				return;
			}
			
			Map<String, Object> sessionMap = JSFHelper.getSessionMap();
			if (sessionMap == null) {
				return;
			}
			
			sessionMap.remove(key);
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		} 
		
	}
	public static Object getSessionAtribute(String key){
		
		try {
			
			key = key == null ? key : key.replaceAll(" ", "");
			
			if (StringHelper.isNullOrEmpty(key)) {
				return null;
			}
			
			Map<String, Object> sessionMap = JSFHelper.getSessionMap();
			if (sessionMap == null) {
				return null;
			}
			
			return sessionMap.get(key);
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		} 
		
		return null;
	}
	
	
	public static Map<String, Object> getApplicationMap(){
		
		try {
			
			ExternalContext externalContext = JSFHelper.getExternalContext();
			if (externalContext == null) {
				return new HashMap<String, Object>();
			}
			
			Map<String, Object> applicationMap = externalContext.getApplicationMap();
			
			if (!MapHelper.isNullOrEmpty(applicationMap)) {
				return applicationMap;
			}
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		} 
		
		return new HashMap<String, Object>();
	}
	public static void putApplicationAttribute(Map<String, Object> mapValues){
		
		try {
			
			if (MapHelper.isNullOrEmpty(mapValues)) {
				mapValues = new HashMap<>();
			}
			
			Map<String, Object> applicationMap = JSFHelper.getApplicationMap();
			if (applicationMap == null) {
				return;
			}
			
			applicationMap.putAll(mapValues);
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		} 
		
	}
	public static void putApplicationAttribute(String key, Object value){
		
		try {
			
			key = key == null ? key : key.replaceAll(" ", "");
			
			if (StringHelper.isNullOrEmpty(key)) {
				return;
			}
			
			Map<String, Object> applicationMap = JSFHelper.getApplicationMap();
			if (applicationMap == null) {
				return;
			}
			
			applicationMap.put(key, value);
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		} 
		
	}
	public static void removeApplicationAttribute(String key) {
		
		try {
			
			key = key == null ? key : key.replaceAll(" ", "");
			
			if (StringHelper.isNullOrEmpty(key)) {
				return;
			}
			
			Map<String, Object> applicationMap = JSFHelper.getApplicationMap();
			if (applicationMap == null) {
				return;
			}
			
			applicationMap.remove(key);
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		} 
		
	}
	public static Object getApplicationAtribute(String key){
		
		try {
			
			key = key == null ? key : key.replaceAll(" ", "");
			
			if (StringHelper.isNullOrEmpty(key)) {
				return null;
			}
			
			Map<String, Object> applicationMap = JSFHelper.getApplicationMap();
			if (applicationMap == null) {
				return null;
			}
			
			return applicationMap.get(key);
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		} 
		
		return null;
	}
	
	
	public static void handleNavigation(String outcome){
		
		try {
			
			JSFHelper.handleNavigation(null, outcome);
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		}
		
	}
	public static void handleNavigation(String fromAction, String outcome){
		
		try {
			
			JSFHelper.handleNavigation(JSFHelper.getFacesContext(), fromAction, outcome);
			
		} catch (Exception e) {
			JSFHelper.logger.warn(e.getMessage());
		}
		
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
			JSFHelper.logger.warn(e.getMessage());
		}
	}
}
