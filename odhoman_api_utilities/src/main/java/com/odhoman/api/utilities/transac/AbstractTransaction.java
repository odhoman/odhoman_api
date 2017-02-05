package com.odhoman.api.utilities.transac;

/**
 * @author Fabian Benitez (fb70883)
 * @date 08/03/2010
 * 
 * Clase abstracta para la manipulacion de transacciones (gralmente hacia el backend)
 */

public abstract class AbstractTransaction extends MdTransaction implements TransactionStatusNotifier {

	private static final long serialVersionUID = 30283264881228916L;

	private String okFlag = "";
	private ApplicationWarningException warningException = null;
	
	public static final String OK = "Y";
	public static final String NO_OK = "N";
	
	public AbstractTransaction() {
		super();
		setMdInfo();
	}
	
	private void setMdInfo() {
		setFieldInfo("okFlag", "String", 1);
	}

	public String getOkFlag() {
		return okFlag;
	}

	public void setOkFlag(String okFlag) {
		this.okFlag = okFlag;
	}
	
	public void setWarningException(ApplicationWarningException warning) {
		warningException = warning;
	}
	
	public boolean isThereAnyWarning() {
		return warningException != null;
	}
	
	public ApplicationWarningException getWarningException() {
		return warningException;
	}
}
