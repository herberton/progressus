package br.com.hcs.progressus.ui.jsf.mb.common;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.to.common.ProgressusTO;
import br.com.hcs.progressus.ui.jsf.helper.JSFHelper;
import br.com.hcs.progressus.ui.jsf.mb.session.SessionMB;

public abstract class ProgressusMB<T extends ProgressusMB<T>>
	extends
		ProgressusTO<T>
	implements 
		Serializable
{

	
	private static final long serialVersionUID = 3997670404725951348L;
	private static final Logger logger = LoggerFactory.getLogger(ProgressusMB.class);
	
	
	public abstract void init();
	
	
	public ProgressusMB(){
		super();
	}
	
	
	@PostConstruct
	public void preInit() {
		
		try {
			
			this.init();
			
		} catch (Exception e) {
			ProgressusMB.logger.warn(e.getMessage());
		}
		
	}
	
	
	public SessionMB getSession() {
		
		try {
			
			return SessionMB.getInstance(SessionMB.class);
		
		} catch (Exception e) {
			ProgressusMB.logger.warn(e.getMessage());
		}
		
		return null;
	}
	
	public String getCss() {
		
		try {
			
			SessionMB session = this.getSession();
			
			if (session == null) {
				return "";
			}
			
			return session.getCss();
		
		} catch (Exception e) {
			ProgressusMB.logger.warn(e.getMessage());
		}
		
		return "";
		
	}
	
	public Locale getLocale() {
		
		try {
			
			SessionMB session = this.getSession();
			
			if (session == null) {
				return null;
			}
			
			return session.getLocale();
			
		} catch (Exception e) {
			ProgressusMB.logger.warn(e.getMessage());
		}
		
		return null;
	}
	
	public String getThemeName() {
		
		try {
			
			SessionMB session = this.getSession();
			
			if (session == null) {
				return "";
			}
			
			
			return session.getThemeName();
		
		} catch (Exception e) {
			ProgressusMB.logger.warn(e.getMessage());
		}
		
		return "";
		
	}
	
	
	public static <X extends ProgressusTO<X>> X getInstance(Class<X> clazz) {
		
		if (ObjectHelper.isNullOrEmpty(clazz)) {
			return null;
		}
		
		try {
			
			X object = JSFHelper.getContainerInstance(clazz);
			
			if (!ObjectHelper.isNullOrEmpty(object)) {
				return object;
			}
			
		} catch (Exception e) {
			ProgressusMB.logger.warn(e.getMessage());
		}
		
		try {
			
			X to = ProgressusTO.getInstance(clazz);
			
			if (ObjectHelper.isNullOrEmpty(to)) {
				return null;
			}
			
			return to;
			
		} catch (Exception e) {
			ProgressusMB.logger.warn(e.getMessage());
		}
		
		return null;
		
	}
}
