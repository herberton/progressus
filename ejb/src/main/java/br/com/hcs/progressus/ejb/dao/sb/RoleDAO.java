package br.com.hcs.progressus.ejb.dao.sb;

import br.com.hcs.progressus.client.dao.sb.RoleDAOLocal;
import br.com.hcs.progressus.ejb.dao.sb.common.ProgressusDAO;
import br.com.hcs.progressus.jpa.entity.RoleEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class RoleDAO extends ProgressusDAO<RoleEntity> implements RoleDAOLocal {
	private static final long serialVersionUID = 3341399598387976498L;
	public RoleDAO() { super(); }
}
