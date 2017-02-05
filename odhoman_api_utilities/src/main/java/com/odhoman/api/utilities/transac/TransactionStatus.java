package com.odhoman.api.utilities.transac;

import java.util.List;

/**
 * @author Fabian Benitez (fb70883)
 * @date 05/03/2010
 * 
 * Permite conocer el estado de una transaccion
 */

public interface TransactionStatus {
	
	/** Informa si la transaccion esta ok o no*/
	
	public boolean isOk();
	
	/** Informa la cantidad de errores */
	
	public int getErrorQuantity();
	
	/** Retorna la lista de errores */
	
	public List<ErrorCode> getErrorCodes();
	
}
