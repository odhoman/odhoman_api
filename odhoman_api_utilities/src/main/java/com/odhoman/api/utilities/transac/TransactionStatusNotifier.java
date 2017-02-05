package com.odhoman.api.utilities.transac;

/**
 * @author Fabian Benitez (fb70883)
 * @date 08/03/2010
 * 
 * Sera el responsable de informar el estado de una transaccion
 */

public interface TransactionStatusNotifier {

	/** Retorna el estado de la transaccion */
	
	public TransactionStatus getTransactionStatus();
	
}
