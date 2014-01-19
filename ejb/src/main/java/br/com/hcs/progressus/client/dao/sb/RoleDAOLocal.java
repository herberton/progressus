package br.com.hcs.progressus.client.dao.sb;

import javax.ejb.Local;

import br.com.hcs.progressus.client.dao.sb.common.ProgressusDAOLocal;
import br.com.hcs.progressus.jpa.entity.RoleEntity;

@Local
public interface RoleDAOLocal extends ProgressusDAOLocal<RoleEntity> { }
