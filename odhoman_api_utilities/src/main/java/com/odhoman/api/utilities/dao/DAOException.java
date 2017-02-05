/**
 * DAOException
 *
 * Extiende la clase Exception para DAO del common
 *
 * @author tecso: 
 * @version  1.0
 */

package com.odhoman.api.utilities.dao;

public class DAOException extends Exception {
	
	private static final long serialVersionUID = 18180042825650101L;

	public DAOException() {
		super();
	}

	public DAOException(String msg) {
		super(msg);
	}

	public DAOException(Exception e) {
		super(e);
	}

	public DAOException(String msg, Exception e) {
		super(msg, e);
	}
}
