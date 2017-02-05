package com.odhoman.api.utilities.shortcut;

import java.util.ArrayList;
import java.util.List;

import com.odhoman.api.utilities.transac.ApplicationErrorException;

/**
 *	@author Fabian Benitez (fb70883)
 *	@version 29/04/2014
 *
 * 	Permite la gestion de los shortcuts.
 * 
 */

public class ShortcutManager {

	private List<Shortcut> shortcuts;
	
	public ShortcutManager() {
		this.shortcuts = new ArrayList<Shortcut>();
	}
	
	/** Agrega un shortcut al manager siempre y cuando no exista ya ni por action o por key o por id */
	
	public void addShortcut(Shortcut shortcut) {
		
		contains(shortcut);
		
		shortcuts.add(shortcut);	
	}
	
	/** Verifica si el shortcut ya existe en la manager por action, key o id */
	
	public void contains(Shortcut shortcut) {
		
		if(shortcuts.isEmpty())
			return;
		
		if(shortcuts.contains(shortcut))
			throw new ApplicationErrorException("Ya existe un shortcut con id " + shortcut.getId());
		
		for(Shortcut sc: shortcuts) {
			
			if(sc.getAction().equals(shortcut.getAction()))
				throw new ApplicationErrorException("Ya existe un shortcut con action " + shortcut.getAction());
			
			if(sc.getKey().equals(shortcut.getKey()))
				throw new ApplicationErrorException("Ya existe un shortcut con key " + shortcut.getKey());
		}
	}
	
	public void removeShortcut(Shortcut shortcut) {
		
		if(shortcuts.contains(shortcut))
			shortcuts.remove(shortcut);
	}
	
	/** Obtiene el shortcut con el id especificado */
	
	public Shortcut getShortcutById(String id) {
		
		for(Shortcut sc: shortcuts) {
			
			if(sc.getId().equals(id))
				return sc;			
		}
		
		throw new ApplicationErrorException("No se encontro el shortcut con id " + id);
		
	}
	
	/** Obtiene el shortcut con el action especificado */
	
	public Shortcut getShortcutByAction(String action) {
		
		for(Shortcut sc: shortcuts) {
			
			if(sc.getAction().equals(action))
				return sc;			
		}
		
		throw new ApplicationErrorException("No se encontro el shortcut con action " + action);
		
	}
	
	/** Obtiene el shortcut con el key especificado */
	
	public Shortcut getShortcutByKey(String key) {
		
		for(Shortcut sc: shortcuts) {
			
			if(sc.getKey().equals(key))
				return sc;			
		}
		
		throw new ApplicationErrorException("No se encontro el shortcut con key " + key);
		
	}
	
	public List<Shortcut> getShortcuts() {
		return shortcuts;
	}

}
