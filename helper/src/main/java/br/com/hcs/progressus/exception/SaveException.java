package br.com.hcs.progressus.exception;

import br.com.hcs.progressus.to.ParameterTO;



public class SaveException extends ProgressusException {

	private static final long serialVersionUID = 6942883156952547090L;

	
	public SaveException(String entityName) {
		super(SaveException.class);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
	public SaveException(String entityName, Throwable cause) throws ProgressusException {
		super(SaveException.class, cause);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
}