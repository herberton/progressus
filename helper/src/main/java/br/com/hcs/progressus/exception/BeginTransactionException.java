package br.com.hcs.progressus.exception;

import br.com.hcs.progressus.exception.common.ProgressusException;

public class BeginTransactionException extends ProgressusException {

	private static final long serialVersionUID = -5078701415179817434L;

	public BeginTransactionException(Throwable cause) throws ProgressusException {
		super(cause);
	}
}