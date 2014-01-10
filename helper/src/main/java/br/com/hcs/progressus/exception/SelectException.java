package br.com.hcs.progressus.exception;

import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.to.ParameterTO;



public class SelectException extends ProgressusException {

	
	private static final long serialVersionUID = 1310086877600843884L;

	
	public SelectException(String entityName) {
		super(SelectException.class);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
	public SelectException(String entityName, Throwable cause) throws ProgressusException {
		super(SelectException.class, cause);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
}
