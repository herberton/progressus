package br.com.hcs.progressus.exception;

import br.com.hcs.progressus.exception.common.ProgressusException;
import br.com.hcs.progressus.to.ParameterTO;

public class GetDAOException extends ProgressusException {

	private static final long serialVersionUID = -2248444289697187637L;

	public GetDAOException(String entityName) {
		super();
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
	public GetDAOException(String entityName, Throwable cause) throws ProgressusException {
		super(cause);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
}
