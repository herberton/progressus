package br.com.hcs.progressus.client.ejb.sb.bo.entity;

import java.util.List;

import javax.ejb.Remote;

import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.server.jpa.entity.MenuEntity;

@Remote
public interface MenuBORemote extends ProgressusBOEntityRemote<MenuEntity> {
	List<MenuEntity> selectTreeList() throws ProgressusException;
	List<MenuEntity> saveTreeList(List<MenuEntity> treeList) throws ProgressusException;
}
