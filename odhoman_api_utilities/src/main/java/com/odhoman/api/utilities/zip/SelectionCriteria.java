package com.odhoman.api.utilities.zip;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/** Define el criterio de seleccion de un file para poder procesarlo */

public class SelectionCriteria {
	
	private Date fechaHoraAltaDesde;
	private Date fechaHoraAltaHasta;
	
	private Date fechaHoraModificacionDesde;
	private Date fechaHoraModificacionHasta;
	
	private List<String> filenameKeywords = new ArrayList<String>();
	
	public SelectionCriteria() {
		
	}
	
	public SelectionCriteria(String criteria) {
		
		//TODO: Parsear el criterio especificado para identificar que se esta pidiendo.
		
	}

	public Date getFechaHoraAltaDesde() {
		return fechaHoraAltaDesde;
	}

	public void setFechaHoraAltaDesde(Date fechaHoraAltaDesde) {
		this.fechaHoraAltaDesde = fechaHoraAltaDesde;
	}

	public Date getFechaHoraAltaHasta() {
		return fechaHoraAltaHasta;
	}

	public void setFechaHoraAltaHasta(Date fechaHoraAltaHasta) {
		this.fechaHoraAltaHasta = fechaHoraAltaHasta;
	}

	public Date getFechaHoraModificacionDesde() {
		return fechaHoraModificacionDesde;
	}

	public void setFechaHoraModificacionDesde(Date fechaHoraModificacionDesde) {
		this.fechaHoraModificacionDesde = fechaHoraModificacionDesde;
	}

	public Date getFechaHoraModificacionHasta() {
		return fechaHoraModificacionHasta;
	}

	public void setFechaHoraModificacionHasta(Date fechaHoraModificacionHasta) {
		this.fechaHoraModificacionHasta = fechaHoraModificacionHasta;
	}

	public List<String> getFilenameKeywords() {
		return filenameKeywords;
	}
	
	public void addFilenameKeyword(String filenameKeyword) {
		getFilenameKeywords().add(filenameKeyword);
	}

	public void setFilenameKeywords(List<String> filenameKeywords) {
		this.filenameKeywords = filenameKeywords;
	}
	
	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer("SelectionCriteria:");
		
		sb.append(" fechaHoraAltaDesde=");
		sb.append(getFechaHoraAltaDesde());			
		sb.append(" fechaHoraAltaHasta=");
		sb.append(getFechaHoraAltaHasta());
		sb.append(" fechaHoraModificacionDesde=");
		sb.append(getFechaHoraModificacionDesde());	
		sb.append(" fechaHoraModificacionHasta=");
		sb.append(getFechaHoraModificacionHasta());
		sb.append(" filenameKeywords=");
		sb.append(getFilenameKeywords());
		
		return sb.toString();
	}
	
}
