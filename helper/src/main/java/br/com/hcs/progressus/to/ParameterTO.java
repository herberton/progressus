package br.com.hcs.progressus.to;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.to.common.ProgressusTO;
import lombok.Getter;
import lombok.Setter;

public class ParameterTO<T> 
	extends 
		ProgressusTO<ParameterTO<T>>
	implements
		Comparable<ParameterTO<T>>
{
	
	private static final long serialVersionUID = 744591423483048198L;
	private static final Logger logger = LoggerFactory.getLogger(ParameterTO.class);
	
	
	@Getter
	@Setter
	private Integer index;
	
	@Getter
	@Setter
	private Class<?> type;
	
	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private T value;
	
	
	public ParameterTO() { super(); }
	public ParameterTO(Integer index, T value) {
		this();
		this.setIndex(index);
		this.setType(value.getClass());
		this.setValue(value);
	}

	
	@Override
	public int compareTo(ParameterTO<T> other) {
		
		try {
			
			if (other == null) {
				return -1;
			}
			
			if (other.getIndex() == null) {
				return -1;
			}
			
			if (this.getIndex() == null) {
				return -1;
			}
			
			return this.getIndex().compareTo(other.getIndex());
			
		} catch (Exception e) {
			ParameterTO.logger.warn(e.getMessage());
		}
		
		return -1;
	}
	
	
	public static Class<?>[] getTypeArray(List<ParameterTO<?>> parameterMethodList) {
		
		try {
			
			if (CollectionHelper.isNullOrEmpty(parameterMethodList)) {
				return new Class<?>[]{};
			}
			
			List<Class<?>> list = new ArrayList<Class<?>>();
			
			for (ParameterTO<?> parameterMethod : parameterMethodList) {
				
				if (ObjectHelper.isNullOrEmpty(parameterMethod)) {
					continue;
				}
				
				list.add(parameterMethod.getType());
			}
			
			return list.toArray(new Class<?>[]{});
			
		} catch (Exception e) {
			ParameterTO.logger.warn(e.getMessage());
		}
		
		return new Class<?>[]{};
		
	}
	
	public static String[] getNameArray(List<ParameterTO<?>> parameterMethodList) {
		
		try {
			
			if (CollectionHelper.isNullOrEmpty(parameterMethodList)) {
				return new String[]{};
			}
			
			List<String> list = new ArrayList<String>();
			
			for (ParameterTO<?> parameterMethod : parameterMethodList) {
				
				if (ObjectHelper.isNullOrEmpty(parameterMethod)) {
					continue;
				}
				
				list.add(parameterMethod.getName());
			}
			
			return list.toArray(new String[]{});

		} catch (Exception e) {
			ParameterTO.logger.warn(e.getMessage());
		}
		
		return new String[]{};
	}

	public static Object[] getValueArray(List<ParameterTO<?>> parameterMethodList) {
		
		try {
			
			if (CollectionHelper.isNullOrEmpty(parameterMethodList)) {
				return new Object[]{};
			}
			
			List<Object> list = new ArrayList<Object>();
			
			for (ParameterTO<?> parameterMethod : parameterMethodList) {
				
				if (ObjectHelper.isNullOrEmpty(parameterMethod)) {
					continue;
				}
				
				list.add(parameterMethod.getValue());
			}
			
			return list.toArray(new Object[]{});
		
		} catch (Exception e) {
			ParameterTO.logger.warn(e.getMessage());
		}
		
		return new Object[]{};
	}
}
