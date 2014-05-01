package br.com.hcs.progressus.client.ejb.sb.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.server.jpa.entity.ProgressusEntity;
import br.com.hcs.progressus.to.OrderByTO;

@Local
public interface ProgressusDAOLocal<T extends ProgressusEntity<?>> extends Serializable {
	
	T insert(T entity) throws ProgressusException;
	
	List<T> insertList(List<T> entityList) throws ProgressusException;
	
	
	T update(T entity) throws ProgressusException;
	
	List<T> updateList(List<T> entityList) throws ProgressusException;
	
	
	T save(T entity) throws ProgressusException;
	
	List<T> saveList(List<T> entityList) throws ProgressusException;
	
	
	void delete(T entity) throws ProgressusException;
	
	void deleteList(List<T> entityList) throws ProgressusException;
	
	
	void remove(T entity) throws ProgressusException;
	
	void removeList(List<T> entityList) throws ProgressusException;
	
	
	T insertOrSelect(T entity) throws ProgressusException;
	
	List<T> insertOrSelectList(List<T> entityList) throws ProgressusException;
	
	
	List<T> selectList(Map<String, Object> parameterMap, Integer firstResult, Integer maxResult, OrderByTO orderBy) throws ProgressusException;
	
	
	int count(Map<String, Object> parameterMap) throws ProgressusException;
}