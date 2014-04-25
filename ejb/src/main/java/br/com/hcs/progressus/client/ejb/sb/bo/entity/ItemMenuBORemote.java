package br.com.hcs.progressus.client.ejb.sb.bo.entity;

import javax.ejb.Remote;

import br.com.hcs.progressus.server.jpa.entity.ItemMenuEntity;

@Remote
public interface ItemMenuBORemote extends ProgressusBOEntityRemote<ItemMenuEntity> {

}
