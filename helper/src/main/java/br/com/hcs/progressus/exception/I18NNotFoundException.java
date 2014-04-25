package br.com.hcs.progressus.exception;

import java.util.Locale;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.to.ParameterTO;

@Slf4j
public class I18NNotFoundException extends ProgressusException {
	private static final long serialVersionUID = 678396400758073404L;
	public I18NNotFoundException(String message, Locale locale) {
		super();
		try {
			super.getParameterList().add(new ParameterTO<>(0, message));
			super.getParameterList().add(new ParameterTO<>(1, locale));
		} catch (ProgressusException e) {
			I18NNotFoundException.log.error(e.getMessage(), e);
		}
	}
}
