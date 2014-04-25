package br.com.hcs.progressus.exception;

public class RollbackTransactionException extends ProgressusException {

	private static final long serialVersionUID = 3430137455227610533L;

	
	public RollbackTransactionException(Throwable cause) {
		super(cause);
	}
}
