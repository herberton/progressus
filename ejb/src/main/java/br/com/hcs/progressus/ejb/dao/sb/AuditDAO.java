package br.com.hcs.progressus.ejb.dao.sb;

import br.com.hcs.progressus.client.dao.sb.AuditDAOLocal;
import br.com.hcs.progressus.ejb.dao.sb.common.ProgressusDAO;
import br.com.hcs.progressus.jpa.entity.AuditEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class AuditDAO extends ProgressusDAO<AuditEntity> implements AuditDAOLocal {
	private static final long serialVersionUID = 1309306919548080121L;
	public AuditDAO() { super(); }
}
