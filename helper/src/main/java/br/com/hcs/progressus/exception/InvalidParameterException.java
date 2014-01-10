package br.com.hcs.progressus.exception;

import br.com.hcs.progressus.to.ParameterTO;



public class InvalidParameterException extends ProgressusException {

	private static final long serialVersionUID = 455564452605760207L;

	public InvalidParameterException(String parameter) {
		super(InvalidParameterException.class);
		super.getParameterList().add(new ParameterTO<>(0, parameter));
	}
	public InvalidParameterException(String parameter, Throwable cause) throws ProgressusException {
		super(InvalidParameterException.class, cause);
		super.getParameterList().add(new ParameterTO<>(0, parameter));
	}
}