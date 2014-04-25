package br.com.hcs.progressus.ui.jsf.pl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.ResponseStateManager;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.ui.jsf.helper.JSFHelper;

import com.sun.faces.RIConstants;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.ComponentStruct;

@Slf4j
public class RestoreDynamicActionsObserverPL implements PhaseListener, Serializable {

	private static final long serialVersionUID = 2570874698055551508L;
	
	
	@Override
	public void afterPhase(PhaseEvent event) {
		
		try {
			
			if (event == null) {
				return;
			}
			
			FacesContext facesContext = event.getFacesContext(); 
			
			if (facesContext == null) {
				return;
			}
			
			ResponseStateManager responseStateManager = 
				RenderKitUtils
					.getResponseStateManager(
						facesContext, 
						Setting.WEB_DYNAMIC_ACTIONS_RENDERKIT.toString()
					);
			
			if (responseStateManager == null) {
				return;
			}
			
			HttpServletRequest httpServletRequest = JSFHelper.getHttpServletRequest();
			if (httpServletRequest == null) {
				return;
			}
			
			String contextPath = httpServletRequest.getContextPath();
			String requestURI = httpServletRequest.getRequestURI();
			
			if (StringHelper.isNullOrEmpty(requestURI)) {
				requestURI = "";
			}
			
			if (StringHelper.isNullOrEmpty(contextPath)) {
				contextPath = "";
			}
			
			Object state = 
				responseStateManager
					.getState (
						facesContext, 
						requestURI.replaceFirst(contextPath, "").split("\\?")[0]
					);
			
			
			if (state == null) {
				return;
			}
			
			Object[] stateArray = (Object[])state;
			
			if (stateArray == null || stateArray.length <= 0 || !(stateArray[1] instanceof Map)) {
			    return;
			}

			@SuppressWarnings("unchecked")
			Map<String, Object> stateMap = (Map<String,Object>) stateArray[1];

			if(stateMap == null){
			    return;
			}
			
			state = stateMap.get(RIConstants.DYNAMIC_ACTIONS);
			
			if (state == null || !(state instanceof List)) {
				return;
			}
			
			@SuppressWarnings("unchecked")
			List<Object> savedActionList = (List<Object>) state;
 
			Iterator<Object> iterator = savedActionList.iterator();
			while(iterator.hasNext()) {
				
			    Object object = iterator.next();
			    
			    if (object == null) {
					continue;
				}
			    
			    ComponentStruct action = new ComponentStruct();
			    
			    try {
					action.restoreState(facesContext, object);
				} catch (Exception e) {
					RestoreDynamicActionsObserverPL.log.error(e.getMessage(), e);
					continue;
				} 
			    
			    if(ComponentStruct.ADD.equals(action.action)){
			        continue;
			    }
			    
			    if (StringHelper.isNullOrEmpty(action.clientId)) {
					continue;
				}

	            if (action.clientId.startsWith(Setting.WEB_MENU_ID_PREFIX.toString()) || 
	            	action.clientId.startsWith(Setting.WEB_VIEW_ID_PREFIX.toString()) ||
	            	action.clientId.startsWith(Setting.WEB_PERMISSION_ID_PREFIX.toString()) ||
	            	action.clientId.startsWith(Setting.WEB_ITEM_MENU_ID_PREFIX.toString())) {
	                iterator.remove();
	            }
			}
			
		} catch (Exception e) {
			RestoreDynamicActionsObserverPL.log.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void beforePhase(PhaseEvent event) {
		try {
			RestoreDynamicActionsObserverPL.log.trace("RestoreDynamicActionsObserverPL.beforePhase(PhaseEvent event)", event);
		} catch (Exception e) {
			RestoreDynamicActionsObserverPL.log.error(e.getMessage(), e);
		}
	}

	@Override
	public PhaseId getPhaseId() {
		try {
			return PhaseId.RESTORE_VIEW;
		} catch (Exception e) {
			RestoreDynamicActionsObserverPL.log.error(e.getMessage(), e);
		}
		return null;
	}

}
