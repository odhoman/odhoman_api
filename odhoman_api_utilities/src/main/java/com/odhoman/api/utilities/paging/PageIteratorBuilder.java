package com.odhoman.api.utilities.paging;

import com.odhoman.api.utilities.config.AbstractConfig;
import com.odhoman.api.utilities.dependencyinjector.Initializable;
import com.odhoman.api.utilities.transac.ApplicationErrorException;

/**
 * Builder de Page iterators.
 * 
 * Requiere que se le definan los executors a utilizar.
 * 
 * @author Fabian Benitez (FB70883)
 * @version MAY-2015
 *
 */

public class PageIteratorBuilder<T, K> implements Initializable {

	private AbstractConfig config;
	
	private ExecutorImpl<T, K> simpleExecutor;
	private ExecutorImpl<T, K> reportExecutor;
	
	public PageIteratorBuilder(AbstractConfig config) {
		this.config = config;
	}

	@SuppressWarnings("unchecked")
	public void init(Object... params) {
		
		if(params == null || params.length < 1)
			throw new ApplicationErrorException("No se pudo inicializar el PageIteratorBuilder");
		
		//Al menos hay un parametro...
		
		if(params[0] == null || !(params[0] instanceof ExecutorImpl<?, ?>))
			throw new ApplicationErrorException("Se esperaba un ExecutorImpl como primer parametro");
		
		simpleExecutor = (ExecutorImpl<T, K>) params[0];
		
		if(params.length > 1) {
			
			if(params[1] == null || !(params[1] instanceof ExecutorImpl<?, ?>))
				throw new ApplicationErrorException("Se esperaba un ExecutorImpl como segundo parametro");
			else
				reportExecutor = (ExecutorImpl<T, K>) params[1];					
		}
		
		config.getLogger().debug("PageIteratorBuilder inicializado");
		
		
	}
	
	private ExecutorImpl<T, K> getSimpleExecutor() {
		
		if(simpleExecutor == null)
			throw new ApplicationErrorException("No hay un Executor definido");
		
		return simpleExecutor;
	}
	
	private ExecutorImpl<T, K> getReportExecutor() {
		
		if(reportExecutor == null)
			throw new ApplicationErrorException("No hay un Executor de reporting definido");
		
		return reportExecutor;
	}
	
	/** Genera y configura el pageIterator de items simples */
	
	public PageIterator<T, K> getIterator(K filter, PageInfo pageInfo, OrderInfo orderInfo) {
		
		return new PageIterator<T, K>(filter, pageInfo, orderInfo, getSimpleExecutor());
	}

	/** Genera y configura el pageIterator de item simples */
	
	public PageIterator<T, K> getIterator(K filter, OrderInfo orderInfo) {
		
		Integer itemsPerPage = getSimpleExecutor().getItemsPerPage();
		
		return getIterator(filter, getPageInfo(itemsPerPage), orderInfo);
	}

	/** Genera y configura el pageIterator de reporting */
	
	public PageIterator<T, K> getReportIterator(K filter, PageInfo pageInfo, OrderInfo orderInfo) {
		
		return new PageIterator<T, K>(filter, pageInfo, orderInfo, getReportExecutor());
	}
	
	/** Genera y configura el pageIterator de reporting */
	
	public PageIterator<T, K> getReportIterator(K filter, OrderInfo orderInfo) {
	
		Integer itemsPerPage = getReportExecutor().getItemsPerPage();
		
		return getReportIterator(filter, getPageInfo(itemsPerPage), orderInfo);
	}	
	
	private PageInfo getPageInfo(Integer itemsPerPage) {
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setFirstPage();
		pageInfo.setItemsPerPage(itemsPerPage);
		
		return pageInfo;
		
	}

	
}
