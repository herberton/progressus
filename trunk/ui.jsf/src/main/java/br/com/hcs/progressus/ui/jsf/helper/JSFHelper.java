package br.com.hcs.progressus.ui.jsf.helper;

import java.io.Serializable;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.annotation.View;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.ui.jsf.mb.ProgressusMB;

@Slf4j
public final class JSFHelper implements Serializable {

	private static final long serialVersionUID = 7103265093360138014L;	
	
	
	public static final FacesContext getFacesContext() { 
		try {
			return FacesContext.getCurrentInstance();
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return null;
	}
		
	public static final ExternalContext getExternalContext(){
		try {
			FacesContext facesContext = JSFHelper.getFacesContext();
			if (facesContext == null) {
				return null;
			}
			return facesContext.getExternalContext();
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return null;
	}
		
	public static final HttpServletRequest getHttpServletRequest() {
		try {
			ExternalContext externalContext = JSFHelper.getExternalContext();
			if (externalContext == null) {
				return null;
			}
			return (HttpServletRequest)externalContext.getRequest();
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return null;
	}
		
	public static final Application getApplication() { 
		try {
			FacesContext facesContext = JSFHelper.getFacesContext();
			if (facesContext == null) {
				return null;
			}
			return facesContext.getApplication();
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return null;
	}
		
	public static final NavigationHandler getNavigationHandler(){
		try {
			Application application = JSFHelper.getApplication();
			if (application == null) {
				return null;
			}
			return application.getNavigationHandler();
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return null;
	}
		
	public static final UIViewRoot getUIViewRoot(){ 
		try {
			FacesContext facesContext = JSFHelper.getFacesContext();
			if (facesContext == null) {
				return null;
			}
			return facesContext.getViewRoot();
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		} 
		return null;
	}
	
	public static final ExpressionFactory getExpressionFactory(){
		try {
			Application application = JSFHelper.getApplication();
			if (application == null) {
				return null;
			}
			return application.getExpressionFactory();
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static final ELContext getELContext(){
		try {
			FacesContext facesContext = JSFHelper.getFacesContext();
			if (facesContext == null) {
				return null;
			}
			return facesContext.getELContext();
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static final Map<String, Object> getApplicationMap(){
		try {
			ExternalContext externalContext = JSFHelper.getExternalContext();
			if (externalContext == null) {
				return null;
			}
			return externalContext.getApplicationMap();
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return null;
	}
	
	public static final Map<String, Object> getSessionMap(){
		try {
			ExternalContext externalContext = JSFHelper.getExternalContext();
			if (externalContext == null) {
				return null;
			}
			return externalContext.getSessionMap();
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	public static final <T> T executeExpressionLanguage(String expressionLanguage, Class<T> clazz) {
		try {
			if (StringHelper.isNullOrEmpty(expressionLanguage)) {
				return null;
			}
			FacesContext facesContext = JSFHelper.getFacesContext();
			if (facesContext == null) {
				return null;
			}
			Application application = JSFHelper.getApplication();
			if (application == null) {
				return null;
			}
			return application.evaluateExpressionGet(facesContext, expressionLanguage, clazz);
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return null;
	}
		
	
	public static final <T extends ProgressusMB<T>> void handleNavigation(T managedBean) {
		try {
			JSFHelper.handleNavigation(JSFHelper.getURL(managedBean));
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final <T extends ProgressusMB<T>> void handleNavigation(String fromAction, T managedBean) {
		try {
			JSFHelper.handleNavigation(fromAction, JSFHelper.getURL(managedBean));
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final <T extends ProgressusMB<T>> void handleNavigation(FacesContext facesContext, String fromAction, T managedBean) {
		try {
			JSFHelper.handleNavigation(facesContext, fromAction, JSFHelper.getURL(managedBean));
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
	}
		
	public static final <T extends ProgressusMB<T>> void handleNavigation(Class<T> clazz) {
		try {
			JSFHelper.handleNavigation(JSFHelper.getURL(clazz));
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final <T extends ProgressusMB<T>> void handleNavigation(String fromAction, Class<T> clazz) {
		try {
			JSFHelper.handleNavigation(fromAction, JSFHelper.getURL(clazz));
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final <T extends ProgressusMB<T>> void handleNavigation(FacesContext facesContext, String fromAction, Class<T> clazz) {
		try {
			JSFHelper.handleNavigation(facesContext, fromAction, JSFHelper.getURL(clazz));
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
	}
		
	public static final void handleNavigation(View view) {
		try {
			JSFHelper.handleNavigation(JSFHelper.getURL(view));
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void handleNavigation(String fromAction, View view) {
		try {
			JSFHelper.handleNavigation(fromAction, JSFHelper.getURL(view));
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void handleNavigation(FacesContext facesContext, String fromAction, View view) {
		try {
			JSFHelper.handleNavigation(facesContext, fromAction, JSFHelper.getURL(view));
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void handleNavigation(String outcome){
		try {
			JSFHelper.handleNavigation(null, outcome);
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void handleNavigation(String fromAction, String outcome){
		try {
			JSFHelper.handleNavigation(JSFHelper.getFacesContext(), fromAction, outcome);
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
	}
	
	public static final void handleNavigation(FacesContext facesContext, String fromAction, String outcome){
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
			JSFHelper.log.error(e.getMessage(), e);
		}
	}
	
	
	public static final <T extends ProgressusMB<T>> String getURL(T managedBean) {
		try {
			return JSFHelper.getURL(managedBean, true, false);
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	public static final <T extends ProgressusMB<T>> String getURL(T managedBean, boolean facesRedirect) {
		try {
			return JSFHelper.getURL(managedBean, facesRedirect, false);
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	public static final <T extends ProgressusMB<T>> String getURL(T managedBean, boolean facesRedirect, boolean windowId) {
		
		try {
			
			if (managedBean == null) {
				return "";
			}
			
			if (!managedBean.getClass().isAnnotationPresent(View.class)) {
				return "";
			}
			
			return JSFHelper.getURL(managedBean.getClass().getAnnotation(View.class), facesRedirect, windowId);
			
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		
		return "";
	}
	
	public static final <T extends ProgressusMB<T>> String getURL(Class<T> clazz) {
		try {
			return JSFHelper.getURL(clazz, true, false);
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	public static final <T extends ProgressusMB<T>> String getURL(Class<T> clazz, boolean facesRedirect) {
		try {
			return JSFHelper.getURL(clazz, facesRedirect, false);
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	public static final <T extends ProgressusMB<T>> String getURL(Class<T> clazz, boolean facesRedirect, boolean windowId) {
		
		try {
			
			if (clazz == null) {
				return "";
			}
			
			if (!clazz.isAnnotationPresent(View.class)) {
				return "";
			}
			
			return JSFHelper.getURL(clazz.getAnnotation(View.class), facesRedirect, windowId);
			
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		
		return "";
	}
	
	public static final String getURL(View view) {
		try {
			return JSFHelper.getURL(view, true, false);
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	public static final String getURL(View view, boolean facesRedirect) {
		try {
			return JSFHelper.getURL(view, facesRedirect, false);
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	public static final String getURL(View view, boolean facesRedirect, boolean windowId) {
		try {
			
			if (view == null) {
				return "";
			}
			
			if (StringHelper.isNullOrEmpty(view.name())) {
				return "";
			}
			
			if (StringHelper.isNullOrEmpty(view.module())) {
				return JSFHelper.getURL(view.name(), facesRedirect, windowId);
			}
			
			return JSFHelper.getURL(view.module() + "/" + view.name(), facesRedirect, windowId);
			
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	public static final String getURL(String page) {
		try {
			return JSFHelper.getURL(page, true, false);
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	
	public static final String getURL(String page, boolean facesRedirect) {
		try {
			return JSFHelper.getURL(page, facesRedirect, false);
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return "";
	}
	
	public static final String getURL(String page, boolean facesRedirect, boolean windowId) {
		try {
			if (StringHelper.isNullOrEmpty(page)) {
				page = "index";
			}
			return "/" + page + "?faces-redirect=" + facesRedirect + "&windowId=" + windowId;
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return "";
	}


	public static final void invalidateSession(){
		try {
			ExternalContext externalContext = JSFHelper.getExternalContext();
			if (externalContext == null) {
				return;
			}
			externalContext.invalidateSession();
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
	}


	public static void putApplicationAttribute(String key, Object value){
		try {
			Map<String, Object> applicationMap = JSFHelper.getApplicationMap();
			if (applicationMap == null) {
				return;
			}
			applicationMap.put(key, value);
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
	}
		
	public static final <T> T getApplicationAttribute(String key, Class<T> clazz) {
		try {
			if (clazz == null) {
				return null;
			}
			if (StringHelper.isNullOrEmpty(key)) {
				return null;
			}
			Map<String, Object> applicationMap = JSFHelper.getApplicationMap();
			if (applicationMap == null) {
				return null;
			}
			return clazz.cast(applicationMap.get(key));
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return null;
	}

	
	public static final void putSessionAttribute(String key, Object value) {
		try {
			if (StringHelper.isNullOrEmpty(key)) {
				return;
			}
			Map<String, Object> sessionMap = JSFHelper.getSessionMap();
			if (sessionMap == null) {
				return;
			}
			sessionMap.put(key, value);
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
	}
		
	public static final <T> T getSessionAtribute(String key, Class<T> clazz) {
		try {
			if (clazz == null) {
				return null;
			}
			if (StringHelper.isNullOrEmpty(key)) {
				return null;
			}
			Map<String, Object> sessionMap = JSFHelper.getSessionMap();
			if (sessionMap == null) {
				return null;
			}
			return clazz.cast(sessionMap.get(key));
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return null;
	}

	
	public static final MethodExpression createMethodExpression(String expression, Class<?> typeReturn, Class<?>[] typeParameters){
		try {
			ExpressionFactory expressionFactory = JSFHelper.getExpressionFactory();
			if (expressionFactory == null) {
				return null;
			}
			ELContext elContext = JSFHelper.getELContext();
			if (elContext == null) {
				return null;
			}
			return expressionFactory.createMethodExpression(elContext, expression, typeReturn, typeParameters);
		} catch (Exception e) {
			JSFHelper.log.error(e.getMessage(), e);
		}
		return null;
	}
}
