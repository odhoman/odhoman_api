package com.odhoman.api.utilities.audit;

import java.io.Serializable;

/**
 * 
 * Estado de ejecucion del proceso de estadistica de items de auditoria
 * 
 * @author Fabian Benitez (fb70883)
 * @version 2/07/2014
 * 
 */

public class StatisticCollectorStatus implements Serializable {

	private static final long serialVersionUID = -850874887309904353L;
	
	private Long processKey;
	private Long errorCode;
	private String errorMessage;
	private Long recordCount;
	
	public StatisticCollectorStatus() {
	}

	public Long getProcessKey() {
		return processKey;
	}

	public void setProcessKey(Long processKey) {
		this.processKey = processKey;
	}

	public Long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Long errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Long getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}

	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer("StatisticCollectorStatus:");
		sb.append(" processKey=");
		sb.append(getProcessKey());
		sb.append(" recordCount=");
		sb.append(getRecordCount());
		sb.append(" errorCode=");
		sb.append(getErrorCode());			
		sb.append(" errorMessage=");
		sb.append(getErrorMessage());		
		
	    return sb.toString();
	}
	
}
