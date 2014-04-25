package  br.com.hcs.progressus.helper;

import java.io.Serializable;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;

@Slf4j
public final class StringHelper implements Serializable {

	private static final long serialVersionUID = -6703583757542354987L;
	
	
	public static final <T> String getI18N(Class<T> clazz) throws ProgressusException {
		try {
			if (clazz == null) {
				return "";
			}
			return clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().subSequence(1, clazz.getSimpleName().length());
		} catch (Exception e) {
			StringHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("getI18N");
		}
	}
	
	public static final <E extends Enum<E>> String getI18N(Enum<E> enumerate) throws ProgressusException {
		try {
			if (enumerate == null) {
				return "";
			}
			return "enum." + enumerate.name();
		} catch (Exception e) {
			StringHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("getI18N");
		}
	}
	
	
	public static final boolean isNullOrEmpty(String string) throws ProgressusException {
		try {
			return string == null || string.isEmpty();
		} catch (Exception e) {
			StringHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("isNullOrEmpty");
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
			StringHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("isNumeric");
		}
    }

	
	public static final String getGetter(String fieldName) throws ProgressusException {
		
		if (StringHelper.isNullOrEmpty(fieldName)) {
			return "";
		}
		
		try {
			
			return String.format("get%s", fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
		} catch (Exception e) {
			StringHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("getGetter");
		}
		
	}
	
	public static final String getSetter(String fieldName) throws ProgressusException {
		
		if (StringHelper.isNullOrEmpty(fieldName)) {
			return "";
		}
		
		try {
			return String.format("set%s", fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
		} catch (Exception e) {
			StringHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("getSetter");
		}
	}
}
