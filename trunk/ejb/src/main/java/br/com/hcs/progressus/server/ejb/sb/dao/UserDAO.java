package br.com.hcs.progressus.server.ejb.sb.dao;

import javax.ejb.Stateless;

import lombok.NoArgsConstructor;
import br.com.hcs.progressus.client.ejb.sb.dao.UserDAOLocal;
import br.com.hcs.progressus.server.jpa.entity.UserEntity;

@NoArgsConstructor
@Stateless
public class UserDAO extends ProgressusDAO<UserEntity> implements UserDAOLocal {
	private static final long serialVersionUID = 2554509078093228133L;
}
