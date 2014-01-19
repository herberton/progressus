package br.com.hcs.progressus.ejb.dao.sb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hcs.progressus.client.dao.sb.ItemMenuDAOLocal;
import br.com.hcs.progressus.ejb.dao.sb.common.ProgressusDAO;
import br.com.hcs.progressus.jpa.entity.ItemMenuEntity;

@Stateless
@LocalBean
public class ItemMenuDAO extends ProgressusDAO<ItemMenuEntity> implements ItemMenuDAOLocal {
    private static final long serialVersionUID = -826247125504910771L;
	public ItemMenuDAO() { super(); }
}