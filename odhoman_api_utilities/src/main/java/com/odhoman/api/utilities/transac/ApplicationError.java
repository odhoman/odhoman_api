package com.odhoman.api.utilities.transac;

import java.io.Serializable;

/**
 * @author Fabian Benitez (fb70883)
 * @version 05/03/2010
 * 
 * Representa un error en la aplicacion. 
 * 
 * Tiene un mensaje de usuario y un mensaje detallado
 */
public class ApplicationError implements Serializable {

	/**
	 * Automatically generated UID
	 */
	private static final long serialVersionUID = 7163960148281092556L;

	private String errorCode;
	
	private String detailedMsg;
	
	private String userMsg;
	
	public ApplicationError(String errorCode, String msg) {
		setErrorCode(errorCode);
		setDetailedMessage(msg);
		setUserMessage(msg);
	}
	
	public ApplicationError(String errorCode, String detailedMsg, String userMsg) {
		setErrorCode(errorCode);
		setDetailedMessage(detailedMsg);
		setUserMessage(userMsg);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDetailedMessage() {
		return detailedMsg;
	}

	public void setDetailedMessage(String detailedMsg) {
		this.detailedMsg = detailedMsg;
	}

	public String getUserMessage() {
		return userMsg;
	}

	public void setUserMessage(String userMsg) {
		this.userMsg = userMsg;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("ApplicationError:");
		sb.append(" detailedMsg=");
		sb.append(getDetailedMessage());
		sb.append(" userMsg=");
		sb.append(getUserMessage());
		return sb.toString();
	}
	
	
	
}
