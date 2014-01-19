package br.com.hcs.progressus.client.dao.sb;

import javax.ejb.Local;

import br.com.hcs.progressus.client.dao.sb.common.ProgressusDAOLocal;
import br.com.hcs.progressus.jpa.entity.PermissionEntity;

@Local
public interface PermissionDAOLocal extends ProgressusDAOLocal<PermissionEntity> { }
