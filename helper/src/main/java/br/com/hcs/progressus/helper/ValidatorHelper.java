package  br.com.hcs.progressus.helper;

import java.io.Serializable;

import br.com.hcs.progressus.exception.EmptyParameterException;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;

public final class ValidatorHelper implements Serializable {

	private static final long serialVersionUID = 8679178811646057572L;
	
	
	public static final <T> void validateFilling(String name, T parameter) throws ProgressusException {
		try {
			if (ObjectHelper.isNullOrEmpty(parameter)) {
				throw new EmptyParameterException(StringHelper.isNullOrEmpty(name) ? "" : name);
			}
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("validateFilling", e);
		}
	}
	
	public static final <T> void validateFilling(T parameter) throws ProgressusException {
		try {
			ValidatorHelper.validateFilling("", parameter);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("validateFilling", e);
		}
	}

	public static final <T> void validateFilling(Class<T> clazz, T parameter) throws ProgressusException {
		try {
			if (clazz == null) {
				ValidatorHelper.validateFilling(parameter); 
			}
			ValidatorHelper.validateFilling(StringHelper.getI18N(clazz), parameter);
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("validateFilling", e);
		}
	}
}
