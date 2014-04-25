package br.com.hcs.progressus.client.ejb.sb.bo.process;

import javax.ejb.Remote;

import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.server.jpa.entity.UserEntity;

@Remote
public interface AuthenticationBORemote extends ProgressusBOProcessRemote {
	UserEntity authenticate(final UserEntity user) throws ProgressusException;
	boolean isAdministrator(final UserEntity user) throws ProgressusException;
}
