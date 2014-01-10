package br.com.hcs.progressus.helper;

import java.util.Iterator;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.hcs.progressus.enumerator.WhereClauseOperator;
import br.com.hcs.progressus.exception.InvalidParameterException;
import br.com.hcs.progressus.to.OrderByTO;
import br.com.hcs.progressus.to.ProgressusTO;
import br.com.hcs.progressus.to.WhereClauseTO;
import br.com.hcs.progressus.to.WhereTO;


public class JPQLHelper {
	
	// bind
	@SuppressWarnings("unchecked")
	public static <T extends ProgressusTO<? extends ProgressusTO<?>>> TypedQuery<T> bindParameter(TypedQuery<T> typedQuery, Map<String, Object> parameterMap) {
		return (TypedQuery<T>) JPQLHelper.bindParameter((Query)typedQuery, parameterMap);
	}
	public static Query bindParameter(Query query, Map<String, Object> parameterMap) {
		
		Iterator<String> iterator = parameterMap.keySet().iterator();
		
		while(iterator.hasNext()){
			
			String key = iterator.next();
			
			String parameter = key.replace(".", "_");
			
			query.setParameter(parameter, parameterMap.get(key));
		}
		
		return query;
	}
		
	// getSelect
	public static String getSelect(Class<? extends ProgressusTO<? extends ProgressusTO<?>>> clazz, Map<String, Object> parameterMap, OrderByTO orderBy) throws InvalidParameterException {
		return JPQLHelper.getSelect(clazz, JPQLHelper.getWhere(parameterMap), orderBy);
	}
	public static String getSelect(Class<? extends ProgressusTO<? extends ProgressusTO<?>>> clazz, WhereTO where, OrderByTO orderBy) {
		
		StringBuffer jpql = new StringBuffer(String.format("SELECT entity FROM %s AS entity", clazz.getSimpleName()));
		
		jpql.append(where.toString());
		
		if (orderBy != null) {
			jpql.append(orderBy.toString());	
		}
		
		return jpql.toString();
	}
	
	// getSelectCount
	public static String getSelectCount(Class<? extends ProgressusTO<? extends ProgressusTO<?>>> clazz, Map<String, Object> parameterMap) throws InvalidParameterException {
		return JPQLHelper.getSelectCount(clazz, JPQLHelper.getWhere(parameterMap));
	}
	public static String getSelectCount(Class<? extends ProgressusTO<? extends ProgressusTO<?>>> clazz, WhereTO where) {
		
		StringBuffer jpql = new StringBuffer(String.format("SELECT COUNT(entity) FROM %s AS entity", clazz.getSimpleName()));
		
		jpql.append(where.toString());
		
		return jpql.toString();
	}
	
	public static WhereTO getWhere(Map<String, Object> parameterMap) throws InvalidParameterException {
		
		WhereTO where = new WhereTO();
		
		if (parameterMap == null || parameterMap.keySet().size() <= 0) {
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
	}
	public static void println(String jpql, Map<String, Object> parameterMap) {
		System.out.println("NAO.JPQL:\t" + jpql);
		System.out.println("NAO.ParameterMap:\t" + parameterMap.toString());
	}
}
