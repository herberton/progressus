package br.com.hcs.progressus.server.ejb.sb.bo.entity;

import javax.ejb.Stateless;

import lombok.NoArgsConstructor;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.RoleBORemote;
import br.com.hcs.progressus.server.jpa.entity.RoleEntity;

@NoArgsConstructor
@Stateless
public class RoleBO extends ProgressusBOEntity<RoleEntity> implements RoleBORemote {
	private static final long serialVersionUID = 1610639394058992791L;
}
