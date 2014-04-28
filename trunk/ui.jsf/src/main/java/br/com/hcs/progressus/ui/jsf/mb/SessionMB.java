package br.com.hcs.progressus.ui.jsf.mb;

import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.enumerator.SupportedLocale;
import br.com.hcs.progressus.enumerator.Template;
import br.com.hcs.progressus.enumerator.Theme;
import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.server.jpa.entity.UserEntity;

@Slf4j
@NoArgsConstructor
@SessionScoped
@ManagedBean
public class SessionMB extends ProgressusMB<SessionMB> {

	private static final long serialVersionUID = 8025634409824404561L;

	
	private UserEntity loggedInUser;
	@Setter
	private SupportedLocale supportedLocale;
	@Setter
	private Template template;
	@Setter
	private Theme theme;
	
	
	@Override
	public void init() { }

	
	@Override
	public UserEntity getLoggedInUser() {
		try {
			return this.loggedInUser; 
		} catch (Exception e) {
			SessionMB.log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	@Override
	public void setLoggedInUser(UserEntity loggedInUser) {
		try {
			
			this.loggedInUser = loggedInUser;
			
			if (ObjectHelper.isNullOrEmpty(this.getLoggedInUser()) || 
				ObjectHelper.isNullOrEmpty(this.getLoggedInUser().getPreference())) {
				return;
			}
			
			this.setSupportedLocale(this.getLoggedInUser().getPreference().getSupportedLocale());
			this.setTemplate(this.getLoggedInUser().getPreference().getTemplate());
			this.setTheme(this.getLoggedInUser().getPreference().getTheme());
		
		} catch (Exception e) {
			SessionMB.log.error(e.getMessage(), e);
		}
	}
	
	
	@Override
	public SupportedLocale getSupportedLocale() {
		try {
			return 
				 this.supportedLocale == null ? 
					SupportedLocale.getDefault() : 
					this.supportedLocale;
		} catch (Exception e) {
			SessionMB.log.error(e.getMessage(), e);
		}
		return SupportedLocale.getDefault();
	}
	
	@Override
	public Template getTemplate() {
		try {
			return 
				this.template == null ?
					Template.getDefault() :
					this.template;
		} catch (Exception e) {
			SessionMB.log.error(e.getMessage(), e);
		}
		return Template.getDefault();
	}
	
	@Override
	public Theme getTheme() {
		try {
			return 
				this.theme == null ?
					this.getTemplate().getDefaultTheme() :
					this.theme;
		} catch (Exception e) {
			SessionMB.log.error(e.getMessage(), e);
		}
		return Template.getDefault().getDefaultTheme();
	}
	
	
	@Override
	public Locale getLocale() {
		try {
			return this.getSupportedLocale().getLocale();
		} catch (Exception e) {
			SessionMB.log.error(e.getMessage(), e);
		}
		return SupportedLocale.getDefault().getLocale();
	}
	
	
	@Override
	public String getThemeName() {
		try {
			return this.getTheme().toString();
		} catch (Exception e) {
			SessionMB.log.error(e.getMessage(), e);
		}
		return Template.getDefault().getDefaultTheme().toString();
	}
	
	@Override
	public String getTemplatePage() {
		try {
			return this.getTemplate().toString();
		} catch (Exception e) {
			SessionMB.log.error(e.getMessage(), e);
		}
		return Template.getDefault().toString();
	}
	
	public String getTemplateCrudPage() {
		try {
			return this.getTemplate().getCrudPage();
		} catch (Exception e) {
			SessionMB.log.error(e.getMessage(), e);
		}
		return Template.getDefault().getCrudPage();
	}
}
