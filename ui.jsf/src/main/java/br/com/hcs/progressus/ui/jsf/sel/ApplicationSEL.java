package br.com.hcs.progressus.ui.jsf.sel;

import java.io.Serializable;

import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.client.ejb.sb.bo.process.ApplicationBOLocal;
import br.com.hcs.progressus.client.helper.EJBHelper;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.ui.jsf.helper.ClassHelper;
import br.com.hcs.progressus.ui.jsf.helper.JSFHelper;

@Slf4j
public class ApplicationSEL implements SystemEventListener, Serializable {
	
	private static final long serialVersionUID = -6222023812938367933L;

	private static ApplicationBOLocal applicationBO = null;
	
	@Override
	public boolean isListenerForSource(Object source) {
		try {
			return (source instanceof Application);
		} catch (Exception e) {
			ApplicationSEL.log.error(e.getMessage(), e);
		}
		return false;
	}
	
	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		try {
			ApplicationSEL.log.trace("ApplicationSEL.processEvent()", event);
			if(event instanceof PostConstructApplicationEvent) {
				this.postConstructApplicationEvent();
			} else if(event instanceof PreDestroyApplicationEvent){
				this.preDestroyApplicationEvent();
			}
		} catch (Exception e) {
			ApplicationSEL.log.error(e.getMessage(), e);
		}
	}

	
	private void preDestroyApplicationEvent() {
		try {
			
		} catch (Exception e) {
			ApplicationSEL.log.error(e.getMessage(), e);
		}
	}

	private void postConstructApplicationEvent() {
		try {
			
			this.getApplicationBO().createMenuTree(ClassHelper.getViewClazzSet());
			this.getApplicationBO().createAdministratorUser();
			
			JSFHelper
				.putApplicationAttribute(
					Setting.WEB_APPLICATION_MENU_TREE_LIST.toString(), 
					this.getApplicationBO().selectMenuTreeList()
				);
			
		} catch (Exception e) {
			ApplicationSEL.log.error(e.getMessage(), e);
		}
	}
	
	
	private ApplicationBOLocal getApplicationBO() {
		try {
			if (ApplicationSEL.applicationBO == null) {
				try {
					ApplicationSEL.applicationBO = 
						(ApplicationBOLocal)new InitialContext().lookup(EJBHelper.getJNDIForLookup(ApplicationBOLocal.class));
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
			return ApplicationSEL.applicationBO;
		} catch (Exception e) {
			ApplicationSEL.log.error(e.getMessage(), e);
		}
		return null;
	}
	
}