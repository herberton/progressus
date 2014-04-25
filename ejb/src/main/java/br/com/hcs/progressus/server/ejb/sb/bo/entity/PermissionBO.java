package br.com.hcs.progressus.server.ejb.sb.bo.entity;

import javax.ejb.Stateless;

import lombok.NoArgsConstructor;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.PermissionBORemote;
import br.com.hcs.progressus.server.jpa.entity.PermissionEntity;

@NoArgsConstructor
@Stateless
public class PermissionBO extends ProgressusBOEntity<PermissionEntity> implements PermissionBORemote {
	private static final long serialVersionUID = -8543360402724842852L;

}
