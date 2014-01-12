package br.com.hcs.progressus.exception;

import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.to.ParameterTO;

public class CountException extends ProgressusException {
	
	private static final long serialVersionUID = 4085471892199750157L;
	
	public CountException(String entityName) {
		super();
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
	public CountException(String entityName, Throwable cause) throws ProgressusException {
		super(cause);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
}
