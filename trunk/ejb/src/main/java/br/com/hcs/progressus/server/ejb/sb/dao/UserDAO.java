package br.com.hcs.progressus.server.ejb.sb.dao;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;

import lombok.NoArgsConstructor;
import br.com.hcs.progressus.client.ejb.sb.dao.UserDAOLocal;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.ValidatorHelper;
import br.com.hcs.progressus.server.jpa.entity.UserEntity;

@NoArgsConstructor
@Stateless
public class UserDAO extends ProgressusDAO<UserEntity> implements UserDAOLocal {
	private static final long serialVersionUID = 2554509078093228133L;
	@Override
	public boolean isValidPassword(String login, String password) throws ProgressusException {
		
		ValidatorHelper.validateFilling("login", login);
		ValidatorHelper.validateFilling("password", password);
		
		try {
			
			StringBuilder jpql = new StringBuilder("SELECT CASE WHEN (COUNT(entity) > 0) THEN true ELSE false END FROM ");
			jpql.append(this.getEntityClazz().getSimpleName());
			jpql.append(" AS entity WHERE entity.login = :login AND entity.password = :password");
			
			Map<String, Object> parameterMap = new HashMap<>();
			
			parameterMap.put("login", login);
			parameterMap.put("password", password);
			
			Boolean returnValue = ((Boolean)this.createQuery(Boolean.class, parameterMap, jpql.toString()).getSingleResult());
			
			if (returnValue == null) {
				return false;
			}
			
			return returnValue.booleanValue();
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isValidPassword", e);
		}
	}
}
