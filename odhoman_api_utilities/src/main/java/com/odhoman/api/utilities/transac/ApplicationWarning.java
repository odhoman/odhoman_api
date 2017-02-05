package com.odhoman.api.utilities.transac;

import java.io.Serializable;

/**
 * @author Fabian Benitez (fb70883)
 * @date 05/03/2010
 * 
 * Representa un warning en la aplicacion. 
 * 
 * Tiene un mensaje detallado
 */

public class ApplicationWarning implements Serializable{
	
	/**
	 * Automatically generated UID
	 */
	private static final long serialVersionUID = 7374091452679933639L;
	
	private String detailedMsg;
		
	public ApplicationWarning(String msg) {
		setDetailedMessage(msg);	
	}
	
	public String getDetailedMessage() {
		return detailedMsg;
	}

	public void setDetailedMessage(String detailedMsg) {
		this.detailedMsg = detailedMsg;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("ApplicationWarning:");
		sb.append(" detailedMsg=");
		sb.append(getDetailedMessage());		
		return sb.toString();
	}
	
}
