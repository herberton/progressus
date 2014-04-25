package br.com.hcs.progressus.server.ejb.sb.dao;

import javax.ejb.Stateless;

import lombok.NoArgsConstructor;
import br.com.hcs.progressus.client.ejb.sb.dao.RoleDAOLocal;
import br.com.hcs.progressus.server.jpa.entity.RoleEntity;

@NoArgsConstructor
@Stateless
public class RoleDAO extends ProgressusDAO<RoleEntity> implements RoleDAOLocal {
	private static final long serialVersionUID = 3055177294821370257L;
}
