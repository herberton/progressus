package br.com.hcs.progressus.exception;

public class CommitTransactionException extends ProgressusException {

	private static final long serialVersionUID = -5890444826197928272L;

	public CommitTransactionException(Throwable cause) throws ProgressusException {
		super(CommitTransactionException.class, cause);
	}
}
