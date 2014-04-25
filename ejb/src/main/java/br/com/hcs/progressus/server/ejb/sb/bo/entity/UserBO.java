package br.com.hcs.progressus.server.ejb.sb.bo.entity;

import javax.ejb.Stateless;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.UserBORemote;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.SelectException;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.server.jpa.entity.UserEntity;

@Slf4j
@NoArgsConstructor
@Stateless
public class UserBO extends ProgressusBOEntity<UserEntity> implements UserBORemote {
	
	private static final long serialVersionUID = 2149389037104552936L;
	
	
	@Override
	public UserEntity select(String login) throws ProgressusException {	
		try {
			return this.select(new UserEntity(login).toParameterMap("login", "entityStatus"));
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			UserBO.log.error(e.getMessage(), e);
			throw new SelectException(StringHelper.getI18N(this.getEntityClass()), e);
		}
	}
}
