package br.com.hcs.progressus.ejb.bo.sb.crud;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hcs.progressus.client.bo.sb.crud.ViewBORemote;
import br.com.hcs.progressus.ejb.bo.sb.crud.common.ProgressusBOCRUD;
import br.com.hcs.progressus.jpa.entity.ViewEntity;

@Stateless
@LocalBean
public class ViewBO extends ProgressusBOCRUD<ViewEntity> implements ViewBORemote {
    private static final long serialVersionUID = -4984671948366664359L;
    public ViewBO() { super(); }
}
