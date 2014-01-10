package br.com.hcs.progressus.client.dao.sb.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.jpa.entity.common.ProgressusEntity;
import br.com.hcs.progressus.to.OrderByTO;


@Local
public interface NAODAOLocal<T extends ProgressusEntity<? extends ProgressusEntity<?>>> extends Serializable {
	
	T insert(T entity) throws ProgressusException;
	
	T update(T entity) throws ProgressusException;
	
	void delete(T entity) throws ProgressusException;
	
	void remove(T entity) throws ProgressusException;
	
	List<T> selectList(Class<T> clazz, Map<String, Object> parameterMap, Integer firstResult, Integer maxResult, OrderByTO orderBy) throws ProgressusException;
	
	int count(Class<T> clazz, Map<String, Object> parameterMap) throws ProgressusException;
}
