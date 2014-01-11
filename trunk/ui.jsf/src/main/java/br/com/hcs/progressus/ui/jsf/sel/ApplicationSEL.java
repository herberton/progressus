package br.com.hcs.progressus.ui.jsf.sel;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import lombok.Getter;
import br.com.hcs.progressus.client.bo.sb.SystemBORemote;
import br.com.hcs.progressus.ui.jsf.helper.JSFHelper;

public class ApplicationSEL implements SystemEventListener, Serializable {

	private static final long serialVersionUID = 1963329710530192750L;

	@Getter
	@EJB
	private SystemBORemote systemBO;
	
	@Override
	public boolean isListenerForSource(Object source) {
		return (source instanceof Application);
	}
	
	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		if(event instanceof PostConstructApplicationEvent) {
			try {
				JSFHelper.putApplicationAttribute("menuList", this.getSystemBO().getMenuList(true));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

