package com.odhoman.api.utilities.transac;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fabian Benitez (fb70883)
 * @date 05/03/2010
 * 
 * Implementacion por defecto del estado de una transaccion
 */

public class TransactionStatusImpl implements TransactionStatus {

	private boolean isOk = false;
	private List<ErrorCode> errors = new ArrayList<ErrorCode>();
		
	public TransactionStatusImpl(boolean isOk, ErrorCode errorCode) {
		addError(errorCode);
		this.isOk = isOk;
	}
	
	public TransactionStatusImpl(boolean isOk, List<ErrorCode> errorCodes) {
		setErrors(errorCodes);		
		this.isOk = isOk;
	}
	
	public List<ErrorCode> getErrorCodes() {
		return errors;
	}

	public int getErrorQuantity() {
		return errors.size();
	}

	public boolean isOk() {
		return isOk;
	}
	
	public void addError(ErrorCode errorCode) {
		if(errorCode != null)
			errors.add(errorCode);
	}
	
	public void setErrors(List<ErrorCode> errorCodes) {
		if(errorCodes != null)
			this.errors = errorCodes;
	}

}
