package com.shadow.biz.excepton;

/**
 * @author guanliming
 *
 */
public class ReportBaseException extends RuntimeException {
	public ReportBaseException() {
		super();
	}

	public ReportBaseException(String message) {
		super(message);
	}

	public ReportBaseException(String message, Throwable cause) {
		super(message, cause);
	}

}
