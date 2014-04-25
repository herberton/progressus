package br.com.hcs.progressus.server.ejb.sb.bo.entity;

import javax.ejb.Stateless;

import lombok.NoArgsConstructor;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.ViewBORemote;
import br.com.hcs.progressus.server.jpa.entity.ViewEntity;

@NoArgsConstructor
@Stateless
public class ViewBO extends ProgressusBOEntity<ViewEntity> implements ViewBORemote {
	private static final long serialVersionUID = -432745324434544386L;
}
