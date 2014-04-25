package br.com.hcs.progressus.exception;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.to.ParameterTO;

@Slf4j
public class SaveException extends ProgressusException {

	private static final long serialVersionUID = 3664800289478339913L;

	
	public SaveException(String entityName) {
		super();
		try {
			super.getParameterList().add(new ParameterTO<>(0, entityName));
		} catch (ProgressusException e) {
			SaveException.log.error(e.getMessage(), e);
		}
	}
	public SaveException(Class<?> entityClazz) {
		super();
		try {
			super.getParameterList().add(new ParameterTO<>(0, StringHelper.getI18N(entityClazz)));
		} catch (ProgressusException e) {
			SaveException.log.error(e.getMessage(), e);
		}
	}
	public SaveException(String entityName, Throwable cause) {
		super(cause);
		try {
			super.getParameterList().add(new ParameterTO<>(0, entityName));
		} catch (ProgressusException e) {
			SaveException.log.error(e.getMessage(), e);
		}
	}
	public SaveException(Class<?> entityClazz, Throwable cause) {
		super(cause);
		try {
			super.getParameterList().add(new ParameterTO<>(0, StringHelper.getI18N(entityClazz)));
		} catch (ProgressusException e) {
			SaveException.log.error(e.getMessage(), e);
		}
	}
}
