package br.com.hcs.progressus.ui.jsf.mb.session;

import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.Getter;
import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.hcs.progressus.enumerator.Language;
import br.com.hcs.progressus.enumerator.Theme;
import br.com.hcs.progressus.jpa.entity.UserEntity;
import br.com.hcs.progressus.ui.jsf.mb.common.ProgressusMB;

@SessionScoped
@ManagedBean
public class SessionMB extends ProgressusMB<SessionMB> {
	
	
	private static final long serialVersionUID = -6271401735990466088L;
	private static final Logger logger = LoggerFactory.getLogger(SessionMB.class);
	
	
	@Setter
	private Language language;
	
	@Getter
	@Setter
	private Theme theme;
	
	@Getter
	@Setter
 	private UserEntity user;
	
	
	public SessionMB() {
		super(SessionMB.class);
	}
	
	
	@Override
	public void init() { }
	
	public Language getLanguage() {
		
		try {
			
			if (this.language == null) {
				this.setLanguage(Language.getDefault());
			}
			
			return this.language;
			
		} catch (Exception e) {
			SessionMB.logger.warn(e.getMessage());
		}
		
		return Language.getDefault();
	}
	
	@Override
	public String getCss() {
		return "style.css";
	}
	
	@Override
	public String getThemeName() {
		
		try {
			
			if (this.getTheme() == null) {

				this.setTheme(Theme.getDefault());
			}
			
			return this.getTheme().getName();
			
		} catch (Exception e) {
			SessionMB.logger.warn(e.getMessage());
		}
		
		return Theme.getDefault().getName();
	}
	
	@Override
	public Locale getLocale() {
		
		try {
			
			if (this.getLanguage() == null) {
				this.setLanguage(Language.getDefault());
			}
			
			return this.getLanguage().getLocale();
			
		} catch (Exception e) {
			SessionMB.logger.warn(e.getMessage());
		}
		
		return Language.getDefault().getLocale();
	}
}
