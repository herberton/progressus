package br.com.hcs.progressus.to;

import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.enumerator.OrderByType;
import br.com.hcs.progressus.helper.StringHelper;

@Slf4j
@NoArgsConstructor
public class OrderByTO extends ProgressusTO<OrderByTO> {

	private static final long serialVersionUID = 2933581165238344379L;
	
	@Getter
	@Setter
	private Map<String, OrderByType> orderByMap;
	
	
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
			
			field = StringHelper.isNullOrEmpty(field) ? "id" : field;
			
			if (this.getOrderByMap().containsKey(field)) {
				throw new InvalidParameterException(field);
			}
			
			this.getOrderByMap().put(field, type);
			
		} catch (Exception e) {
			OrderByTO.log.error(e.getMessage());
		}
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
			OrderByTO.log.error(e.getMessage());
		}
		
		return "";
	}
}
