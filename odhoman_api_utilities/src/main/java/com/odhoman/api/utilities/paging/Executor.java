package com.odhoman.api.utilities.paging;

import java.io.Serializable;
import java.util.List;

/**
 * Executor para resolver la busqueda de items 
 * 
 * @author Nicolas Calvo(nc90244)
 *
 */

public interface Executor<T, K> extends Serializable {

	/** 
	 * Obtiene una lista de objetos de tipo T en funcion de un filtro de tipo K
	 * y los datos puntuales del paginado deseado
	 */
	
	public List<T> getItems(K filter, PageInfo pageInfo, OrderInfo orderInfo) throws ExecutorException;
	
	/** 
	 * Retorna el numero total de items segun la ultima busqueda 
	 * 
	 * @throws ExecutorException 
	 * 		
	 * 		Cuando se intentan obtener los items totales y aun no se ejecuto el getItems
	 *  
	 */
	
	public long getTotalItems() throws ExecutorException;
	
}
