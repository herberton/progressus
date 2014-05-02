package br.com.hcs.progressus.server.ejb.sb.bo.process;

import java.util.Iterator;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import lombok.Getter;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.UserBORemote;
import br.com.hcs.progressus.client.ejb.sb.bo.process.AuthenticationBORemote;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.exception.InvalidLoginOrPasswordException;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.ValidatorHelper;
import br.com.hcs.progressus.server.jpa.entity.RoleEntity;
import br.com.hcs.progressus.server.jpa.entity.UserEntity;

@Stateless
public class AuthenticationBO extends ProgressusBOProcess implements AuthenticationBORemote {

	private static final long serialVersionUID = -1059273385095335670L;
	
	
	@Getter
	@EJB
	private UserBORemote userBO;
	
	
	@Override
	public UserEntity authenticate(final UserEntity user) throws ProgressusException {
		
		ValidatorHelper.validateFilling(UserEntity.class, user);
		ValidatorHelper.validateFilling("login", user.getLogin());
		ValidatorHelper.validateFilling("password", user.getPassword());
		
		UserEntity userDB = null;
		
		if (this.isAdministrator(user)) {
			userDB = this.getUserBO().select(user.getLogin());
			userDB.getPreference().setSupportedLocale(user.getPreference().getSupportedLocale());
			return userDB;
		}
		
		if (!this.getUserBO().isValidPassword(user.getLogin(), user.getPassword())) {
			throw new InvalidLoginOrPasswordException();
		}
		
		try {
			userDB = this.getUserBO().select(user.getLogin());
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("authenticate", e);
		}
		
		try {
			
			userDB.getPreference().setSupportedLocale(user.getPreference().getSupportedLocale());
			
			Iterator<RoleEntity> iterator = userDB.getRoleList().iterator();
			while (iterator.hasNext()) {
				if (iterator.next().isInactive()) {
					iterator.remove();
				}
			}
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("authenticate", e);
		}
		
		return this.getUserBO().save(userDB);
	}
	
	@Override
	public boolean isAdministrator(final UserEntity user) throws ProgressusException {
		
		ValidatorHelper.validateFilling(UserEntity.class, user);
		ValidatorHelper.validateFilling("login", user.getLogin());
		ValidatorHelper.validateFilling("password", user.getPassword());
		
		try {
			
			return
				System.getProperty(Setting.SERVER_KEY_ADMIN_LOGIN.toString()).equals(user.getLogin()) &&
				System.getProperty(Setting.SERVER_KEY_ADMIN_PASSWORD.toString()).equals(user.getPassword());
		
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("authenticate", e);
		}
	}

}
