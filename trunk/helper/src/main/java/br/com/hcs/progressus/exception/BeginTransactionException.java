package br.com.hcs.progressus.exception;

public class BeginTransactionException extends ProgressusException {

	private static final long serialVersionUID = -5078701415179817434L;

	public BeginTransactionException(Throwable cause) throws ProgressusException {
		super(BeginTransactionException.class, cause);
	}
}
