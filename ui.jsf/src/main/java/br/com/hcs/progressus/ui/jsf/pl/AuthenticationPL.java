package br.com.hcs.progressus.ui.jsf.pl;

import java.io.Serializable;

import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.ui.jsf.helper.JSFHelper;
import br.com.hcs.progressus.ui.jsf.mb.ProgressusMB;
import br.com.hcs.progressus.ui.jsf.mb.SessionMB;


@Slf4j
public class AuthenticationPL implements PhaseListener, Serializable {

	private static final long serialVersionUID = 840473194218218333L;

	
	@Override
	public void afterPhase(PhaseEvent phaseEvent) {
		
		try {
			
			FacesContext facesContext = phaseEvent.getFacesContext();
			
			if(facesContext == null) {
				this.toLogin(facesContext);
				return;
			}
			
			UIViewRoot uiViewRoot = facesContext.getViewRoot();
			if (uiViewRoot == null) {
				this.toLogin(facesContext);
				return;
			}
			
			String viewId = uiViewRoot.getViewId();
			
			if (StringHelper.isNullOrEmpty(viewId)) {
				this.toLogin(facesContext);
				return;
			}
			
			if (viewId.contains("/contentRead.xhtml")) {
				return;
			}
			
			if (viewId.contains("/rfid/reader_impinj.xhtml")) {
				return;
			}
			
			ExternalContext externalContext = facesContext.getExternalContext();
			if (externalContext == null) {
				this.toLogin(facesContext);
				return;
			}
			
			Object object = externalContext.getSession(false);
			
			if (object == null || !(object instanceof HttpSession)) {

				if (viewId.contains("/login.xhtml")) {
					return;
				}
				
				this.toLogin(facesContext);
				return;
			}
			
			SessionMB sessionMB = ProgressusMB.getInstance(SessionMB.class);
			
			HttpSession httpSession = (HttpSession)externalContext.getSession(false);
			if (httpSession == null) {
				sessionMB.setLoggedInUser(null); 
			}
			
			if (sessionMB.getLoggedInUser() == null) {
				
				if(!viewId.contains("/login.xhtml")){
					this.toLogin(facesContext);
				}
				
				return;
			}
			
		} catch (Exception e) {
			AuthenticationPL.log.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void beforePhase(PhaseEvent event) {
		try {
			AuthenticationPL.log.trace("AuthenticationPL.beforePhase(PhaseEvent event)", event);
		} catch (Exception e) {
			AuthenticationPL.log.error(e.getMessage(), e);
		}
	}

	@Override
	public PhaseId getPhaseId() {
		try {
			return PhaseId.RESTORE_VIEW;
		} catch (Exception e) {
			AuthenticationPL.log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	private void toLogin(FacesContext facesContext) {
		try {
			JSFHelper.handleNavigation(facesContext, null, JSFHelper.getURL("login"));
		} catch (Exception e) {
			AuthenticationPL.log.error(e.getMessage(), e);
		}
	}
}

