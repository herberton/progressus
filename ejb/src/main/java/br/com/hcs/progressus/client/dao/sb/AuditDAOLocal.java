package br.com.hcs.progressus.client.dao.sb;

import javax.ejb.Local;

import br.com.hcs.progressus.client.dao.sb.common.ProgressusDAOLocal;
import br.com.hcs.progressus.jpa.entity.AuditEntity;

@Local
public interface AuditDAOLocal extends ProgressusDAOLocal<AuditEntity> { }
