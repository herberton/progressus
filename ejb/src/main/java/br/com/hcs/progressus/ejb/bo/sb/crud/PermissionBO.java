package br.com.hcs.progressus.ejb.bo.sb.crud;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hcs.progressus.client.bo.sb.crud.PermissionBORemote;
import br.com.hcs.progressus.ejb.bo.sb.crud.common.ProgressusBOCRUD;
import br.com.hcs.progressus.jpa.entity.PermissionEntity;

@Stateless
@LocalBean
public class PermissionBO extends ProgressusBOCRUD<PermissionEntity> implements PermissionBORemote {
    private static final long serialVersionUID = 230774448077920934L;
	public PermissionBO() { super(); }
}
