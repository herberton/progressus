package br.com.hcs.progressus.ui.jsf.mb;

import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.enumerator.SupportedLocale;
import br.com.hcs.progressus.enumerator.Theme;
import br.com.hcs.progressus.server.jpa.entity.UserEntity;

@Slf4j
@NoArgsConstructor
@SessionScoped
@ManagedBean
public class SessionMB extends ProgressusMB<SessionMB> {

	private static final long serialVersionUID = 8025634409824404561L;

	
	@Setter
	private UserEntity loggedInUser;
	
	
	@Override
	public void init() {
		
	}

	
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
	public Locale getLocale() {
		try {
			if (this.getLoggedInUser() == null) {
				return SupportedLocale.getDefault().getLocale();
			}
			if (this.getLoggedInUser().getPreference() == null) {
				return SupportedLocale.getDefault().getLocale();
			}
			return this.getLoggedInUser().getPreference().getSupportedLocale().getLocale(); 
		} catch (Exception e) {
			SessionMB.log.error(e.getMessage(), e);
		}
		return SupportedLocale.getDefault().getLocale();
	}
	
	
	@Override
	public String getThemeName() {
		try {
			if (this.getLoggedInUser() == null) {
				return Theme.getDefault().toString();
			}
			if (this.getLoggedInUser().getPreference() == null) {
				return Theme.getDefault().toString();
			}
			return this.getLoggedInUser().getPreference().getTheme().toString();
		} catch (Exception e) {
			SessionMB.log.error(e.getMessage(), e);
		}
		return Theme.getDefault().toString();
	}
}
