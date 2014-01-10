package br.com.hcs.progressus.client.dao.sb;

import javax.ejb.Local;

import br.com.hcs.progressus.client.dao.sb.common.NAODAOLocal;
import br.com.hcs.progressus.jpa.entity.UserEntity;


@Local
public interface UserDAOLocal extends NAODAOLocal<UserEntity> {

}
