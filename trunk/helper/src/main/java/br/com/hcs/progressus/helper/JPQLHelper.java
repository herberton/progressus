package  br.com.hcs.progressus.helper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.enumerator.WhereClauseOperator;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.to.OrderByTO;
import br.com.hcs.progressus.to.ProgressusTO;
import br.com.hcs.progressus.to.WhereClauseTO;
import br.com.hcs.progressus.to.WhereTO;

@Slf4j
public final class JPQLHelper implements Serializable {

	private static final long serialVersionUID = 3962812252308941950L;
	
	
	@SuppressWarnings("unchecked")
	public static final <T extends ProgressusTO<? extends ProgressusTO<?>>> TypedQuery<T> bindParameter(TypedQuery<? extends T> typedQuery, Map<String, Object> parameterMap) throws ProgressusException {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(typedQuery)) {
				return null;
			}
			
			parameterMap = MapHelper.isNullOrEmpty(parameterMap) ? new HashMap<String, Object>() : parameterMap;
			
			return (TypedQuery<T>) JPQLHelper.bindParameter((Query)typedQuery, parameterMap);
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			JPQLHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("bindParameter");
		}
	}
	
	public static final Query bindParameter(Query query, Map<String, Object> parameterMap) throws ProgressusException {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(query)) {
				return null;
			}
			
			parameterMap = MapHelper.isNullOrEmpty(parameterMap) ? new HashMap<String, Object>() : parameterMap;
			
			Iterator<String> iterator = parameterMap.keySet().iterator();
			
			while(iterator.hasNext()){
				
				String key = iterator.next();
				
				String parameter = key.replace(".", "_");
				
				query.setParameter(parameter, parameterMap.get(key));
			}
			
			return query;
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			JPQLHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("bindParameter");
		}
	}
		
	
	public static final <T extends ProgressusTO<? extends ProgressusTO<?>>> String getSelect(Class<? extends T> clazz, Map<String, Object> parameterMap, OrderByTO orderBy) throws ProgressusException {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(clazz)) {
				return "";
			}
			
			parameterMap = MapHelper.isNullOrEmpty(parameterMap) ? new HashMap<String, Object>() : parameterMap;
			
			WhereTO where = JPQLHelper.getWhere(parameterMap);
			
			if (ObjectHelper.isNullOrEmpty(where)) {
				return "";
			}
			
			return JPQLHelper.getSelect(clazz, where, orderBy);
		
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			JPQLHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("getSelect");
		}
	}

	public static final <T extends ProgressusTO<? extends ProgressusTO<?>>> String getSelect(Class<? extends T> clazz, WhereTO where, OrderByTO orderBy) throws ProgressusException {
		
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
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			JPQLHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("getSelect");
		}
	}
	
	
	public static final <T extends ProgressusTO<? extends ProgressusTO<?>>> String getSelectCount(Class<? extends T> clazz, Map<String, Object> parameterMap) throws ProgressusException {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(clazz)) {
				return "";
			}
			
			parameterMap = MapHelper.isNullOrEmpty(parameterMap) ? new HashMap<String, Object>() : parameterMap;
			
			return JPQLHelper.getSelectCount(clazz, JPQLHelper.getWhere(parameterMap));
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			JPQLHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("getSelectCount");
		}
	}

	public static final <T extends ProgressusTO<? extends ProgressusTO<?>>> String getSelectCount(Class<? extends T> clazz, WhereTO where) throws ProgressusException {
		
		try {
			
			if (ObjectHelper.isNullOrEmpty(clazz)) {
				return "";
			}
			
			StringBuffer jpql = new StringBuffer(String.format("SELECT COUNT(entity) FROM %s AS entity", clazz.getSimpleName()));
			
			if (where != null) {
				jpql.append(where.toString());
			}
			
			return jpql.toString();
			
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			JPQLHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("getSelectCount");
		}
	}

	
	public static final WhereTO getWhere(Map<String, Object> parameterMap) throws ProgressusException {
			
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
				
			} catch (ProgressusException pe) {
				throw pe;
			} catch (Exception e) {
				JPQLHelper.log.error(e.getMessage(), e);
				throw new UnableToCompleteOperationException("getWhere");
			}
		}
}