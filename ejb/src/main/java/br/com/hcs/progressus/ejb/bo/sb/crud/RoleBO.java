package br.com.hcs.progressus.ejb.bo.sb.crud;

import br.com.hcs.progressus.client.bo.sb.crud.RoleBORemote;
import br.com.hcs.progressus.ejb.bo.sb.crud.common.ProgressusBOCRUD;
import br.com.hcs.progressus.jpa.entity.RoleEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class RoleBO extends ProgressusBOCRUD<RoleEntity> implements RoleBORemote {
    private static final long serialVersionUID = -6105960071915384385L;
    public RoleBO() { super(); }
}
