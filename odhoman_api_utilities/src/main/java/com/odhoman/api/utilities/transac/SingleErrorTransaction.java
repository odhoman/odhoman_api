package com.odhoman.api.utilities.transac;

/**
 * @author Fabian Benitez (fb70883)
 * @date 08/03/2010
 * 
 * Maneja transacciones con un solo error
 */

public class SingleErrorTransaction extends AbstractTransaction {

	private static final long serialVersionUID = 8912856647998775858L;

	private ErrorCode errorCode = null;
	
	public SingleErrorTransaction() {
		super();
		setMdInfo();
	}
	
	public SingleErrorTransaction(String okFlag, ErrorCode errorCode) {
		this();
		setOkFlag(okFlag);		
		setErrorCode(errorCode);		
	}
	
	private void setMdInfo() {
		setFieldInfo("errorCode", "com.citi.common.util.transac.ErrorCode", 3);
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public TransactionStatus getTransactionStatus() {
		return new TransactionStatusImpl(OK.equals(getOkFlag()), getErrorCode());
	}
	
}
