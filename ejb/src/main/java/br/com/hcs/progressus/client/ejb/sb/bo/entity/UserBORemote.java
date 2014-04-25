package br.com.hcs.progressus.client.ejb.sb.bo.entity;

import javax.ejb.Remote;

import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.server.jpa.entity.UserEntity;

@Remote
public interface UserBORemote extends ProgressusBOEntityRemote<UserEntity> {
	UserEntity select(String login) throws ProgressusException;
}
