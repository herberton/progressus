package br.com.hcs.progressus.client.ejb.sb.bo.process;

import java.util.List;
import java.util.Set;

import javax.ejb.Local;

import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.server.jpa.entity.MenuEntity;
import br.com.hcs.progressus.server.jpa.entity.ViewEntity;

@Local
public interface ApplicationBOLocal extends ProgressusBOProcessRemote {
	void createAdministratorUser() throws ProgressusException;
	void createMenuTree(Set<Class<?>> viewClazzSet) throws ProgressusException;
	List<ViewEntity> newViewList(Set<Class<?>> viewClazzSet) throws ProgressusException;
	ViewEntity newView(Class<?> viewClazz) throws ProgressusException;
	List<MenuEntity> selectMenuTreeList() throws ProgressusException;
}
