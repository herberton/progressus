package br.com.hcs.progressus.server.ejb.sb.dao;

import javax.ejb.Stateless;

import lombok.NoArgsConstructor;
import br.com.hcs.progressus.client.ejb.sb.dao.PermissionDAOLocal;
import br.com.hcs.progressus.server.jpa.entity.PermissionEntity;

@NoArgsConstructor
@Stateless
public class PermissionDAO extends ProgressusDAO<PermissionEntity> implements PermissionDAOLocal {
	private static final long serialVersionUID = 5740639141278985969L;
}
