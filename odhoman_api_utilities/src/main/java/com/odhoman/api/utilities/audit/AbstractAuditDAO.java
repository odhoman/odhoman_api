package com.odhoman.api.utilities.audit;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.odhoman.api.utilities.CmIOFormat;
import com.odhoman.api.utilities.config.AbstractConfig;
import com.odhoman.api.utilities.dao.DAOException;
import com.odhoman.api.utilities.db.DatabaseConnection;
import com.odhoman.api.utilities.transac.ApplicationErrorException;

/**
 * 
 * DAO abstracto para funciones auditoria
 * 
 * @author Fabian Benitez (fb70883)
 * @version 14/05/2012
 * 
 */

public abstract class AbstractAuditDAO implements AuditDAO<AuditRecord, AuditRecordFilter> {

	private Logger logger = null;
	
	public AbstractAuditDAO(AbstractConfig config) {
		setConfig(config);
	}
	
	public void setConfig(AbstractConfig config) {
		logger = config.getLogger();
	}

	public Long getAuditRecordCount(AuditRecordFilter auditRecordFilter) throws DAOException {
		DatabaseConnection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			checkPagingInfo(1L, 1L);			
			
			//Configuracion de la conexion
			db = getDatabaseConnection();
			db.connect();
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("select count(1) as totalItems from ");
			sb.append(getAuditTableName());
			sb.append(" where ");
			sb.append(getFilteringConditions(auditRecordFilter));
			sb.append(" order by ");
			sb.append(getOrderBy());
			
			String sql = sb.toString();
			logger.info("QUERY: " + sql);

			ps = db.prepare(sql);
			
			rs = ps.executeQuery();
			
			Long count = 0L;
			while (rs.next()) {
				count = rs.getLong("totalItems");				
			}	
			return count;
			
		} catch (DAOException e) {
			throw e;
		} catch (ApplicationErrorException e) {
			throw e;
		} catch (Exception e) {			
			logger.error("Error en AbstractAuditDAO - getAuditRecordCount", e);
			throw new DAOException("No se pudo obtener la cantidad de registros de auditoria", e);
		} finally {
			closeResources(db, ps, rs, "getAuditRecordCount");
		}
	}
	
	public List<AuditRecord> getAuditRecords(AuditRecordFilter auditRecordFilter, Long page, Long itemsPerPage) 
	throws DAOException {
		
		DatabaseConnection db = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			
			checkPagingInfo(page, itemsPerPage);			
			
			//Configuracion de la conexion
			db = getDatabaseConnection();
			db.connect();
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("select * from (select a.*, rownum rnum from (");
			
			sb.append("select * from ");
			sb.append(getAuditTableName());
			sb.append(" where ");
			sb.append(getFilteringConditions(auditRecordFilter));
			sb.append(" order by ");
			sb.append(getOrderBy());
			
			sb.append(") a ) where rnum between " + (((page-1)*itemsPerPage)+1)  + " and " + page*itemsPerPage );
			
			String sql = sb.toString();
			logger.info("QUERY: " + sql);

			ps = db.prepare(sql);
			
			rs = ps.executeQuery();
			
			List<AuditRecord> auditRecords = new ArrayList<AuditRecord>();
			AuditRecord ar = null;
			while (rs.next()) {
				ar = new AuditRecord();				
				ar.setFechaHora(new java.util.Date(rs.getTimestamp("fecha_hora").getTime()));
				ar.setUsuario(rs.getString("usuario"));
				ar.setModulo(rs.getString("modulo"));
				ar.setFuncion(rs.getString("funcion"));
				ar.setArgumentos(rs.getString("argumentos"));
				ar.setTiempoEjecucion(rs.getLong("tiempo_ejecucion"));
				ar.setConError("Y".equals(rs.getString("con_error")));
				ar.setError(rs.getString("error"));
				
				auditRecords.add(ar);
			}	
			return auditRecords;
			
		} catch (DAOException e) {
			throw e;
		} catch (ApplicationErrorException e) {
			throw e;
		} catch (Exception e) {			
			logger.error("Error en AbstractAuditDAO - getAuditRecords", e);
			throw new DAOException("No se pudieron obtener los registros de auditoria", e);
		} finally {
			closeResources(db, ps, rs, "getAuditRecords");
		}
	}

	/** Valida los datos de paginado */
	
	private void checkPagingInfo(Long page, Long itemsPerPage) {
		if(null == page || page <= 0)
			throw new ApplicationErrorException("El numero de pagina solicitado es invalido: " + page);
		
		if(null == itemsPerPage || itemsPerPage <= 0)
			throw new ApplicationErrorException("La cantidad de items por pagina solicitada es invalida: " + itemsPerPage);
		
	}
	
	/** Arma las condiciones de busqueda segun el filter recibido */
	
	private String getFilteringConditions(AuditRecordFilter filter) {
		StringBuffer sb = new StringBuffer();
		
		if(filter.getFechaDesde() == null || filter.getFechaHasta() == null)
			throw new ApplicationErrorException("Se debe especificar la fecha desde y la fecha hasta"); 
		
		sb.append("fecha_hora BETWEEN TO_TIMESTAMP ('");
		sb.append(CmIOFormat.getFormattedDate(filter.getFechaDesde(), "yyyy-MM-dd HH:mm:ss"));
		sb.append("', '" + getDateFormat() + "')");
		
		sb.append(" AND TO_TIMESTAMP ('");
		sb.append(CmIOFormat.getFormattedDate(filter.getFechaHasta(), "yyyy-MM-dd HH:mm:ss"));
		sb.append("', '" + getDateFormat() + "')");
		
		if(filter.getConError() != null && !AuditRecordFilter.SIN_DEFINIR.equals(filter.getConError())) {
			sb.append(" AND con_error = '");
			sb.append(filter.isConError() ? "Y" : "N");
			sb.append("'");
		}
		
		if(filter.getUsuario() != null && !"".equals(filter.getUsuario())) {
			sb.append(" AND LOWER(usuario) LIKE '%");
			sb.append(filter.getUsuario().toLowerCase());
			sb.append("%'");
		}
		
		if(filter.getModulo() != null && !"".equals(filter.getModulo())) {
			sb.append(" AND LOWER(modulo) LIKE '%");
			sb.append(filter.getModulo().toLowerCase());
			sb.append("%'");
		}
		
		if(filter.getFuncion() != null && !"".equals(filter.getFuncion())) {
			sb.append(" AND LOWER(funcion) LIKE '%");
			sb.append(filter.getFuncion().toLowerCase());
			sb.append("%'");
		}
		
		if(filter.getArgumentos() != null && !"".equals(filter.getArgumentos())) {
			sb.append(" AND LOWER(argumentos) LIKE '%");
			sb.append(filter.getArgumentos().toLowerCase());
			sb.append("%'");
		}	
		
		return sb.toString();
	}
	
	public void insertAuditRecord(AuditRecord auditRecord) throws DAOException {
		DatabaseConnection db = null;
		PreparedStatement ps = null;		
		try {
			//Configuracion de la conexion
			db = getDatabaseConnection();
			db.connect();
				
			ps = buildInsertStatement(db);
			
			fillInsertParameters(auditRecord, ps, db);
			
			int inserted = ps.executeUpdate();
			ps.close();
			
			if(inserted <= 0) 
				throw new DAOException("No se pudo grabar el registro de auditoria: " + auditRecord);
			
		} catch (Exception e) {			
			logger.error("Error en AbstractAuditDAO - insertAuditRecord", e);
			throw new DAOException("No se pudo grabar el registro de auditoria", e);
		} finally {			
			closeResources(db, ps, null, "insertAuditRecord");
		}		
	}
	
	
	public int insertAuditRecords(List<AuditRecord> items) throws DAOException {
		
		logger.debug("AbstractAuditDAO - insertAuditRecords: Ejecutando...");
		
		if(items == null || items.isEmpty()) {
			logger.debug("AbstractAuditDAO - addItems: Finalizando. No hay items que agregar");
		}
		
		DatabaseConnection db = null;
		PreparedStatement ps = null;		
		try {
			//Configuracion de la conexion
			db = getDatabaseConnection();
			db.connect();
			
			logger.debug("AbstractAuditDAO - insertAuditRecords: items=" + items);
			
			for(AuditRecord item : items) {
			
				//Solo con el primer item arma el prepared statement necesario. Luego simplemente hace el fill correspondiente al item.
				
				if(ps == null) {
					ps = buildInsertStatement(db);
				}
				
				fillInsertParameters(item, ps, db);	//Se completan los parametros del insert
				
				ps.addBatch();
			}
			
			int[] resultado = ps.executeBatch();
			
			logger.debug("AbstractAuditDAO - insertAuditRecords: Batch ejecutado");
			
			int resultadoTotal = getProcessedResultCount(resultado);
			
			logger.info(getClassName() + " - insertAuditRecords: Se insertaron " + resultadoTotal + " registros de auditoria");
			
			ps.clearBatch();
			
			return resultadoTotal;
			
		} catch (Exception e) {			
			logger.error("Error en AbstractAuditDAO - insertAuditRecords", e);
			throw new DAOException("No se pudo grabar el registro de auditoria", e);
		} finally {			
			closeResources(db, ps, null, "insertAuditRecords");
		}		
	}	
	
	private PreparedStatement buildInsertStatement(DatabaseConnection db) throws SQLException{
		
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append(getAuditTableName());
		sb.append("( fecha_hora, event_id, usuario, modulo, funcion, argumentos, tiempo_ejecucion, con_error, error )");
		sb.append(" values (?,?,?,?,?,?,?,?,?)");
		
		String sql = sb.toString();
		logger.info("insertAuditRecord - QUERY: " + sql);
				
		return db.prepare(sql);
		
	}
	
	private void fillInsertParameters(AuditRecord auditRecord, PreparedStatement ps, DatabaseConnection db) throws SQLException{
		
		ps.setTimestamp(1, new Timestamp(auditRecord.getFechaHora().getTime()));
		ps.setLong(2, getGeneratedId());
		ps.setString(3, auditRecord.getUsuario());
		ps.setString(4, auditRecord.getModulo());
		ps.setString(5, auditRecord.getFuncion());
		// En especial, para el alta de operaciones, el tama�o de los argumentos son mayores a 500.
		String argumentos = CmIOFormat.compactText(auditRecord.getArgumentos(), 500);
		ps.setString(6, argumentos);	
		ps.setLong(7, auditRecord.getTiempoEjecucion());
		ps.setString(8, auditRecord.isConError() ? "Y" : "N");
		String error = null;
		if (auditRecord.getError() != null)
			error = CmIOFormat.compactText(auditRecord.getError(), 500);
		ps.setString(9, auditRecord.isConError() ? error : null);
		
	}
	
	/** Retorna la cantidad de items procesados */
	
	private Integer getProcessedResultCount(int [] result) {
		
		if(result == null || result.length == 0)
			return 0;
		
		Integer total = 0;
		for(int i : result) {
			total += (i != Statement.SUCCESS_NO_INFO) ? i : 1; 
		}
		return total;
		
	}
	
	
	public void deleteAuditRecords(AuditRecordFilter filter) throws DAOException {
		DatabaseConnection db = null;
		PreparedStatement ps = null;		
		try {
			
			if(filter.getFechaDesde() == null || filter.getFechaHasta() == null)
				throw new ApplicationErrorException("Se debe especificar la fecha desde y la fecha hasta"); 
			
			//Configuracion de la conexion
			db = getDatabaseConnection();
			db.connect();
			
			StringBuffer sb = new StringBuffer();
			sb.append("delete from ");
			sb.append(getAuditTableName());
			sb.append(" where fecha_hora between ? and ? ");

			String sql = sb.toString();
			logger.info("deleteAuditRecords - QUERY: " + sql);
					
			ps = db.prepare(sql);
			ps.setTimestamp(1, new Timestamp(filter.getFechaDesde().getTime()));
			ps.setTimestamp(2, new Timestamp(filter.getFechaHasta().getTime()));
			
			int deleted = ps.executeUpdate();
			
			if(deleted <= 0) 
				throw new DAOException("No se pudieron eliminar los registros de auditoria del filtro " + filter);
			
		} catch (Exception e) {			
			logger.error("Error en AbstractAuditDAO - deleteAuditRecords", e);
			throw new DAOException("No se pudieron eliminar los registros de auditoria", e);
		} finally {			
			closeResources(db, ps, null, "deleteAuditRecords");
		}		
	}
	
	
	public StatisticCollectorStatus callServiceStatisticCollector(Long dateFrom, Long dateTo) throws DAOException {
		DatabaseConnection db = null;
		CallableStatement cs = null;		
		try {
			//Configuracion de la conexion
			db = getDatabaseConnection();
			db.connect();
			
			StringBuffer sb = new StringBuffer();
			sb.append("{call ");
			sb.append(getAuditStatisticsProcessName());
			sb.append("(?,?,?,?,?,?)}");
			
			String sql = sb.toString();
			logger.info("callServiceStatisticCollector - QUERY: " + sql);
					
			cs = db.prepareCall(sql);
			cs.setLong(1, dateFrom);
			cs.setLong(2, dateTo);
			cs.registerOutParameter(3, java.sql.Types.NUMERIC);
			cs.registerOutParameter(4, java.sql.Types.NUMERIC);
			cs.registerOutParameter(5, java.sql.Types.NUMERIC);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			
			cs.executeUpdate();
			
			StatisticCollectorStatus status = new StatisticCollectorStatus();
			status.setProcessKey(cs.getLong(3));
			status.setRecordCount(cs.getLong(4));
			status.setErrorCode(cs.getLong(5));
			status.setErrorMessage(cs.getString(6));
			
			logger.debug("AbstractAuditDAO - callServiceStatisticCollector: " + status);
			
			return status;
			
		} catch (Exception e) {			
			logger.error("Error en AbstractAuditDAO - callServiceStatisticCollector", e);
			throw new DAOException("No se pudo invocar al proceso de estadisticas", e);
		} finally {			
			closeResources(db, cs, null, "callServiceStatisticCollector");
		}		
	}

	/** Devuelve un id generado al azar*/
	
	private long getGeneratedId() {
		Long id = UUID.randomUUID().getMostSignificantBits();
		return id > 0 ? id : id*(-1); 
	}
	
	/** Cierra los recursos abiertos */
	
	private void closeResources(DatabaseConnection db, PreparedStatement ps, ResultSet rs, String function) {
		try {
			if(rs != null) rs.close();
		} catch (Exception e) {
			logger.error("Error en AbstractAuditDAO - " + function, e);
		}
		try {
			if(ps != null) ps.close();
		} catch (Exception e) {
			logger.error("Error en AbstractAuditDAO - " + function, e);
		}
		try {
			if(db != null) db.close();
		} catch (Exception e) {
			logger.error("Error en AbstractAuditDAO - " + function, e);
		}		
	}
	
	/** Obtiene el objeto databaseconnection necesario para las consultas */
	
	protected abstract DatabaseConnection getDatabaseConnection();
	
	/** Indica el nombre de la tabla de auditoria */
	
	protected abstract String getAuditTableName();
	
	/** Indica el nombre de la tabla de estadisticas de auditoria */
	
	protected abstract String getAuditStatisticsProcessName();
	
	/** Devuelve el campo por el que se realizara el ordenamiento de la salida */
	
	protected String getOrderBy() {
		return "fecha_hora DESC";
	}
	
	/** Devuelve el formato que se utilizara para las fechas */
	
	protected String getDateFormat() {
		return "YYYY-MM-DD HH24:MI:SS";
	}
	
	private String getClassName() {
		return getClass().getSimpleName();
	}
	
}
