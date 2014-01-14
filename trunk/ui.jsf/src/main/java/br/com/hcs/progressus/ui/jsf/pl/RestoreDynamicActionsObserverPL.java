package br.com.hcs.progressus.ui.jsf.pl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.ResponseStateManager;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.helper.ConfigurationHelper;
import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.ui.jsf.helper.JSFHelper;

import com.sun.faces.RIConstants;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.ComponentStruct;

public class RestoreDynamicActionsObserverPL implements PhaseListener { 

    private static final long serialVersionUID = -4634057340724844798L;
    private static final Logger logger = LoggerFactory.getLogger(RestoreDynamicActionsObserverPL.class);
	
    

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
 
        try {
        	
        	FacesContext facesContext = event.getFacesContext();
        	
        	if (ObjectHelper.isNullOrEmpty(facesContext)) {
				return;
			}
        	
			ResponseStateManager responseStateManager =
				RenderKitUtils
					.getResponseStateManager(
						facesContext, 
						ConfigurationHelper.UI_WEB_DYNAMIC_ACTIONS_RENDERKIT
					);
			
			if (ObjectHelper.isNullOrEmpty(responseStateManager)) {
				return;
			}
			
			HttpServletRequest httpServletRequest = JSFHelper.getHttpServletRequest();
			
			if (ObjectHelper.isNullOrEmpty(httpServletRequest)) {
				return;
			}
			
			String 
				requestURI = httpServletRequest.getRequestURI(),
				contextPath = httpServletRequest.getContextPath();
			
			if (StringHelper.isNullOrEmpty(requestURI) || StringHelper.isNullOrEmpty(contextPath)) {
				return;
			}
			
			Object[] rawState = (Object[]) 
				responseStateManager
					.getState(
						facesContext, 
						requestURI.replaceFirst(contextPath, "").split("\\?")[0]
					);
			
			if (CollectionHelper.isNullOrEmpty(rawState)) {
			    return;
			}
			
			@SuppressWarnings("unchecked")
			Map<String, Object> state = (Map<String,Object>) rawState[1];
			
			if(ObjectHelper.isNullOrEmpty(state)){
			    return;
			}
			
			Object savedActionObject = state.get(RIConstants.DYNAMIC_ACTIONS);
			
			if (ObjectHelper.isNullOrEmpty(savedActionObject) || !(savedActionObject instanceof List)) {
				return;
			}
			
			@SuppressWarnings("unchecked")
			List<Object> savedActionList = (List<Object>) savedActionObject;

			if(CollectionHelper.isNullOrEmpty(savedActionList)){
			    return;
			}

			Iterator<Object> iterator = savedActionList.iterator();
			while(iterator.hasNext()) {
				
			    Object object = iterator.next();
			    
			    if (ObjectHelper.isNullOrEmpty(object)) {
					continue;
				}
			    
			    ComponentStruct action = new ComponentStruct();
			    action.restoreState(facesContext, object); 

			    if (ObjectHelper.isNullOrEmpty(action)) {
					continue;
				}
			    
			    if(ComponentStruct.ADD.equals(action.action)){
			        continue;
			    }			    
			    
			    if (StringHelper.isNullOrEmpty(action.clientId)) {
					continue;
				}
			    
			    if (action.clientId.startsWith(ConfigurationHelper.UI_WEB_MENU_ID_PREFIX) || 
			    	action.clientId.startsWith(ConfigurationHelper.UI_WEB_VIEW_ID_PREFIX) ||
			    	action.clientId.startsWith(ConfigurationHelper.UI_WEB_PERMISSION_ID_PREFIX) ||
			    	action.clientId.startsWith(ConfigurationHelper.UI_WEB_ITEM_MENU_ID_PREFIX)) {             
			        iterator.remove();
			    }           
			}
			
		} catch (Exception e) {
			RestoreDynamicActionsObserverPL.logger.warn(e.getMessage());
		}
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        
    }
}

