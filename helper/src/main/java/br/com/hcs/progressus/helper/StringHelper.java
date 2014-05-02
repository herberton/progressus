package  br.com.hcs.progressus.helper;

import java.io.Serializable;

import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;

public final class StringHelper implements Serializable {

	private static final long serialVersionUID = -6703583757542354987L;
	
	
	public static final <T> String getI18N(Class<T> clazz) throws ProgressusException {
		try {
			if (clazz == null) {
				return "";
			}
			return clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().subSequence(1, clazz.getSimpleName().length());
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getI18N", e);
		}
	}
	
	public static final <E extends Enum<E>> String getI18N(Enum<E> enumerate) throws ProgressusException {
		try {
			if (enumerate == null) {
				return "";
			}
			return "enum." + enumerate.name();
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getI18N", e);
		}
	}
	
	
	public static final boolean isNullOrEmpty(String string) throws ProgressusException {
		try {
			return string == null || string.isEmpty();
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isNullOrEmpty", e);
		}
	}
	
	
	public static final boolean isNumeric(String string) throws ProgressusException {
		
		if (StringHelper.isNullOrEmpty(string)) {
			return false;
		}
		
		try {
			// "((-|\\+)?[0-9]+(\\.[0-9]+)?)+"
			return string.matches("[-+]?\\d*\\.?\\d+");
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isNumeric", e);
		}
    }

	
	public static final String getGetter(String fieldName) throws ProgressusException {
		
		if (StringHelper.isNullOrEmpty(fieldName)) {
			return "";
		}
		
		try {
			
			return String.format("get%s", fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getGetter", e);
		}
		
	}
	
	public static final String getSetter(String fieldName) throws ProgressusException {
		
		if (StringHelper.isNullOrEmpty(fieldName)) {
			return "";
		}
		
		try {
			return String.format("set%s", fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSetter", e);
		}
	}
}
