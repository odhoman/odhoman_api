package com.odhoman.api.utilities.paging;

public class NoMoreItemsException extends PageIteratorException {

	private static final long serialVersionUID = 4013729095162235241L;

	public NoMoreItemsException() {		
	}

	public NoMoreItemsException(String message) {
		super(message);
	}

	public NoMoreItemsException(Throwable cause) {
		super(cause);
	}

	public NoMoreItemsException(String message, Throwable cause) {
		super(message, cause);
	}

}
