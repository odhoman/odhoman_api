package com.odhoman.api.utilities.paging;

import java.io.Serializable;

/**
 * 
 * Representa el ordenamiento deseado para una busqueda determinada
 * 
 * @author Nicolas Calvo(nc90244)
 *
 */
public class OrderInfo implements Serializable {

	private static final long serialVersionUID = 7562667695018914811L;
	
	private String orderField;
	private String orderType;
	
	public OrderInfo() {
		
	}
	
	public OrderInfo(String orderField, String orderType) {
		setOrderField(orderField);
		setOrderType(orderType);
	}
	
	public String getOrderField() {
		return orderField;
	}
	
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	
	public String getOrderType() {
		return orderType;
	}
	
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("OrderInfo:");
		sb.append(" orderField=");
		sb.append(getOrderField());
		sb.append(" orderType=");
		sb.append(getOrderType());
		return sb.toString();
	}
	
	
}
