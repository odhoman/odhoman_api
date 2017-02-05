package com.odhoman.api.utilities.paging;

public enum EnumOrderInfo {
	
	ORDER_TYPE_ASC("asc"),
	ORDER_TYPE_DESC("desc");
	
	private final String descripcion;

	private EnumOrderInfo(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
