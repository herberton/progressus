package  br.com.hcs.progressus.helper;

import java.io.Serializable;
import java.util.Map;

import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;

public final class MapHelper implements Serializable {

	private static final long serialVersionUID = 1114599505122531801L;

	
	public static final <K, V> boolean isNullOrEmpty(Map<K, V> map) throws ProgressusException {
		try {
			return map == null || map.size() <= 0;
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isNullOrEmpty", e);
		}
	}
}
