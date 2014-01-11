package br.com.hcs.progressus.client.bo.sb.crud;

import javax.ejb.Remote;

import br.com.hcs.progressus.client.bo.sb.crud.common.ProgressusBOCRUDRemote;
import br.com.hcs.progressus.jpa.entity.UserEntity;

@Remote
public interface UserBORemote extends ProgressusBOCRUDRemote<UserEntity> {
	String test(String number);
}
