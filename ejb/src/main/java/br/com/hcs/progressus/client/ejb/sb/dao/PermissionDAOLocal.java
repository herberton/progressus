package br.com.hcs.progressus.client.ejb.sb.dao;

import javax.ejb.Local;

import br.com.hcs.progressus.server.jpa.entity.PermissionEntity;

@Local
public interface PermissionDAOLocal extends ProgressusDAOLocal<PermissionEntity> {

}
