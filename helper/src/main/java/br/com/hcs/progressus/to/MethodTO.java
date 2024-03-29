package br.com.hcs.progressus.to;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;
import br.com.hcs.progressus.helper.CollectionHelper;
import br.com.hcs.progressus.helper.ReflectionHelper;

public class MethodTO implements Serializable {
	
	private static final long serialVersionUID = 2701723307246976637L;
	
	@Getter
	@Setter
	private String name;
	@Setter
	private List<ParameterTO<?>> parameterList; 
	
	
	public MethodTO(String name) throws ProgressusException {
		super();
		try {
			this.setName(name);
			this.addParameter(this.getDefaultParameterList());
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("newMethodTO", e);
		}
	}
	
	public MethodTO(String name, ParameterTO<?>...parameterArray) throws ProgressusException {
		this(name);
		try {
			this.addParameter(parameterArray);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("newMethodTO", e);
		}
	}
	
	
	private List<ParameterTO<?>> getParameterList() throws ProgressusException {
		try {
			if (parameterList == null) {
				this.setParameterList(new ArrayList<ParameterTO<?>>());
			}
			return parameterList;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getParameterList", e);
		}
	}

	
	public Class<?>[] getParameterTypeArray() throws ProgressusException {
		try {
			List<Class<?>> parameterTypeList = new ArrayList<Class<?>>();
			for (ParameterTO<?> parameter : this.getParameterList()) {
				parameterTypeList.add(parameter.getType());
			}
			return parameterTypeList.toArray(new Class<?>[]{});
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getParameterTypeArray", e);
		}
	}
	
	public Object[] getParameterValueArray() throws ProgressusException {
		try {
			List<Object> parameterValueList = new ArrayList<>();
			for (ParameterTO<?> parameter : this.getParameterList()) {
				parameterValueList.add(parameter.getValue());
			}
			return parameterValueList.toArray(new Object[]{});
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getParameterValueArray", e);
		}
	}

	
	@SuppressWarnings("unchecked")
	public <T> void setParameterValue(String parameterName, T parameterValue) throws ProgressusException {
		try {
			for (ParameterTO<?> parameter : this.getParameterList()) {
				if (parameter.getName().equals(parameterName)) {
					((ParameterTO<T>)parameter).setValue(parameterValue);
					break;
				}
			}
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("setParameterValue", e);
		}
	}
	
	
	public void addParameter(ParameterTO<?> parameter) throws ProgressusException {
		try {
			CollectionHelper.add(this.getParameterList(), parameter);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("addParameter", e);
		}
	}
	
	public void addParameter(List<ParameterTO<?>> parameterList) throws ProgressusException {
		try {
			CollectionHelper.add(this.getParameterList(), parameterList);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("addParameter", e);
		}
	}
	
	public void addParameter(ParameterTO<?>...parameterArray) throws ProgressusException {
		try {
			CollectionHelper.add(this.getParameterList(), parameterArray);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("addParameter", e);
		}
	}

	
	public Method getMethod(Class<?> clazz) throws ProgressusException {
		
		try {
			return
				ReflectionHelper.findMethod(clazz, this.getName(), this.getParameterTypeArray());
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getMethod", e);
		}
	}
	
	
	public List<ParameterTO<?>> getDefaultParameterList() throws ProgressusException {
		return new ArrayList<>();
	}
}
