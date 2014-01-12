package br.com.hcs.progressus.exception;

import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.to.ParameterTO;



public class RemoveException extends ProgressusException {

	private static final long serialVersionUID = 7214466673821711345L;

	
	public RemoveException(String entityName) {
		super();
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
	public RemoveException(String entityName, Throwable cause) throws ProgressusException {
		super(cause);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
}
