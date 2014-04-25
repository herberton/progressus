package br.com.hcs.progressus.exception;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.to.ParameterTO;

@Slf4j
public class InvalidParameterException extends ProgressusException {

	private static final long serialVersionUID = -2419735074784367180L;

	public InvalidParameterException(String parameter) {
		super();
		try {
			super.getParameterList().add(new ParameterTO<>(0, parameter));
		} catch (ProgressusException e) {
			InvalidParameterException.log.error(e.getMessage(), e);
		}
	}
	public InvalidParameterException(String parameter, Throwable cause) {
		super(cause);
		try {
			super.getParameterList().add(new ParameterTO<>(0, parameter));
		} catch (ProgressusException e) {
			InvalidParameterException.log.error(e.getMessage(), e);
		}
	}
}
