package br.com.hcs.progressus.exception;

import br.com.hcs.progressus.to.ParameterTO;

public class GetDAOException extends ProgressusException {

	private static final long serialVersionUID = -2248444289697187637L;

	public GetDAOException(String entityName) {
		super(GetDAOException.class);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
	public GetDAOException(String entityName, Throwable cause) throws ProgressusException {
		super(GetDAOException.class, cause);
		super.getParameterList().add(new ParameterTO<>(0, entityName));
	}
}