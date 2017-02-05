package com.odhoman.api.utilities.shortcut;

public class NoShortcutDefinedException extends Exception {

	private static final long serialVersionUID = 313627650463193348L;

	public NoShortcutDefinedException() {
	}

	public NoShortcutDefinedException(String message) {
		super(message);
	}

	public NoShortcutDefinedException(Throwable cause) {
		super(cause);
	}

	public NoShortcutDefinedException(String message, Throwable cause) {
		super(message, cause);
	}

}
