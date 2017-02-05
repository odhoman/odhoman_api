package com.odhoman.api.utilities.dependencyinjector;

public class DependencyInjectorException extends Exception {

	private static final long serialVersionUID = 390524133281801430L;

	public DependencyInjectorException() {
	
	}

	public DependencyInjectorException(String message) {
		super(message);
	}

	public DependencyInjectorException(Throwable cause) {
		super(cause);
	}

	public DependencyInjectorException(String message, Throwable cause) {
		super(message, cause);
	}

}
