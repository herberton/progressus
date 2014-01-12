package br.com.hcs.progressus.exception;

import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.to.ParameterTO;



public class EntityNotFoundException extends ProgressusException {

	private static final long serialVersionUID = 8635604731989547524L;

	public EntityNotFoundException(String entityName) {
		super();
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
	public EntityNotFoundException(String entityName, Throwable cause) throws ProgressusException {
		super(cause);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
}
