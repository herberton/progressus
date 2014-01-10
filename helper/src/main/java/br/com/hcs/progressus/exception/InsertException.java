package br.com.hcs.progressus.exception;

import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.to.ParameterTO;


public class InsertException extends ProgressusException {

	
	private static final long serialVersionUID = -6057423217852444386L;
	
	
	public InsertException(String entityName) {
		super(InsertException.class);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
	public InsertException(String entityName, Throwable cause) throws ProgressusException {
		super(InsertException.class, cause);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
}
