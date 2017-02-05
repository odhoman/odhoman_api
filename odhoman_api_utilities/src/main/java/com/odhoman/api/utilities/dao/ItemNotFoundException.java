package com.odhoman.api.utilities.dao;

public class ItemNotFoundException extends DAOException {
	
	private static final long serialVersionUID = 18180042825650101L;

	public ItemNotFoundException() {
		super();
	}

	public ItemNotFoundException(String msg) {
		super(msg);
	}

	public ItemNotFoundException(Exception e) {
		super(e);
	}

	public ItemNotFoundException(String msg, Exception e) {
		super(msg, e);
	}
}
