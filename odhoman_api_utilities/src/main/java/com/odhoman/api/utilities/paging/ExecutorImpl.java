package com.odhoman.api.utilities.paging;

import java.util.List;

import org.apache.log4j.Logger;

import com.odhoman.api.utilities.config.AbstractConfig;


/**
 * Implementacion abstracta del Executor para resolver la busqueda de items.
 * 
 * Basicamente permite traducir la obtencion de los items a una interfaz simple y unica.
 * 
 * Ademas requiere la definicion de los items por pagina a utilizar.
 * 
 * @author Fabian Benitez (FB70883)
 * @version MAY-2015
 *
 */

public abstract class ExecutorImpl<T, K> implements Executor<T, K> {

	private static final long serialVersionUID = -7067601120725053713L;

	protected AbstractConfig config = null;
	protected Logger logger = null;
	
	protected long totalItems = 0;
	
	public ExecutorImpl(AbstractConfig config) {
		this.config = config;
		this.logger = config.getLogger();
	}

	public List<T> getItems(K filter, PageInfo pageInfo, OrderInfo orderInfo) throws ExecutorException {
		
		setTotalItems(0);	//Se inicializa la cantidad de items totales
		
		try {
						
			List<T> items = getResults(filter, pageInfo, orderInfo);
			
			logger.debug("ExecutorImpl - Se obtuvieron " + items.size() + " items");
			
			if (items.isEmpty())
				throw new NoMoreItemsException("No se obtuvieron items con el filtro ingresado.");
			
			setTotalItems(pageInfo.getTotalItems());
			
			return items;
		
		} catch (NoMoreItemsException e) {
			throw e;
		} catch (Exception e) {
			logger.error("ExecutorImpl: No se pudo obtener la lista de items", e);
			throw new ExecutorException("No se pudo obtener la lista de items", e);
		}
	}

	private void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}
	
	public long getTotalItems() throws ExecutorException {
		
		if(totalItems <= 0)
			throw new ExecutorException("Total de items aun no determinado");
		
		return totalItems;
	}
	
	/** Obtiene los items segun corresponda */
	
	protected abstract List<T> getResults(K filter, PageInfo pageInfo, OrderInfo orderInfo) throws Exception;
	
	/** Obtiene los items por pagina */
	
	public abstract int getItemsPerPage();


}
