package com.odhoman.api.utilities.audit;

import java.util.List;

import com.odhoman.api.utilities.dao.DAOException;

/**
 * 
 * DAO para auditoria
 * 
 * @author Fabian Benitez (fb70883)
 * @version 14/05/2012
 * 
 */

public interface AuditDAO<P extends AuditRecord, Q extends AuditRecordFilter> {

	/** Inserta un registro de auditoria */
	
	public void insertAuditRecord(P auditRecord) throws DAOException;
	
	/** Inserta una lista de registros de auditoria */
	
	public int insertAuditRecords(List<P> auditRecord) throws DAOException;
	
	/** Elimina los registros de auditoria que esten dentro de las fechas especificadas */
	
	public void deleteAuditRecords(AuditRecordFilter filter) throws DAOException;
	
	/** Devuelve los registros de auditoria que cumplan con las condiciones especificadas */
	
	public List<P> getAuditRecords(Q auditRecordFilter, Long page, Long itemsPerPage) throws DAOException;
	
	/** Indica la cantidad de items que tiene la consulta en funcion del filtro especificado */
	
	public Long getAuditRecordCount(Q auditRecordFilter) throws DAOException;
	
	/** 
	 *  Invoca al proceso de recoleccion de estadisticas de los logs de auditoria
	 *  
	 *   Recibe las fechas desde y hasta a procesar. Toma todos los registros de auditoria en este rango de fechas
	 *   y totaliza cantidades, errores y tiempos por dia, usuario y funcion. Luego graba estas filas
	 *   en la tabla de estadisticas.
	 *   
	 *   Retorna el estado del proceso ejecutado.
	 *   
	 */
	
	public StatisticCollectorStatus callServiceStatisticCollector(Long dateFrom, Long dateTo) throws DAOException;
	
}
