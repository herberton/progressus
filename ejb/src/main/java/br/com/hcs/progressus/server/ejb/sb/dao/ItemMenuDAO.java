package br.com.hcs.progressus.server.ejb.sb.dao;

import javax.ejb.Stateless;

import lombok.NoArgsConstructor;
import br.com.hcs.progressus.client.ejb.sb.dao.ItemMenuDAOLocal;
import br.com.hcs.progressus.server.jpa.entity.ItemMenuEntity;

@NoArgsConstructor
@Stateless
public class ItemMenuDAO extends ProgressusDAO<ItemMenuEntity> implements ItemMenuDAOLocal {
	private static final long serialVersionUID = 2033958308317239499L;
}
