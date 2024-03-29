package br.com.hcs.progressus.exception;

import lombok.extern.slf4j.Slf4j;
import br.com.hcs.progressus.helper.ObjectHelper;
import br.com.hcs.progressus.helper.StringHelper;
import br.com.hcs.progressus.to.ParameterTO;

@Slf4j
public class UnableToCompleteOperationException extends ProgressusException {
	
	private static final long serialVersionUID = 6306135818290124908L;
	
	
	public UnableToCompleteOperationException(String operation, String cause) {
		super();
		try {
			super.getParameterList().add(new ParameterTO<>(0, operation));
			super.getParameterList().add(new ParameterTO<>(1, cause));
		} catch (ProgressusException e) {
			UnableToCompleteOperationException.log.error(e.getMessage(), e);
		}
	}
	public UnableToCompleteOperationException(String operation, Object cause) {
		this(operation, "unknown");
		try {
			if (ObjectHelper.isNullOrEmpty(cause)) {
				return;
			}
			super.getParameterList().set(1, new ParameterTO<>(1, StringHelper.getI18N(cause.getClass())));
		} catch (ProgressusException e) {
			// TODO: handle exception
		}
	}
}
