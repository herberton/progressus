package br.com.hcs.progressus.exception;

import br.com.hcs.progressus.to.ParameterTO;



public class RemoveException extends ProgressusException {

	private static final long serialVersionUID = 7214466673821711345L;

	
	public RemoveException(String entityName) {
		super(RemoveException.class);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
	public RemoveException(String entityName, Throwable cause) throws ProgressusException {
		super(RemoveException.class, cause);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
}
