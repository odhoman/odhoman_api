package com.odhoman.api.utilities.audit;

import java.util.List;

import org.apache.log4j.Logger;

import com.odhoman.api.utilities.config.AbstractConfig;
import com.odhoman.api.utilities.dao.DAOException;

/**
 * 
 * Inserta un conjunto de registros de auditoria de manera asincronica.
 * 
 * @author Jonatan Flores (jf16203)
 * @version AGO-2016
 */
public class AsynchronousAuditRecordPersistence implements Runnable {

	private List<AuditRecord> auditRecords;
	private AuditDAO<AuditRecord, AuditRecordFilter> dao;
	private Logger logger;
	int count;

	public AsynchronousAuditRecordPersistence(List<AuditRecord> auditRecords,
											  AuditDAO<AuditRecord, AuditRecordFilter> dao, 
											  AbstractConfig config) {
		this.auditRecords = auditRecords;
		this.dao = dao;
		this.logger = config.getLogger();
	}

	public void run(){
		
		try {
			
			if (auditRecords == null || auditRecords.size() == 0) {
				logger.debug("AsynchronousAuditRecordPersistence - run: No hay registros de auditoria en la lista para insertar");
				return;
			}
			
			logger.debug("AsynchronousAuditRecordPersistence - run: Se intentan insertar "+auditRecords.size() +" registros de auditoria");
			count = dao.insertAuditRecords(auditRecords);
			logger.debug("AsynchronousAuditRecordPersistence - run: Se insertaron "+ count +" registros de auditoria");
			
		} catch (DAOException e) {
			logger.error("AsynchronousAuditRecordPersistence - run: no se pudieron insertar los registros de auditoria por lote",e);
			logger.error("AsynchronousAuditRecordPersistence - run: Se reintenta guardar registros de auditoria");
			saveRemainingAuditRecords();
			
		}
	}
	
	private void saveRemainingAuditRecords(){
		
		if(auditRecords != null && auditRecords.size()>0){
			try {
				dao.insertAuditRecords(auditRecords);
			} catch (DAOException e) {
				logger.error("AsynchronousAuditRecordPersistence - saveRemainingAuditRecords: no se pudieron persistir los AuditRecords",e);
				logger.error("Los Registros de auditoria que no se pudieron insertar fueron los siguientes: ");
				for (AuditRecord auditRecord: auditRecords) 
					logger.error(auditRecord);
			}
		}
	}

}