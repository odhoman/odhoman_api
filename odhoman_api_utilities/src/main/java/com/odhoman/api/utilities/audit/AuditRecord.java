package com.odhoman.api.utilities.audit;

import java.io.Serializable;
import java.util.Date;

import com.odhoman.api.utilities.CmIOFormat;
import com.odhoman.api.utilities.ExceptionUtil;

/**
 * 
 * Registro de auditoria para modulos y funciones y su contexto
 * 
 * @author Fabian Benitez (fb70883)
 * @version 14/05/2012
 * 
 */

public class AuditRecord implements Serializable {

	private static final long serialVersionUID = 7215630088544432108L;
	
	private Date fechaHora;
	private String usuario;
	private String modulo;		//clase / modulo / grupo funcional
	private String funcion;		//funcion / metodo / operacion
	private String argumentos;
	private Long tiempoEjecucion;
	private boolean conError;
	private String error; 
	
	public AuditRecord() {
		
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public String getArgumentos() {
		return argumentos;
	}

	public void setArgumentos(String argumentos) {
		this.argumentos = argumentos;
	}

	public Long getTiempoEjecucion() {
		return tiempoEjecucion;
	}

	public void setTiempoEjecucion(Long tiempoEjecucion) {
		this.tiempoEjecucion = tiempoEjecucion;
	}

	public boolean isConError() {
		return conError;
	}

	public void setConError(boolean conError) {
		this.conError = conError;
	}

	public String getError() {
		return error;
	}

	public void setError(Throwable error) {
		if(error != null)
			this.error = ExceptionUtil.getShortStackTrace(error);
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer("AuditRecord:");
		sb.append(" dateTime=");
		sb.append(CmIOFormat.getFormattedDate(getFechaHora(), "dd/MM/yyyy HH:mm:ss"));	
		sb.append(" user=");
		sb.append(getUsuario());			
		sb.append(" module=");
		sb.append(getModulo());
	    sb.append(" function=");
	    sb.append(getFuncion());
	    sb.append(" args=(");
	    sb.append(getArgumentos());		    
	    sb.append(") executionTime=");
	    sb.append(getTiempoEjecucion());
	    sb.append(" withError=");
	    sb.append(isConError());
	    sb.append(" exception=");
	    sb.append(getError());
	    
	    return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(null == obj || !(obj instanceof AuditRecord))
			return false;
		
		AuditRecord e = (AuditRecord) obj;
		
		return e.getModulo().equals(getModulo())
				&& e.getUsuario().equals(getUsuario())
				&& e.getFuncion().equals(getFuncion()) 
				&& e.getFechaHora().equals(getFechaHora());
		
	}
	
}
