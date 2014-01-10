package br.com.hcs.progressus.to;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import br.com.hcs.progressus.enumerator.OrderByType;
import br.com.hcs.progressus.exception.InvalidParameterException;
import br.com.hcs.progressus.to.common.ProgressusTO;
import lombok.Setter;


public class OrderByTO extends ProgressusTO<OrderByTO> {

	private static final long serialVersionUID = -7062194474364983363L;
	
	
	@Setter
	private Map<String, OrderByType> orderByMap;
	
	
	public Map<String, OrderByType> getOrderByMap() {
		if (this.orderByMap == null) {
			this.setOrderByMap(new HashMap<String, OrderByType>());
		}
		return this.orderByMap;
	}
	
	
	public OrderByTO() { super(); }
	public OrderByTO(String field, OrderByType type) throws InvalidParameterException {
		this();
		this.addOrderBy(field, type);
	}
	public OrderByTO(Map<String, OrderByType> orderByMap) throws InvalidParameterException{
		this();
		this.setOrderByMap(orderByMap);
	}
	
	
	public void addOrderBy(String field, OrderByType type) throws InvalidParameterException {
		
		field = 
			field == null || field.trim().isEmpty() ? 
				"id" : 
				field;
		
		if (this.getOrderByMap().containsKey(field)) {
			throw new InvalidParameterException(field);
		}
		this.getOrderByMap().put(field, type);
	}
	
	@Override
	public String toString() {
		
		if (this.getOrderByMap().size() <= 0) {
			return "";
		}
		
		StringBuffer jpql = new StringBuffer(" ORDER BY ");
		
		Iterator<String> iterator = this.getOrderByMap().keySet().iterator();
		while (iterator.hasNext()) {
			
			String field = (String)iterator.next();
			OrderByType type = this.getOrderByMap().get(field);
			
			jpql.append(String.format("entity.%s %s", field, type.toString()));
			
			if (iterator.hasNext()) {
				jpql.append(", ");
			}
		}
		
		return jpql.toString();
	}
}
