package com.odhoman.api.utilities.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.odhoman.api.utilities.config.AbstractConfig;
import com.odhoman.api.utilities.transac.ApplicationErrorException;

public abstract class AbstractCacheManager<T, F> {
	
	protected Map<String, T> items = null;
	protected Long cacheHits = 0L;
	
	protected Logger logger = null;
	
	protected AbstractCacheManager(AbstractConfig config) {
		items = new HashMap<String, T>();
		cacheHits = 0L;
		
		logger = config.getLogger();
	}
	
	/** Realiza la carga de la cache en funcion de un mapa de items */
	
	public void load(Map<String, T> map) {
		if(map != null)
			items = map;
	}
	
	/** Realiza la carga de la cache en funcion de una lista de items */
	
	public void load(List<T> list) {
	
		logger.debug("AbstractCacheManager - load: Iniciando la carga de items");
		
		for(T item : list) {
			add(item);
		}
		
		logger.debug("AbstractCacheManager - load: Finalizando - se cargaron " + list.size() + " items");
	}
	
	/** Agrega el item especificado a la cache */
	
	public void add(T item) {

		String key = geKeyForMap(item);
		
		items.put(key, item);
		
		logger.debug("AbstractCacheManager - add: item '" + key + "' agregado");
	}
	
	/** Determina el campo que se usara para la key del mapa de la cache */
	
	protected abstract String geKeyForMap(T item);
	
	/** Evalua si la key esta en la cache */
	
	public boolean contains(String key) {
		
		logger.debug("AbstractCacheManager - contains: Verificando existencia del item con key " + key);
		
		return items.containsKey(key);
	}
	
	/** Obtiene el item con la key especificada de la cache. */
	
	public T getItem(String key) {
		
		return getItem(key, false);
		
	}
	
	/** Obtiene el item con la key especificada de la cache. Valida si corresponde que existe segun el flag especificado */
	
	public T getItem(String key, boolean mustExist) {
		
		cacheHits++;		
		
		T item = items.get(key);
		
		if(mustExist && item == null) {
			throw new ApplicationErrorException("No se encontro en la cache el item con key '" + key + "'");
		}
			
		return item;
	}
	
	/** Obtiene todos los items de la cache */
	
	public List<T> getAll() {
		return sortItems(new ArrayList<T>(items.values()));
	}
	
	/** Devuelve los items filtrados por el filter */
	
	public List<T> getFilteredItems(F filter) {
		
		List<T> filteredItems = new ArrayList<T>();
		
		for(T item : getAll()) {
			if(match(item, filter))
				filteredItems.add(item);
		}
		
		return filteredItems;		
	}
	
	/** Determina si el item cumple los requisitos definidos por el filter */
	
	protected abstract boolean match(T item, F filter);
	
	/** Obtiene la cantidad de hits de la cache */
	
	public Long getCacheHits() {
		return cacheHits;
	}
	
	/**Ordena los items del cache*/
	
	protected abstract List<T> sortItems(List<T> items);
	
	/**Limpia el mapa de resultados del cache y se encarga de reiniciar el cacheHits*/
	
	public void clean() {
		items.clear();
		cacheHits = 0L;
	}

}
