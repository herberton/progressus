package br.com.hcs.progressus.ui.jsf.mb.common;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.hcs.progressus.ui.jsf.mb.session.SessionMB;

public abstract class ProgressusMB<T extends ProgressusMB<T>> implements Serializable {

	
	private static final long serialVersionUID = 3997670404725951348L;
	private static final Logger logger = LoggerFactory.getLogger(ProgressusMB.class);
	
	
	public abstract void init();
	
	
	@Getter
	@Setter(AccessLevel.PRIVATE)
	private Class<T> clazz;
	
	
	public ProgressusMB(Class<T> clazz){
		super();
		this.setClazz(clazz);
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
		
	public T getInstance() {
		
		try {
			
			return ProgressusMB.getInstance(this.getClazz());
		
		} catch (Exception e) {
			ProgressusMB.logger.warn(e.getMessage());
		}
		
		return null;
	}

	public static <T extends ProgressusMB<T>> T getInstance(Class<T> clazz) {
		
		try {
			
			return clazz.newInstance();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
}
