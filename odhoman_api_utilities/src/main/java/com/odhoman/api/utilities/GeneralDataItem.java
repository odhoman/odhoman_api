/**
 * GeneralDataItem.java
 *
 * @author tecso:
 * @version
 */

package com.odhoman.api.utilities;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import com.odhoman.api.utilities.transac.ApplicationErrorException;

public class GeneralDataItem implements Serializable, Comparable<GeneralDataItem> {

	private static final long serialVersionUID = 48316458422777399L;

	private Long entityKey = new Long(0);

	private Long parentEntityKey = new Long(0);

	private String code = "";

	private String parentCode = "";

	private String description = "";

	private Collection<?> childs = null;

	public GeneralDataItem() {
		super();
	}

	public GeneralDataItem(Long entityKey, String code, String description) {
		this.entityKey = entityKey;
		this.code = code.trim();
		this.description = description;
	}

	public GeneralDataItem(Long entityKey, String code, String description, Long parentEntityKey) {
		this.entityKey = entityKey;
		this.code = code.trim();
		this.description = description;
		this.parentEntityKey = parentEntityKey;
	}

	public Long getEntityKey() {
		return this.entityKey;
	}

	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

	public Long getParentEntityKey() {
		return this.parentEntityKey;
	}

	public void setParentEntityKey(Long parentEntityKey) {
		this.parentEntityKey = parentEntityKey;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code.trim();
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode.trim();
	}

	public Collection<?> getChilds() {
		return this.childs;
	}

	public void setChilds(LinkedList<?> childs) {
		this.childs = childs;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("GeneralDataItem: code=");
		sb.append(getCode());
		sb.append(" parentCode=");
		sb.append(getParentCode());
		sb.append(" description=");
		sb.append(getDescription());
		
		return sb.toString();
	}

	// Compatibilidad con versiones anteriores
	
	public GeneralDataItem(String code, String description) {
		this.code = code.trim();
		this.description = description;
	}

	public GeneralDataItem(String code, String description, String parentCode) {
		this.code = code.trim();
		this.description = description;
		this.parentCode = parentCode.trim();
	}

	public GeneralDataItem(String code, String description, String parentCode, LinkedList<?> childs) {
		this.code = code.trim();
		this.description = description;
		this.parentCode = parentCode.trim();
		this.childs = childs;
	}	

	
	
	/**
	 * @see Comparable#compareTo(Object)
	 */
	public int compareTo(GeneralDataItem obj) {
		
		if(obj == null ||!(obj instanceof GeneralDataItem) ) {
			throw new ApplicationErrorException("No valid object to be compared");
		}

		return obj.getCode().compareTo(this.getCode());
		
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj == null || !(obj instanceof GeneralDataItem)) {
			return false;
		}
		
		GeneralDataItem gdi = (GeneralDataItem) obj;
		
		if(gdi.getCode() == null || gdi.getDescription() == null) {
			return false;
		}
		
		if(gdi.getCode().equals(this.getCode()) &&
			gdi.getDescription().equals(this.getDescription())) {
			return true;
		}
		
		return false;
	}
	
	

}
