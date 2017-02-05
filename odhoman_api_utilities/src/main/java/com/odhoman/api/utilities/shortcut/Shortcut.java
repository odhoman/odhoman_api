package com.odhoman.api.utilities.shortcut;

/**
 * @author Fabian Benitez (fb70883)
 * @version 29/04/2014
 * 
 * Permite la definicion de shotcuts para la ejecucion de ciertas acciones.
 * 
 */

public class Shortcut {

	private String id;
	private String key;
	private String action;
	private String description;
	
	public Shortcut(String id, String key, String action, String description) {
		setId(id);
		setKey(key);
		setAction(action);
	}

	public String getId() {
		return id;
	}

	private void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	private void setKey(String key) {
		this.key = key;
	}

	public String getAction() {
		return action;
	}

	private void setAction(String action) {
		this.action = action;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof Shortcut))
			return false;
		
		Shortcut sc = (Shortcut) obj;
		
		return getId().equals(sc.getId()) 
					&& getKey().equals(sc.getKey())
					&& getAction().equals(sc.getAction())
					&& getDescription().equals(sc.getDescription());
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("Shortcut: id=");
		sb.append(getId());
		sb.append(" key=");
		sb.append(getKey());
		sb.append(" action=");
		sb.append(getAction());
		sb.append(" description=");
		sb.append(getDescription());
		return sb.toString();
	}

}
