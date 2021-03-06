package kr.co.kmac.pms.common.repository.exception;

public class RepositoryException extends RuntimeException {

	private static final long serialVersionUID = 4053521058256564355L;

	public RepositoryException() {
		super();
	}

	public RepositoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepositoryException(String message) {
		super(message);
	}

	public RepositoryException(Throwable cause) {
		super(cause);
	}
}
