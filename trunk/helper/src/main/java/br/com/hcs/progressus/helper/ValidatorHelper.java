package br.com.hcs.progressus.helper;

import java.io.Serializable;

import br.com.hcs.progressus.exception.EmptyParameterException;
import br.com.hcs.progressus.exception.common.ProgressusException;

public class ValidatorHelper implements Serializable {
	
	private static final long serialVersionUID = -1490813784562560132L;
	
	public static <T> void validateFilling(String name, T parameter) throws ProgressusException {
		
		if (StringHelper.isNullOrEmpty(name) && parameter == null) {
			throw new EmptyParameterException();
		}
		
		if (parameter == null) {
			throw new EmptyParameterException(name);
		}
		
	}
	
	public static <T> void validateFilling(T parameter) throws ProgressusException {
		ValidatorHelper.validateFilling("", parameter);
	}

	public static <T> void validateFilling(Class<T> clazz, T parameter) throws ProgressusException {
		if (clazz == null) {
			ValidatorHelper.validateFilling(parameter); 
		}
		ValidatorHelper.validateFilling(StringHelper.getI18N(clazz), parameter);
	}
}
