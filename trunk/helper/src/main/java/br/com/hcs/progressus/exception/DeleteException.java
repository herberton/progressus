package br.com.hcs.progressus.exception;

import br.com.hcs.progressus.to.ParameterTO;



public class DeleteException extends ProgressusException {


	private static final long serialVersionUID = -5744689408587352762L;

	
	public DeleteException(String entityName) {
		super(DeleteException.class);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
	public DeleteException(String entityName, Throwable cause) throws ProgressusException {
		super(DeleteException.class, cause);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
}
