package br.com.hcs.progressus.client.bo.sb.crud;

import javax.ejb.Remote;

import br.com.hcs.progressus.client.bo.sb.crud.common.ProgressusBOCRUDRemote;
import br.com.hcs.progressus.jpa.entity.PermissionEntity;

@Remote
public interface PermissionBORemote extends ProgressusBOCRUDRemote<PermissionEntity> { }
