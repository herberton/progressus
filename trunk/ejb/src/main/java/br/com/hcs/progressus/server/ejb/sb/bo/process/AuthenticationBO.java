package br.com.hcs.progressus.server.ejb.sb.bo.process;

import java.util.Iterator;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.UserBORemote;
import br.com.hcs.progressus.client.ejb.sb.bo.process.AuthenticationBORemote;
import br.com.hcs.progressus.enumerator.Setting;
import br.com.hcs.progressus.exception.EmptyParameterException;
import br.com.hcs.progressus.exception.InvalidLoginOrPasswordException;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.server.jpa.entity.RoleEntity;
import br.com.hcs.progressus.server.jpa.entity.UserEntity;

@Slf4j
@Stateless
public class AuthenticationBO extends ProgressusBOProcess implements AuthenticationBORemote {

	private static final long serialVersionUID = -1059273385095335670L;
	
	@Getter
	@EJB
	private UserBORemote userBO;
	
	@Override
	public UserEntity authenticate(final UserEntity user) throws ProgressusException {
		
		if (user == null) {
			throw new EmptyParameterException("userEntity");
		}
		
		if (StringHelper.isNullOrEmpty(user.getLogin())) {
			throw new EmptyParameterException("login");
		}
		
		if (StringHelper.isNullOrEmpty(user.getPassword())) {
			throw new EmptyParameterException("password");
		}
		
		if (this.isAdministrator(user)) {
			return this.getUserBO().select(user.getLogin());
		}
		
		if (!this.getUserBO().isValidPassword(user.getLogin(), user.getPassword())) {
			throw new InvalidLoginOrPasswordException();
		}
		
		UserEntity userDB = null;
		
		try {
			userDB = this.getUserBO().select(user.getLogin());
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			AuthenticationBO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("authenticate");
		}
		
		try {
			userDB.getPreference().setSupportedLocale(user.getPreference().getSupportedLocale());
		} catch (Exception e) {
			AuthenticationBO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("authenticate");
		}
		
		try {
			
			Iterator<RoleEntity> iterator = userDB.getRoleList().iterator();
			while (iterator.hasNext()) {
				if (iterator.next().isInactive()) {
					iterator.remove();
				}
			}
			
		} catch (Exception e) {
			AuthenticationBO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("authenticate");
		}
		
		try {
			
			userDB = this.getUserBO().save(userDB);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			AuthenticationBO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("authenticate"); 
		}
		
		return userDB;
	}
	
	@Override
	public boolean isAdministrator(final UserEntity user) throws ProgressusException {
		
		if (user == null) {
			throw new EmptyParameterException("userEntity");
		}
		
		if (StringHelper.isNullOrEmpty(user.getLogin())) {
			throw new EmptyParameterException("login");
		}
		
		if (StringHelper.isNullOrEmpty(user.getPassword())) {
			throw new EmptyParameterException("password");
		}
		
		try {
			return
				System.getProperty(Setting.SERVER_KEY_ADMIN_LOGIN.toString()).equals(user.getLogin()) &&
				System.getProperty(Setting.SERVER_KEY_ADMIN_PASSWORD.toString()).equals(user.getPassword());
		} catch (Exception e) {
			AuthenticationBO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("authenticate");
		}
	}

}
