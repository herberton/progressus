package br.com.hcs.progressus.ui.jsf.mb.session;

import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.hcs.progressus.enumerator.Language;
import br.com.hcs.progressus.enumerator.Theme;
import br.com.hcs.progressus.jpa.entity.UserEntity;
import br.com.hcs.progressus.ui.jsf.mb.common.ProgressusMB;

@SessionScoped
@ManagedBean
public class SessionMB extends ProgressusMB<SessionMB> {
	
	private static final long serialVersionUID = -6271401735990466088L;
	
	
	private UserEntity user;
	
	
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	@Override
	public String getCss() {
		return "style.css";
	}
	@Override
	public Locale getLocale() {
		return Language.getDefault().getLocale();
	}
	@Override
	public String getTheme() {
		return Theme.getDefault().getName();
	}
	
	public SessionMB() {
		super(SessionMB.class);
	}
	
	
	@Override
	public void init() { }
}
