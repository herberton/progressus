package br.com.hcs.progressus.exception;

public class RollbackTransactionException extends ProgressusException {

	private static final long serialVersionUID = 5009355566167360439L;

	public RollbackTransactionException(Throwable cause) throws ProgressusException {
		super(RollbackTransactionException.class, cause);
	}
}
