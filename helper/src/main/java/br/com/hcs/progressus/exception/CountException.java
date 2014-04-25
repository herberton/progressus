package br.com.hcs.progressus.exception;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.to.ParameterTO;

@Slf4j
public class CountException extends ProgressusException {

	private static final long serialVersionUID = 7877657892175757985L;

	public CountException(String entityName) {
		super();
		try {
			super.getParameterList().add(new ParameterTO<>(0, entityName));
		} catch (ProgressusException e) {
			CountException.log.error(e.getMessage(), e);
		}
	}
	public CountException(Class<?> entityClazz) {
		super();
		try {
			super.getParameterList().add(new ParameterTO<>(0, StringHelper.getI18N(entityClazz)));
		} catch (ProgressusException e) {
			CountException.log.error(e.getMessage(), e);
		}
	}
	public CountException(String entityName, Throwable cause) {
		super(cause);
		try {
			super.getParameterList().add(new ParameterTO<>(0, entityName));
		} catch (ProgressusException e) {
			CountException.log.error(e.getMessage(), e);
		}
	}
	public CountException(Class<?> entityClazz, Throwable cause) {
		super(cause);
		try {
			super.getParameterList().add(new ParameterTO<>(0, StringHelper.getI18N(entityClazz)));
		} catch (ProgressusException e) {
			CountException.log.error(e.getMessage(), e);
		}
	}
}
