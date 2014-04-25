package br.com.hcs.progressus.exception;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.to.ParameterTO;

@Slf4j
public class EmptyParameterException extends ProgressusException {

	private static final long serialVersionUID = -6281369842317439908L;

	
	public EmptyParameterException() {
		super();
	}
	public EmptyParameterException(String parameterName) {
		this();
		try {
			super.getParameterList().add(new ParameterTO<>(0, parameterName));
		} catch (ProgressusException e) {
			EmptyParameterException.log.error(e.getMessage(), e);
		}
	}
}
