package br.com.hcs.progressus.client.bo.sb.crud;

import javax.ejb.Remote;

import br.com.hcs.progressus.client.bo.sb.crud.common.NAOBOCRUDRemote;
import br.com.hcs.progressus.jpa.entity.UserEntity;

@Remote
public interface UserBORemote extends NAOBOCRUDRemote<UserEntity> {
	String test(String number);
}
