package br.com.hcs.progressus.helper;

import br.com.hcs.progressus.exception.EmptyParameterException;
import br.com.hcs.progressus.exception.ProgressusException;

public class ValidateHelper {
	
	public static <X> void validateFilling(String name, X parameter) throws ProgressusException {
		if (StringHelper.isNullOrEmpty(name) && parameter == null) {
			throw new EmptyParameterException();
		}
		
		if (parameter == null) {
			throw new EmptyParameterException(name);
		}
	}
	
	public static <X> void validateFilling(X parameter) throws ProgressusException {
		ValidateHelper.validateFilling("", parameter);
	}

	public static <X> void validateFilling(Class<X> clazz, X parameter) throws ProgressusException {
		if (clazz == null) {
			ValidateHelper.validateFilling(parameter); 
		}
		ValidateHelper.validateFilling(StringHelper.getI18N(clazz), parameter);
	}
}
