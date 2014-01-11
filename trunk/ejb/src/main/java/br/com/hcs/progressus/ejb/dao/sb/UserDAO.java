package br.com.hcs.progressus.ejb.dao.sb;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hcs.progressus.client.dao.sb.UserDAOLocal;
import br.com.hcs.progressus.ejb.dao.sb.common.ProgressusDAO;
import br.com.hcs.progressus.jpa.entity.UserEntity;

/**
 * Session Bean implementation class UserDAO
 */
@Stateless
@LocalBean
public class UserDAO extends ProgressusDAO<UserEntity> implements UserDAOLocal {
       
	private static final long serialVersionUID = 4181463477034236592L;

	public UserDAO() { super(); }

}
