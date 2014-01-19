package br.com.hcs.progressus.ejb.bo.sb.crud;

import br.com.hcs.progressus.client.bo.sb.crud.AuditBORemote;
import br.com.hcs.progressus.ejb.bo.sb.crud.common.ProgressusBOCRUD;
import br.com.hcs.progressus.jpa.entity.AuditEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class AuditBO extends ProgressusBOCRUD<AuditEntity> implements AuditBORemote {
    private static final long serialVersionUID = -670017151598402537L;
    public AuditBO() { super(); }
}
