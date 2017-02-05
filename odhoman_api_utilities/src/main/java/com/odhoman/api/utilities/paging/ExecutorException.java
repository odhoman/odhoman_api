package com.odhoman.api.utilities.paging;

public class ExecutorException extends Exception {

	private static final long serialVersionUID = 2585128120600782063L;

	public ExecutorException() {
	
	}

	public ExecutorException(String message) {
		super(message);
	}

	public ExecutorException(Throwable cause) {
		super(cause);
	}

	public ExecutorException(String message, Throwable cause) {
		super(message, cause);
	}

}
