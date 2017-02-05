package com.odhoman.api.utilities.audit;

import java.io.Serializable;
import java.util.Date;

import com.odhoman.api.utilities.transac.ApplicationErrorException;

/**
 * 
 * Filter para busquedas de registros de auditoria
 * 
 * @author Fabian Benitez (fb70883)
 * @version 14/05/2012
 * 
 */

public class AuditRecordFilter implements Serializable {

	private static final long serialVersionUID = 1838359483680261213L;
	
	private Date fechaDesde;
	private Date fechaHasta;
	private String usuario;
	private String modulo;
	private String funcion;
	private String argumentos;
	private String conError = SIN_DEFINIR;
	
	public static final String CON_ERROR = "S";
	public static final String SIN_ERROR = "N";
	public static final String SIN_DEFINIR = "";
	
	public AuditRecordFilter() {
		
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
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

	public boolean isConError() {
		return conError.equals(CON_ERROR);
	}

	public String getConError() {
		return conError;
	}
	
	public void setConError(String conError) {
		if(conError == null || (
				!conError.equals(CON_ERROR) && 
				!conError.equals(SIN_ERROR) && 
				!conError.equals(SIN_DEFINIR)
			)
		) {
			throw new ApplicationErrorException("Estado de error invalido: " + conError);
		}
		this.conError = conError;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("[ AuditRecordFilter:");

		sb.append(" fechaDesde=");
		sb.append(getFechaDesde());
		sb.append(" fechaHasta=");
		sb.append(getFechaHasta());		
		sb.append(" usuario=");
		sb.append(getUsuario());		
		sb.append(" modulo=");
		sb.append(getModulo());
		sb.append(" funcion=");
		sb.append(getFuncion());
		sb.append(" argumentos=");
		sb.append(getArgumentos());		
		sb.append(" conError=");
		sb.append(getConError());
		
		sb.append(" ]");
		
		return sb.toString();
	}

}
