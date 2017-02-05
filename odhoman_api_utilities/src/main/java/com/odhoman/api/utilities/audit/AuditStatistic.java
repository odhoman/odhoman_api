package com.odhoman.api.utilities.audit;

import java.io.Serializable;
import java.util.Date;

import com.odhoman.api.utilities.CmIOFormat;

/**
 * 
 * Registro de estadistica de items de auditoria
 * 
 * @author Fabian Benitez (fb70883)
 * @version 23/06/2014
 * 
 */

public class AuditStatistic implements Serializable {

	private static final long serialVersionUID = 7103702479176859656L;
	
	private Date fecha;
	private String usuario;
	private String modulo;		//clase / modulo / grupo funcional
	private String funcion;		//funcion / metodo / operacion
	
	private Long tiempoEjecucionPromedio;
	private Long tiempoEjecucionMinimo;
	private Long tiempoEjecucionMaximo;
	
	private Long totalConError;
	private Long totalSinError;
	
	public AuditStatistic() {
		
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public Long getTiempoEjecucionPromedio() {
		return tiempoEjecucionPromedio;
	}

	public void setTiempoEjecucionPromedio(Long tiempoEjecucionPromedio) {
		this.tiempoEjecucionPromedio = tiempoEjecucionPromedio;
	}

	public Long getTiempoEjecucionMinimo() {
		return tiempoEjecucionMinimo;
	}

	public void setTiempoEjecucionMinimo(Long tiempoEjecucionMinimo) {
		this.tiempoEjecucionMinimo = tiempoEjecucionMinimo;
	}

	public Long getTiempoEjecucionMaximo() {
		return tiempoEjecucionMaximo;
	}

	public void setTiempoEjecucionMaximo(Long tiempoEjecucionMaximo) {
		this.tiempoEjecucionMaximo = tiempoEjecucionMaximo;
	}

	public Long getTotalConError() {
		return totalConError;
	}

	public void setTotalConError(Long totalConError) {
		this.totalConError = totalConError;
	}

	public Long getTotalSinError() {
		return totalSinError;
	}

	public void setTotalSinError(Long totalSinError) {
		this.totalSinError = totalSinError;
	}

	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer("AuditStatict:");
		sb.append(" fecha=");
		sb.append(CmIOFormat.getFormattedDate(getFecha(), "dd/MM/yyyy"));	
		sb.append(" usuario=");
		sb.append(getUsuario());			
		sb.append(" modulo=");
		sb.append(getModulo());
	    sb.append(" funcion=");
	    sb.append(getFuncion());
	    sb.append(" tiempoEjecucionPromedio=");
	    sb.append(getTiempoEjecucionPromedio());
	    sb.append(" tiempoEjecucionMaximo=");
	    sb.append(getTiempoEjecucionMaximo());
	    sb.append(" tiempoEjecucionMinimo=");
	    sb.append(getTiempoEjecucionMinimo());
	    sb.append(" totalSinError=");
	    sb.append(getTotalSinError());
	    sb.append(" totalConError=");
	    sb.append(getTotalConError());
	    	    
	    return sb.toString();
	}
	
}
