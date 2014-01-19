package br.com.hcs.progressus.ejb.bo.sb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.hcs.progressus.client.bo.sb.SystemBORemote;
import br.com.hcs.progressus.ejb.bo.sb.common.ProgressusBO;
import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.jpa.entity.MenuEntity;

@Stateless
@LocalBean
public class SystemBO extends ProgressusBO implements SystemBORemote {
       
    private static final long serialVersionUID = 121260125226042695L;

    public SystemBO() {
        super();
    }

	@Override
	public List<MenuEntity> getMenuList(final boolean create) throws ProgressusException {
		
		
		
		if (create) {
			// TODO: Criar lista de menus
		}
		
		// TODO: Retornar lista de menus
		return null;
	}

}
