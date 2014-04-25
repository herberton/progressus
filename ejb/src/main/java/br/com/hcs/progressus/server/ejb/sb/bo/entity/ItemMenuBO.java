package br.com.hcs.progressus.server.ejb.sb.bo.entity;

import javax.ejb.Stateless;

import lombok.NoArgsConstructor;
import br.com.hcs.progressus.client.ejb.sb.bo.entity.ItemMenuBORemote;
import br.com.hcs.progressus.server.jpa.entity.ItemMenuEntity;

@NoArgsConstructor
@Stateless
public class ItemMenuBO extends ProgressusBOEntity<ItemMenuEntity> implements ItemMenuBORemote {
	private static final long serialVersionUID = -1994224354647791439L;
}
