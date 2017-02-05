package com.odhoman.api.utilities.paging;

import java.io.Serializable;

import com.odhoman.api.utilities.config.ConfigConstants;

/**
 * 
 * Representa la informacion de paginado
 * 
 * @author Nicolas Calvo(nc90244)
 *
 */
public class PageInfo implements Serializable {
	
	private static final long serialVersionUID = 4526892713972624993L;

	private int page;
	private int itemsPerPage;
	private long totalItems = 0L;
	
	public PageInfo() {
		this(ConfigConstants.DEFAULT_PAGE, ConfigConstants.DEFAULT_ITEMS_PER_PAGE);
	}
	
	public PageInfo(int page, int itemsPerPage) {
		setPage(page);
		setItemsPerPage(itemsPerPage);		
	}

	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		if(page <= 0)
			throw new PageIteratorException("El numero debe ser mayor a 0");
		
		this.page = page;
	}
	
	public void setFirstPage() {
		this.page = 1;
	}
	
	public void setNextPage() {
		if(page >= Integer.MAX_VALUE)
			throw new PageIteratorException("Numero maximo de pagina");	
		
		this.page++;
	}
	
	public void setPreviousPage() {
		if(page <= 1)
			throw new PageIteratorException("Numero minimo de pagina");
		
		this.page--;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}
	
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}
	
	public long getTotalItems() {
		return totalItems;
	}
	
	public void setTotalItems(long totalItems) {
		if(totalItems < 0)
			throw new PageIteratorException("Numero total de paginas invalido");
		
		this.totalItems = totalItems;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("PageInfo:");
		sb.append(" page=");
		sb.append(getPage());
		sb.append(" itemsPerPage=");
		sb.append(getItemsPerPage());
		sb.append(" totalItems=");
		sb.append(getTotalItems());
		return sb.toString();
	}
	
}
