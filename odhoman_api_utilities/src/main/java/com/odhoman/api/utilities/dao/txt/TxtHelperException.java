/**
 *
 * Extiende la clase Exception para TxtHelper
 *
 * @author tecso: 
 * @version  1.0
 */

package com.odhoman.api.utilities.dao.txt;

public class TxtHelperException extends Exception {
	/**
	 * Autogenerated UID
	 */
	private static final long serialVersionUID = 4373536220279172925L;

	public TxtHelperException() {
		super();
	}

	public TxtHelperException(String msg) {
		super(msg);
	}

	public TxtHelperException(Exception e) {
		super(e);
	}

	/**
	 * @see Exception#Exception(String, Throwable)
	 */
	public TxtHelperException(String message, Exception e) {
		super(message, e);
	}
}