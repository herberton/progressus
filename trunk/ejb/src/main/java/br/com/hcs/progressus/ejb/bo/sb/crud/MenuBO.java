package br.com.hcs.progressus.ejb.bo.sb.crud;

import br.com.hcs.progressus.client.bo.sb.crud.MenuBORemote;
import br.com.hcs.progressus.ejb.bo.sb.crud.common.ProgressusBOCRUD;
import br.com.hcs.progressus.jpa.entity.MenuEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class MenuBO extends ProgressusBOCRUD<MenuEntity> implements MenuBORemote {
	private static final long serialVersionUID = -4299733987529602723L;
    public MenuBO() { super(); }
}
