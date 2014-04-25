package br.com.hcs.progressus.client.ejb.sb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.server.jpa.entity.MenuEntity;

@Local
public interface MenuDAOLocal extends ProgressusDAOLocal<MenuEntity> {
	List<MenuEntity> selectList() throws ProgressusException;
	List<MenuEntity> saveTreeList(List<MenuEntity> treeList) throws ProgressusException;
}
