package br.com.hcs.progressus.ejb.dao.sb;

import br.com.hcs.progressus.client.dao.sb.MenuDAOLocal;
import br.com.hcs.progressus.ejb.dao.sb.common.ProgressusDAO;
import br.com.hcs.progressus.jpa.entity.MenuEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class MenuDAO extends ProgressusDAO<MenuEntity> implements MenuDAOLocal {
	private static final long serialVersionUID = -6277442440646113278L;
	public MenuDAO() { super(); }
}
