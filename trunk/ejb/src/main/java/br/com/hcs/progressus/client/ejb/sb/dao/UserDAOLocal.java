package br.com.hcs.progressus.client.ejb.sb.dao;

import javax.ejb.Local;

import br.com.hcs.progressus.server.jpa.entity.UserEntity;

@Local
public interface UserDAOLocal extends ProgressusDAOLocal<UserEntity> {

}
