package br.com.hcs.progressus.ejb.dao.sb;

import br.com.hcs.progressus.client.dao.sb.ViewDAOLocal;
import br.com.hcs.progressus.ejb.dao.sb.common.ProgressusDAO;
import br.com.hcs.progressus.jpa.entity.ViewEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class ViewDAO extends ProgressusDAO<ViewEntity> implements ViewDAOLocal {
    private static final long serialVersionUID = -1292624573204379608L;
	public ViewDAO() { super(); }
}
