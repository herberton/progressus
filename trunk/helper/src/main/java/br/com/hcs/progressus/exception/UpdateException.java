package br.com.hcs.progressus.exception;

import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.to.ParameterTO;



public class UpdateException extends ProgressusException {

	private static final long serialVersionUID = 3765249947269339026L;

	public UpdateException(String entityName) {
		super(UpdateException.class);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
	public UpdateException(String entityName, Throwable cause) throws ProgressusException {
		super(UpdateException.class, cause);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
}
