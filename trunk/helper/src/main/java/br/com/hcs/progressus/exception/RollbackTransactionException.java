package br.com.hcs.progressus.exception;

import br.com.hcs.progressus.exception.common.ProgressusException;

public class RollbackTransactionException extends ProgressusException {

	private static final long serialVersionUID = 5009355566167360439L;

	public RollbackTransactionException(Throwable cause) throws ProgressusException {
		super(RollbackTransactionException.class, cause);
	}
}
