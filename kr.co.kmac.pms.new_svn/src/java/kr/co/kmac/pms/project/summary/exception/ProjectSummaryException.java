package kr.co.kmac.pms.project.summary.exception;

public class ProjectSummaryException extends RuntimeException {	
	private static final long serialVersionUID = 1L;

	public ProjectSummaryException() {
		super();
	}

	public ProjectSummaryException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProjectSummaryException(String message) {
		super(message);
	}

	public ProjectSummaryException(Throwable cause) {
		super(cause);
	}
}
