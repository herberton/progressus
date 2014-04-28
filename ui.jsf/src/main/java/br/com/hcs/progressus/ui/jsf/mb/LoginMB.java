package br.com.hcs.progressus.ui.jsf.mb;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.client.ejb.sb.bo.process.AuthenticationBORemote;
import br.com.hcs.progressus.enumerator.SupportedLocale;
import br.com.hcs.progressus.exception.InvalidLoginOrPasswordException;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.server.jpa.entity.UserEntity;
import br.com.hcs.progressus.ui.jsf.helper.JSFMessageHelper;

@Slf4j
@NoArgsConstructor
@ManagedBean
@ViewScoped
public class LoginMB extends ProgressusMB<LoginMB> {

	private static final long serialVersionUID = -6527970229783646005L;

	
	@EJB
	@Getter(AccessLevel.PRIVATE)
	private AuthenticationBORemote authenticationBO;
	
	@Setter
	@Getter
	private UserEntity user;
	
	
	@Override
	public void init() {
		try {
			this.setUser(new UserEntity());
		} catch (Exception e) {
			LoginMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("init"));
		}
	}
	
	
	public List<SupportedLocale> getSupportedLocaleList() {
		try {
			return Arrays.asList(SupportedLocale.values());
		} catch (Exception e) {
			LoginMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("getSupportedLocaleList"));
		}
		return new ArrayList<>();
	}
	
	
	public String authenticate(){
		
		try {
			
			this.setUser(this.getAuthenticationBO().authenticate(this.getUser()));
			
			if(this.getUser() == null || !this.getUser().hasId()){
				throw new InvalidLoginOrPasswordException();
			}
			
			super.setLoggedInUser(this.getUser());
			
			return super.getURL("index");
			
		} catch (ProgressusException pe) {
			JSFMessageHelper.showMessage(pe);
		} catch (Exception e) {
			LoginMB.log.error(e.getMessage(), e);
			JSFMessageHelper.showMessage(new UnableToCompleteOperationException("authenticate"));
		}
		
		return "";
	}

	
	public void forgotMyPassword(){
		// TODO: COLOCAR LÓGICA PARA ESQUECI MINHA SENHA
	}
	
	public void changeLanguage(AjaxBehaviorEvent ajaxBehaviorEvent) {
		this.setSupportedLocale(this.getUser().getPreference().getSupportedLocale());
	}
}
