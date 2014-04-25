package br.com.hcs.progressus.exception;

import java.util.Locale;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.to.ParameterTO;

@Slf4j
public class InvalidI18NMessageException extends ProgressusException {

	private static final long serialVersionUID = 1713448874114797531L;

	
	public InvalidI18NMessageException(String message, Locale locale) {
		super();
		try {
			super.getParameterList().add(new ParameterTO<>(0, message));
			super.getParameterList().add(new ParameterTO<>(1, locale));
		} catch (ProgressusException e) {
			InvalidI18NMessageException.log.error(e.getMessage(), e);
		}
	}
}
