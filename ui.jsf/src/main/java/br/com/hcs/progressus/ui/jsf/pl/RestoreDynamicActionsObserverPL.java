package br.com.hcs.progressus.ui.jsf.pl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.ResponseStateManager;
import javax.servlet.http.HttpServletRequest;

import br.com.hcs.progressus.helper.ConfigurationHelper;
import br.com.hcs.progressus.ui.jsf.helper.JSFHelper;

import com.sun.faces.RIConstants;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.ComponentStruct;

public class RestoreDynamicActionsObserverPL implements PhaseListener { 

    private static final long serialVersionUID = -4634057340724844798L;
	
    

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
 
        ResponseStateManager responseStateManager =
    		RenderKitUtils.getResponseStateManager(event.getFacesContext(), ConfigurationHelper.UI_WEB_DYNAMIC_ACTIONS_RENDERKIT);

        HttpServletRequest httpServletRequest = JSFHelper.getHttpServletRequest();

        Object[] rawState = (Object[]) 
    		responseStateManager
    			.getState(
					event.getFacesContext(), 
					httpServletRequest.getRequestURI().replaceFirst(httpServletRequest.getContextPath(), "").split("\\?")[0]
				);
        
        if (rawState == null) {
            return;
        }

        @SuppressWarnings("unchecked")
		Map<String, Object> state = (Map<String,Object>) rawState[1];

        if(state == null){
            return;
        }

        @SuppressWarnings("unchecked")
		List<Object> savedActions = (List<Object>) state.get(RIConstants.DYNAMIC_ACTIONS);

        if(savedActions == null){
            return;
        }

        Iterator<Object> iterator = savedActions.iterator();
        while(iterator.hasNext()) {
        	
            Object object = iterator.next();
            ComponentStruct action = new ComponentStruct();
            action.restoreState(event.getFacesContext(), object); 

            if(ComponentStruct.ADD.equals(action.action)){
                continue;
            }

            if (action.clientId.startsWith(ConfigurationHelper.UI_WEB_MENU_ID_PREFIX) || 
            	action.clientId.startsWith(ConfigurationHelper.UI_WEB_VIEW_ID_PREFIX) ||
            	action.clientId.startsWith(ConfigurationHelper.UI_WEB_PERMISSION_ID_PREFIX) ||
            	action.clientId.startsWith(ConfigurationHelper.UI_WEB_ITEM_MENU_ID_PREFIX)) {             
                iterator.remove();
            }           
        }
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        
    }
}

