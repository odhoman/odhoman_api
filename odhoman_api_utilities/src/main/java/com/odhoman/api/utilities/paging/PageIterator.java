package com.odhoman.api.utilities.paging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Iterador de paginas generico
 * 
 * @author Nicolas Calvo(nc90244) / Fabian Benitez (fb70883)
 *	
 */

public class PageIterator<T, K> implements Serializable {

	private static final long serialVersionUID = 2946037745564624104L;
	
	private K filter;
	private PageInfo pageInfo;
	private OrderInfo orderInfo;	
	private Executor<T, K> executor;
		
	/** 
	 * Items de la pagina. Contendra como maximo la cantidad de items por pagina 
	 * definido en pageInfo
	 */
	private List<T> items = new ArrayList<T>(); 
	
	/** Total de items de todas las paginas */
	
	private long totalItems = 0;

	public PageIterator(K filter, PageInfo pageInfo, OrderInfo orderInfo) {
		setFilter(filter);
		setPageInfo(pageInfo);
		setOrderInfo(orderInfo);		
	}

	public PageIterator(K filter, PageInfo pageInfo, OrderInfo orderInfo, Executor<T, K> executor) {
		this(filter, pageInfo, orderInfo);
		this.executor = executor;
	}

	public void cleanItems(){
		items.clear();
	}
	
	/** Retorna los items de la pagina actual. Si no hay elementos, intentara obtenerlos */
	
	public List<T> getItems() {
		if(items.isEmpty())
			loadPage();
		
		return items;
	}
	
	protected void setItems(List<T> items) {
		this.items = items;
	}
	
	public long getTotalItems() {
		return totalItems;
	}
	
	protected void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}
	
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	
	public void setPageInfo(PageInfo pageInfo) {
		if(pageInfo != null)
			this.pageInfo = pageInfo;
	}
	
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}
	
	public void setOrderInfo(OrderInfo orderInfo) {
		if(orderInfo != null)
			this.orderInfo = orderInfo;
	}
	
	public K getFilter() {
		return filter;
	}
	
	public void setFilter(K filter) {
		if(filter != null)
			this.filter = filter;
	}
	
	/** Retorna el executor configurado */
	
	protected Executor<T, K> getExecutor() {
		if(executor == null)
			throw new PageIteratorException("No hay un executor definido");
		
		return executor;
	}
	
	/** Configura un executor puntual */
	
	public void setExecutor(Executor<T, K> executor) {
		if(executor == null)
			throw new PageIteratorException("Executor invalido");
		
		this.executor = executor;
	}
	
	/** Obtiene la pagina deseada a traves del executor */
	
	protected void loadPage() {
		Executor<T, K> executor = getExecutor();		
		
		//Se obtienen los items a traves del executor
		List<T> items; 
		try {
			pageInfo.setTotalItems(0);	//Se reinicia el total items
			items = executor.getItems(filter, pageInfo, orderInfo);
		} catch (ExecutorException e) {
			throw new PageIteratorException("No se pudieron obtener los items", e);
		}	
		
		if(items.size() > pageInfo.getItemsPerPage())
			throw new PageIteratorException("Cantidad de elementos por pagina superior a lo estipulado");
		
		setItems(items);
		
		//Se actualiza la cantidad total de items segun la ultima consulta
		try {
			setTotalItems(executor.getTotalItems());
		} catch (ExecutorException e) {
			throw new PageIteratorException("No se pudo obtener la cantidad total de items", e);
		}
	}
	
	/** Evalua si existe una siguiente pagina */
	
	public boolean hasNext() {
		return (totalItems > pageInfo.getPage() * pageInfo.getItemsPerPage()); 
	}

	/** Obtiene la siguiente pagina */
	
	public List<T> next() {
		
		if(!hasNext())
			throw new PageIteratorException("No hay paginas posteriores");

		pageInfo.setNextPage();
		loadPage();
		return getItems();
	}
	
	/** Evalua si existe una pagina previa */
	
	public boolean hasPrevious() {
		return totalItems > 0 & pageInfo.getPage() > 1;
	}
	
	/** Obtiene la pagina previa */
	
	public List<T> previous() {
		
		if(!hasPrevious())
			throw new PageIteratorException("No hay mas paginas previas");

		pageInfo.setPreviousPage();
		loadPage();
		return getItems();
	}

	/** Obtiene una pagina determinada */
	
	public List<T> setPage(int page) {
		
		if(page < 1 || page > getTotalPages())
			throw new PageIteratorException("La pagina solicitada no es valida");

		pageInfo.setPage(page);
		loadPage();
		return getItems();
	}
		
	
	/** Obtiene la primer pagina */
	
	public List<T> first() {
		pageInfo.setPage(1);
		loadPage();
		return getItems();
	}
	
	/** Obtiene la ultima pagina */
	
	public List<T> last() {
		pageInfo.setPage(getTotalPages());			
		loadPage();
		return getItems();
	}

	/** Obtiene el nro de pagina actual */
	
	public int getCurrentPage() {
		return pageInfo.getPage();
	}
	
	/** Obtiene el nro total de paginas */
	
	public int getTotalPages() {
		
		if(totalItems <= 0)
			throw new PageIteratorException("Cantidad total de paginas desconocida");
		
		int pages = (int)(totalItems / pageInfo.getItemsPerPage());

		if (totalItems % pageInfo.getItemsPerPage() != 0)
			pages++;
		
		return pages;
	}
	
}
