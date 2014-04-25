package br.com.hcs.progressus.client.ejb.sb.bo.entity;

import javax.ejb.Remote;

import br.com.hcs.progressus.server.jpa.entity.RoleEntity;

@Remote
public interface RoleBORemote extends ProgressusBOEntityRemote<RoleEntity> {

}
