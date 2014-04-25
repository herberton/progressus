package br.com.hcs.progressus.exception;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.to.ParameterTO;

@Slf4j
public class SelectException extends ProgressusException {

	private static final long serialVersionUID = -4666192873888252857L;

	public SelectException(String entityName) {
		super();
		try {
			super.getParameterList().add(new ParameterTO<>(0, entityName));
		} catch (ProgressusException e) {
			SelectException.log.error(e.getMessage(), e);
		}
	}
	public SelectException(Class<?> entityClazz) {
		super();
		try {
			super.getParameterList().add(new ParameterTO<>(0, StringHelper.getI18N(entityClazz)));
		} catch (ProgressusException e) {
			SelectException.log.error(e.getMessage(), e);
		}
	}
	public SelectException(String entityName, Throwable cause) {
		super(cause);
		try {
			super.getParameterList().add(new ParameterTO<>(0, entityName));
		} catch (ProgressusException e) {
			SelectException.log.error(e.getMessage(), e);
		}
	}
	public SelectException(Class<?> entityClazz, Throwable cause) {
		super(cause);
		try {
			super.getParameterList().add(new ParameterTO<>(0, StringHelper.getI18N(entityClazz)));
		} catch (ProgressusException e) {
			SelectException.log.error(e.getMessage(), e);
		}
	}
}
