package br.com.hcs.progressus.to;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ParameterTO<T> 
	extends 
		ProgressusTO<ParameterTO<T>>
	implements
		Comparable<ParameterTO<T>>
{
	
	private static final long serialVersionUID = 744591423483048198L;
	
	@Getter
	@Setter
	private Integer index;
	
	@Getter
	@Setter
	private Class<T> type;
	
	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private T value;
	
	
	public ParameterTO() { super(); }
	@SuppressWarnings("unchecked")
	public ParameterTO(Integer index, T value) {
		this();
		this.setIndex(index);
		this.setType((Class<T>)(Class<?>)value.getClass());
		this.setValue(value);
	}

	
	@Override
	public int compareTo(ParameterTO<T> other) {
		
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
	}
	
	
	public static Class<?>[] getTypeArray(List<ParameterTO<?>> parameterMethodList) {
		List<Class<?>> list = new ArrayList<Class<?>>();
		for (ParameterTO<?> parameterMethod : parameterMethodList) {
			list.add(parameterMethod.getType());
		}
		return list.toArray(new Class<?>[]{});
	}
	public static String[] getNameArray(List<ParameterTO<?>> parameterMethodList) {
		List<String> list = new ArrayList<String>();
		for (ParameterTO<?> parameterMethod : parameterMethodList) {
			list.add(parameterMethod.getName());
		}
		return list.toArray(new String[]{});
	}
	public static Object[] getValueArray(List<ParameterTO<?>> parameterMethodList) {
		List<Object> list = new ArrayList<Object>();
		for (ParameterTO<?> parameterMethod : parameterMethodList) {
			list.add(parameterMethod.getValue());
		}
		return list.toArray(new Object[]{});
	}
}
