package br.com.hcs.progressus.client.ejb.sb.bo.entity;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import br.com.hcs.progressus.client.ejb.sb.bo.process.ProgressusBOProcessRemote;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.server.jpa.entity.ProgressusEntity;
import br.com.hcs.progressus.to.OrderByTO;

@Remote
public interface ProgressusBOEntityRemote<T extends ProgressusEntity<?>> extends ProgressusBOProcessRemote {

	T save(T entity) throws ProgressusException;
	
	List<T> saveList(List<T> entityList) throws ProgressusException;
	
	
	void delete(T entity) throws ProgressusException;
	
	void deleteList(List<T> entityList) throws ProgressusException;
	
	
	void remove(T entity) throws ProgressusException;
	
	void removeList(List<T> entityList) throws ProgressusException;
	
	
	T insertOrSelect(T entity) throws ProgressusException;
	
	List<T> insertOrSelectList(List<T> entityList) throws ProgressusException;
	
	
	T select(T entity) throws ProgressusException;
	T select(Map<String, Object> parameterMap) throws ProgressusException;
	
	List<T> selectList() throws ProgressusException;
	List<T> selectList(T entity) throws ProgressusException;
	List<T> selectList(T entity, Integer firstResult, Integer maxResult) throws ProgressusException;
	List<T> selectList(T entity, OrderByTO orderBy) throws ProgressusException;
	List<T> selectList(T entity, Integer firstResult, Integer maxResult, OrderByTO orderBy) throws ProgressusException;
	
	List<T> selectList(Map<String, Object> parameterMap) throws ProgressusException;
	List<T> selectList(Map<String, Object> parameterMap, Integer firstResult, Integer maxResult) throws ProgressusException;
	List<T> selectList(Map<String, Object> parameterMap, OrderByTO orderBy) throws ProgressusException;
	List<T> selectList(Map<String, Object> parameterMap, Integer firstResult, Integer maxResult, OrderByTO orderBy) throws ProgressusException;
	
	int count() throws ProgressusException;
	int count(T entity) throws ProgressusException;
	int count(Map<String, Object> parameterMap) throws ProgressusException;
	
	Class<T> getEntityClass() throws ProgressusException;
	
}
