package  br.com.hcs.progressus.helper;

import java.io.Serializable;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.exception.ProgressusException;
import br.com.hcs.progressus.exception.UnableToCompleteOperationException;

@Slf4j
public final class MapHelper implements Serializable {

	private static final long serialVersionUID = 1114599505122531801L;

	
	public static final <K, V> boolean isNullOrEmpty(Map<K, V> map) throws ProgressusException {
		try {
			return map == null || map.size() <= 0;
		} catch (Exception e) {
			MapHelper.log.error(e.getMessage(), e);
			throw new UnableToCompleteOperationException("isNullOrEmpty");
		}
	}
}
