package br.com.hcs.progressus.ejb.bo.sb.crud;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hcs.progressus.client.bo.sb.crud.ItemMenuBORemote;
import br.com.hcs.progressus.ejb.bo.sb.crud.common.ProgressusBOCRUD;
import br.com.hcs.progressus.jpa.entity.ItemMenuEntity;

@Stateless
@LocalBean
public class ItemMenuBO extends ProgressusBOCRUD<ItemMenuEntity> implements ItemMenuBORemote {
    private static final long serialVersionUID = -8810048176268745157L;
    public ItemMenuBO() { super(); }
}
