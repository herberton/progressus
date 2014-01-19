package br.com.hcs.progressus.ejb.dao.sb;

import br.com.hcs.progressus.client.dao.sb.PermissionDAOLocal;
import br.com.hcs.progressus.ejb.dao.sb.common.ProgressusDAO;
import br.com.hcs.progressus.jpa.entity.PermissionEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class PermissionDAO extends ProgressusDAO<PermissionEntity> implements PermissionDAOLocal {
    private static final long serialVersionUID = 3895231672285434630L;
	public PermissionDAO() { super(); }
}
