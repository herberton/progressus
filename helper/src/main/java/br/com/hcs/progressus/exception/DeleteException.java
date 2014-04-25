package br.com.hcs.progressus.exception;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.to.ParameterTO;

@Slf4j
public class DeleteException extends ProgressusException {

	private static final long serialVersionUID = 3410270324013884578L;

	
	public DeleteException(String entityName) {
		super();
		try {
			super.getParameterList().add(new ParameterTO<>(0, entityName));
		} catch (ProgressusException e) {
			DeleteException.log.error(e.getMessage(), e);
		}
	}
	public DeleteException(Class<?> entityClazz) {
		super();
		try {
			super.getParameterList().add(new ParameterTO<>(0, StringHelper.getI18N(entityClazz)));
		} catch (ProgressusException e) {
			DeleteException.log.error(e.getMessage(), e);
		}
	}
	public DeleteException(String entityName, Throwable cause) {
		super(cause);
		try {
			super.getParameterList().add(new ParameterTO<>(0, entityName));
		} catch (ProgressusException e) {
			DeleteException.log.error(e.getMessage(), e);
		}
	}
	public DeleteException(Class<?> entityClazz, Throwable cause) {
		super(cause);
		try {
			super.getParameterList().add(new ParameterTO<>(0, StringHelper.getI18N(entityClazz)));
		} catch (ProgressusException e) {
			DeleteException.log.error(e.getMessage(), e);
		}
	}
}
