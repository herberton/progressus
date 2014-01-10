package br.com.hcs.progressus.client.bo.sb.crud.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.jpa.entity.common.ProgressusEntity;
import br.com.hcs.progressus.to.OrderByTO;

@Remote
public interface NAOBOCRUDRemote<T extends ProgressusEntity<T>> 
	extends
		Serializable 
{
	
	T save(T entity) throws ProgressusException;
	T save(T entity, boolean isCommitTransaction) throws ProgressusException;
	
	void delete(T entity) throws ProgressusException;
	void delete(T entity, boolean isCommitTransaction) throws ProgressusException;
	
	void remove(T entity) throws ProgressusException;
	void remove(T entity, boolean isCommitTransaction) throws ProgressusException;
	
	T select(T entity) throws ProgressusException;
	T select(Class<T> clazz, Map<String, Object> parameterMap) throws ProgressusException;
	
	List<T> selectList(T entity) throws ProgressusException;
	List<T> selectList(T entity, Integer firstResult, Integer maxResult, OrderByTO orderBy) throws ProgressusException;
	List<T> selectList(Class<T> clazz, Map<String, Object> parameterMap) throws ProgressusException;
	List<T> selectList(Class<T> clazz, Map<String, Object> parameterMap, Integer firstResult, Integer maxResult, OrderByTO orderBy) throws ProgressusException;
	
	int count(T entity) throws ProgressusException;
	int count(Class<T> clazz, Map<String, Object> parameterMap) throws ProgressusException;

}
