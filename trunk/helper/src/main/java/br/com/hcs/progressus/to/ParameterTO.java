package br.com.hcs.progressus.to;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.CollectionHelper;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ParameterTO<T>  extends ProgressusTO<ParameterTO<T>> {

	private static final long serialVersionUID = -4466697084820407L;


	@Getter
	@Setter
	private int index;
	@Getter
	@Setter
	private Class<T> type;
	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private T value;
	
	
	public ParameterTO(Class<T> type, String name, T value) throws ProgressusException {
		this();
		try {
			this.setIndex(0);
			this.setType(type);
			this.setName(name);
			this.setValue(value);
		} catch (Exception e) {
			ParameterTO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("ParameterTO");
		}
	}
	@SuppressWarnings("unchecked")
	public ParameterTO(int index, T value) throws ProgressusException {
		this((Class<T>)value.getClass(), null, value);
		try {
			this.setIndex(index);
		} catch (Exception e) {
			ParameterTO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("ParameterTO");
		}
	}
	
	@Override
	public int compareTo(ParameterTO<T> other) {
		try {
			if (other == null) {
				return -1;
			}
			return new Integer(this.getIndex()).compareTo(new Integer(other.getIndex()));
		} catch (Exception e) {
			ParameterTO.log.error(e.getMessage(), e);
		}
		return super.compareTo(other);
	}
	

	public static final Class<?>[] getTypeArray(List<ParameterTO<?>> parameterMethodList) {
		
		try {
			
			if (CollectionHelper.isNullOrEmpty(parameterMethodList)) {
				return new Class<?>[]{};
			}
			
			List<Class<?>> list = new ArrayList<Class<?>>();
			
			for (ParameterTO<?> parameterMethod : parameterMethodList) {
				
				if (parameterMethod == null) {
					continue;
				}
				
				list.add(parameterMethod.getType());
			}
			
			return list.toArray(new Class<?>[]{});
			
		} catch (Exception e) {
			ParameterTO.log.error(e.getMessage(), e);
		}
		
		return new Class<?>[]{};
		
	}
	
	public static final String[] getNameArray(List<ParameterTO<?>> parameterMethodList) {
		
		try {
			
			if (CollectionHelper.isNullOrEmpty(parameterMethodList)) {
				return new String[]{};
			}
			
			List<String> list = new ArrayList<String>();
			
			for (ParameterTO<?> parameterMethod : parameterMethodList) {
				
				if (parameterMethod == null) {
					continue;
				}
				
				list.add(parameterMethod.getName());
			}
			
			return list.toArray(new String[]{});

		} catch (Exception e) {
			ParameterTO.log.warn(e.getMessage(), e);
		}
		
		return new String[]{};
	}

	public static final Object[] getValueArray(List<ParameterTO<?>> parameterMethodList) {
		
		try {
			
			if (CollectionHelper.isNullOrEmpty(parameterMethodList)) {
				return new Object[]{};
			}
			
			List<Object> list = new ArrayList<Object>();
			
			for (ParameterTO<?> parameterMethod : parameterMethodList) {
				
				if (parameterMethod == null) {
					continue;
				}
				
				list.add(parameterMethod.getValue());
			}
			
			return list.toArray(new Object[]{});
		
		} catch (Exception e) {
			ParameterTO.log.error(e.getMessage(), e);
		}
		
		return new Object[]{};
	}

	
	public static final ParameterTO<?> getInstance(int index, Object value) throws ProgressusException {
		try {
			if (value == null) {
				return null;
			}
			return new ParameterTO<>(index, value);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			ParameterTO.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("getInstance");
		}
	}
}
