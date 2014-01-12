package br.com.hcs.progressus.to;

import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Setter;
import br.com.hcs.progressus.enumerator.OrderByType;
import br.com.hcs.progressus.exception.InvalidParameterException;
import br.com.hcs.progressus.helper.MapHelper;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.to.common.ProgressusTO;


public class OrderByTO extends ProgressusTO<OrderByTO> {

	private static final long serialVersionUID = -7062194474364983363L;
	private static final Logger logger = LoggerFactory.getLogger(OrderByTO.class);
	
	
	@Setter
	private Map<String, OrderByType> orderByMap;
	
	
	public Map<String, OrderByType> getOrderByMap() {
		return MapHelper.isNullOrEmptyReplaceByNewHashMap(this.orderByMap) ;
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
		
		try {
			
			field = 
				field == null ?
					field :
					field.replaceAll(" ", "");
			
			field = StringHelper.isNullOrEmptyReplaceBy(field, "id");
			
		} catch (Exception e) {
			OrderByTO.logger.warn(e.getMessage());
		}
		
		if (this.getOrderByMap().containsKey(field)) {
			throw new InvalidParameterException(field);
		}
		
		this.getOrderByMap().put(field, type);
		
	}
	
	
	@Override
	public String toString() {
		
		try {
			
			if (this.getOrderByMap().size() <= 0) {
				return "";
			}
			
			StringBuffer jpql = new StringBuffer(" ORDER BY ");
			
			Iterator<String> iterator = this.getOrderByMap().keySet().iterator();
			while (iterator.hasNext()) {
				
				String field = (String)iterator.next();
				OrderByType type = this.getOrderByMap().get(field);
				
				if (type == null) {
					continue;
				}
				
				jpql.append(String.format("entity.%s %s", field, type.toString()));
				
				if (iterator.hasNext()) {
					jpql.append(", ");
				}
			}
			
			return jpql.toString();
			
		} catch (Exception e) {
			OrderByTO.logger.warn(e.getMessage());
		}
		
		return "";
	}
}
