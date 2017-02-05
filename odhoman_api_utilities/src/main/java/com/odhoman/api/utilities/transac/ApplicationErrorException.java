package com.odhoman.api.utilities.transac;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception para el manejo de errores
 * 
 * @author Fabian Benitez (fb70883)
 * @version 05/03/2010
 * 
 */
public class ApplicationErrorException extends RuntimeException {

	private static final long serialVersionUID = -3909774153517975870L;

	private List<ApplicationError> errors = new ArrayList<ApplicationError>();;

	public ApplicationErrorException() {
		super();
	}

	public ApplicationErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationErrorException(String message) {
		super(message);
	}

	public ApplicationErrorException(Throwable cause) {
		super(cause);
	}

	public void setErrors(List<ApplicationError> errors) {
		this.errors = errors;
	}

	public List<ApplicationError> getErrors() {
		return errors;
	}

	/**
	 * Inidica si existe un c&oacute;digo de error dado en la lista de errores
	 * 
	 * @param errorCode
	 *            C&oacute;digo de error buscado
	 * @return <code>true</code> si est&aacute; el c&oacute;digo de error entre los erroes de la lista.
	 *         <code>false</code> en caso contrario.
	 */
	public boolean containsErrorCode(String errorCode) {
		for (ApplicationError error : getErrors()) {
			if (errorCode.equals(error.getErrorCode())) {
				return true;
			}
		}

		return false;
	}

	public String getUserMessage() {
		if (errors.isEmpty()) {
			return super.getMessage();
		}
		// Hay errores
		StringBuffer sb = new StringBuffer("Cantidad de Errores: ");
		sb.append(errors.size());
		sb.append("\r");
		for (ApplicationError e : errors) {
			sb.append(e.getUserMessage());
			sb.append("\r");
		}
		return sb.toString();
	}

	public String getDetailedMessage() {
		if (errors.isEmpty()) {
			return super.getMessage();
		}
		// Hay errores
		StringBuffer sb = new StringBuffer("Cantidad de Errores: ");
		sb.append(errors.size());
		sb.append("\r");
		for (ApplicationError e : errors) {
			sb.append(e.getDetailedMessage());
			sb.append("\r");
		}
		return sb.toString();
	}

}
