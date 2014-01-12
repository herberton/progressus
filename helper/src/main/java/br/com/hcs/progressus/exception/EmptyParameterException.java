package br.com.hcs.progressus.exception;

import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.to.ParameterTO;



public class EmptyParameterException extends ProgressusException {

	private static final long serialVersionUID = -7412695787511450192L;
	
	public EmptyParameterException() {
		super();
	}
	public EmptyParameterException(String parameterName) {
		this();
		super.getParameterList().add(new ParameterTO<>(0, parameterName));
	}
}
