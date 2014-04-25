package br.com.hcs.progressus.exception;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.to.ParameterTO;

@Slf4j
public class InsertException extends ProgressusException {

	private static final long serialVersionUID = 3673081547691435607L;

	
	public InsertException(String entityName) {
		super();
		try {
			super.getParameterList().add(new ParameterTO<>(0, entityName));
		} catch (ProgressusException e) {
			InsertException.log.error(e.getMessage(), e);
		}
	}
	public InsertException(Class<?> entityClazz) {
		super();
		try {
			super.getParameterList().add(new ParameterTO<>(0, StringHelper.getI18N(entityClazz)));
		} catch (ProgressusException e) {
			InsertException.log.error(e.getMessage(), e);
		}
	}
	public InsertException(String entityName, Throwable cause) {
		super(cause);
		try {
			super.getParameterList().add(new ParameterTO<>(0, entityName));
		} catch (ProgressusException e) {
			InsertException.log.error(e.getMessage(), e);
		}
	}
	public InsertException(Class<?> entityClazz, Throwable cause) {
		super(cause);
		try {
			super.getParameterList().add(new ParameterTO<>(0, StringHelper.getI18N(entityClazz)));
		} catch (ProgressusException e) {
			InsertException.log.error(e.getMessage(), e);
		}
	}
}
