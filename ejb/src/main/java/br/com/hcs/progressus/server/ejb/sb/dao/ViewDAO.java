package br.com.hcs.progressus.server.ejb.sb.dao;

import javax.ejb.Stateless;

import lombok.NoArgsConstructor;
import br.com.hcs.progressus.client.ejb.sb.dao.ViewDAOLocal;
import br.com.hcs.progressus.server.jpa.entity.ViewEntity;

@NoArgsConstructor
@Stateless
public class ViewDAO extends ProgressusDAO<ViewEntity> implements ViewDAOLocal {
	private static final long serialVersionUID = -490146068934963885L;
}
