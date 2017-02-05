package com.odhoman.api.utilities.transac;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fabian Benitez (fb70883)
 * @date 05/03/2010
 * 
 * Exception para el manejo de warnings
 */

public class ApplicationWarningException extends RuntimeException {
	
	private static final long serialVersionUID = -5570846019897317780L;

	private List<ApplicationWarning> warnings = new ArrayList<ApplicationWarning>();
			
	public ApplicationWarningException() {
		super();		
	}

	public ApplicationWarningException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationWarningException(String message) {
		super(message);
	}

	public ApplicationWarningException(Throwable cause) {
		super(cause);
	}

	public void setWarnings(List<ApplicationWarning> warnings) {
		this.warnings = warnings;
	}
	
	public List<ApplicationWarning> getWarnings() {
		return warnings;
	}
		
	public String getDetailedMessage() {
		if(warnings.isEmpty())
			return super.getMessage();
		//Hay warnings
		StringBuffer sb = new StringBuffer("Cantidad de Warnings: ");
		sb.append(warnings.size());
		sb.append("\r");
		for(ApplicationWarning w: warnings) {
			sb.append(w.getDetailedMessage());
			sb.append("\r");
		}
		return sb.toString();
	}
}