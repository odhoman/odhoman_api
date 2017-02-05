package com.odhoman.api.utilities.security.filter;

public interface InputFilter {

	/** Aplica el filtro al input especificado */
	
	public <T>T apply(T input);	

}
