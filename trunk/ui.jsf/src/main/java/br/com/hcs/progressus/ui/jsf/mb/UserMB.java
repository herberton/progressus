package br.com.hcs.progressus.ui.jsf.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.hcs.progressus.annotation.View;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.server.jpa.entity.EmailEntity;
import br.com.hcs.progressus.server.jpa.entity.UserEntity;
import br.com.hcs.progressus.server.jpa.entity.UserPreferenceEntity;

@ManagedBean
@ViewScoped
@View(module="crud", name="user")
public class UserMB extends ProgressusCRUDMB<UserMB, UserEntity> {

	private static final long serialVersionUID = -1505766935235205004L;

	
	@Override
	public UserEntity getEntity() {
		
		UserEntity user = super.getEntity();
		
		if (user.getEmail() == null) {
			user.setEmail(new EmailEntity());
		}
		
		if (user.getPreference() == null) {
			user.setPreference(UserPreferenceEntity.getDefault());
		}
		
		return user;
	}
	
	
	@Override
	public void load() throws ProgressusException {
		System.out.println("load()");
	}
	
	public void sendTestEmail() {
		
	}
}
