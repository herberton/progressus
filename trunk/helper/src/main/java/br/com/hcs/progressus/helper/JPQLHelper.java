package br.com.hcs.progressus.helper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.hcs.progressus.enumerator.WhereClauseOperator;
import br.com.hcs.progressus.exception.InvalidParameterException;
import br.com.hcs.progressus.to.OrderByTO;
import br.com.hcs.progressus.to.WhereClauseTO;
import br.com.hcs.progressus.to.WhereTO;
import br.com.hcs.progressus.to.common.ProgressusTO;


public class JPQLHelper implements Serializable {
	
	private static final long serialVersionUID = 3143156680236714463L;
	private static final Logger logger = LoggerFactory.getLogger(JPQLHelper.class);
	
	// bind
	@SuppressWarnings("unchecked")
	public static <T extends ProgressusTO<? extends ProgressusTO<?>>> TypedQuery<T> bindParameter(TypedQuery<? extends T> typedQuery, Map<String, Object> parameterMap) {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(typedQuery)) {
				return null;
			}
			
			if (MapHelper.isNullOrEmpty(parameterMap)) {
				parameterMap = new HashMap<String, Object>();
			}
			
			return (TypedQuery<T>) JPQLHelper.bindParameter((Query)typedQuery, parameterMap);
			
		} catch (Exception e) {
			JPQLHelper.logger.warn(e.getMessage());
		}
		
		return null;
	}
	
	public static Query bindParameter(Query query, Map<String, Object> parameterMap) {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(query)) {
				return null;
			}
			
			if (MapHelper.isNullOrEmpty(parameterMap)) {
				parameterMap = new HashMap<String, Object>();
			}
			
			Iterator<String> iterator = parameterMap.keySet().iterator();
			
			while(iterator.hasNext()){
				
				String key = iterator.next();
				
				String parameter = key.replace(".", "_");
				
				query.setParameter(parameter, parameterMap.get(key));
			}
			
			return query;
			
		} catch (Exception e) {
			JPQLHelper.logger.warn(e.getMessage());
		}
		
		return null;
	}
		
	
	// getSelect
	public static <T extends ProgressusTO<? extends ProgressusTO<?>>> String getSelect(Class<? extends T> clazz, Map<String, Object> parameterMap, OrderByTO orderBy) throws InvalidParameterException {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(clazz)) {
				return "";
			}
			
			if (MapHelper.isNullOrEmpty(parameterMap)) {
				parameterMap = new HashMap<String, Object>();
			}
			
			WhereTO where = JPQLHelper.getWhere(parameterMap);
			
			if (ObjectHelper.isNullOrEmpty(where)) {
				return "";
			}
			
			return JPQLHelper.getSelect(clazz, where, orderBy);
		
		} catch (Exception e) {
			JPQLHelper.logger.warn(e.getMessage());
		}
		
		return "";
	}
	public static <T extends ProgressusTO<? extends ProgressusTO<?>>> String getSelect(Class<? extends T> clazz, WhereTO where, OrderByTO orderBy) {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(clazz)) {
				return "";
			}
			
			StringBuffer jpql = new StringBuffer(String.format("SELECT entity FROM %s AS entity", clazz.getSimpleName()));
			
			if (where != null) {
				jpql.append(where.toString());
			}
			
			if (orderBy != null) {
				jpql.append(orderBy.toString());	
			}
			
			return jpql.toString();
			
		} catch (Exception e) {
			JPQLHelper.logger.warn(e.getMessage());
		}
		
		return "";
	}
	
	// getSelectCount
	public static <T extends ProgressusTO<? extends ProgressusTO<?>>> String getSelectCount(Class<? extends T> clazz, Map<String, Object> parameterMap) throws InvalidParameterException {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(clazz)) {
				return "";
			}
			
			if (MapHelper.isNullOrEmpty(parameterMap)) {
				parameterMap = new HashMap<String, Object>();
			}
			
			return JPQLHelper.getSelectCount(clazz, JPQLHelper.getWhere(parameterMap));
			
		} catch (Exception e) {
			JPQLHelper.logger.warn(e.getMessage());
		}
		
		return "";
	}
	public static <T extends ProgressusTO<? extends ProgressusTO<?>>> String getSelectCount(Class<? extends T> clazz, WhereTO where) {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(clazz)) {
				return "";
			}
			
			StringBuffer jpql = new StringBuffer(String.format("SELECT COUNT(entity) FROM %s AS entity", clazz.getSimpleName()));
			
			if (where != null) {
				jpql.append(where.toString());
			}
			
			return jpql.toString();
			
		} catch (Exception e) {
			JPQLHelper.logger.warn(e.getMessage());
		}
		
		return "";
	}
	
	public static WhereTO getWhere(Map<String, Object> parameterMap) throws InvalidParameterException {
		
		try {
			
			WhereTO where = new WhereTO();
			
			if (MapHelper.isNullOrEmpty(parameterMap)) {
				return where;
			}
			
			Iterator<String> iterator = parameterMap.keySet().iterator();

			while(iterator.hasNext()){

				String key = iterator.next();
				Object value = parameterMap.get(key);
				
				if (value instanceof String) {
					
					where.addClause(new WhereClauseTO(key, WhereClauseOperator.LIKE, true));
					parameterMap.put(key, "%" + value.toString() + "%");
					
					continue;
				}
				
				where.addClause(new WhereClauseTO(key, WhereClauseOperator.EQUAL, true));
			}
			
			return where;
			
		} catch (java.security.InvalidParameterException e) {
			JPQLHelper.logger.warn(e.getMessage());
		}
		
		return null;
	}
}
