package com.odhoman.api.utilities.paging;

public class PageIteratorException extends RuntimeException {

	private static final long serialVersionUID = 3373605344807041130L;

	public PageIteratorException() {		
	}

	public PageIteratorException(String message) {
		super(message);
	}

	public PageIteratorException(Throwable cause) {
		super(cause);
	}

	public PageIteratorException(String message, Throwable cause) {
		super(message, cause);
	}

}
