package br.com.hcs.progressus.client.bo.sb.crud;

import javax.ejb.Remote;

import br.com.hcs.progressus.client.bo.sb.crud.common.ProgressusBOCRUDRemote;
import br.com.hcs.progressus.jpa.entity.MenuEntity;

@Remote
public interface MenuBORemote extends ProgressusBOCRUDRemote<MenuEntity> { }
