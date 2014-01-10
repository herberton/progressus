package br.com.hcs.progressus.client.bo.sb;

import java.util.List;

import javax.ejb.Remote;

import br.com.hcs.progressus.client.bo.sb.common.NAOBORemote;
import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.jpa.entity.MenuEntity;

@Remote
public interface SystemBORemote extends NAOBORemote {
	List<MenuEntity> getMenuList(final boolean create) throws ProgressusException;
}
