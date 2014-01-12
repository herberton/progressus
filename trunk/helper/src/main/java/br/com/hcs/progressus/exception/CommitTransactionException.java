package br.com.hcs.progressus.exception;

import br.com.hcs.progressus.exception.common.ProgressusException;

public class CommitTransactionException extends ProgressusException {

	private static final long serialVersionUID = -5890444826197928272L;

	public CommitTransactionException(Throwable cause) throws ProgressusException {
		super(cause);
	}
}
