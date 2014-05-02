package  br.com.hcs.progressus.helper;

import java.io.Serializable;
import java.util.Collection;

import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;

public class ObjectHelper implements Serializable {

	private static final long serialVersionUID = 539195880585172943L;

	public static final boolean isNullOrEmpty(Object object) throws ProgressusException {
		try {
			
			if (object == null) {
				return true;
			}
			
			if (object.getClass().isPrimitive()) {
				
				if (object instanceof Number) {
					return ((Number)object).intValue() == 0;
				}
				
				return StringHelper.isNullOrEmpty(object.toString());
			}
			
			if (object instanceof String) {
				return StringHelper.isNullOrEmpty(object.toString());
			}
			
			if (object instanceof Collection) {
				return CollectionHelper.isNullOrEmpty((Collection<?>)object);
			}
			
			if (object.getClass().isArray()) {
				return CollectionHelper.isNullOrEmpty((Object[])object);
			}
			
			return false; 
				
		} catch (ProgressusException pe) {
			throw pe;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isNullOrEmpty", e);
		}
	}
}
