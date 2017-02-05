package com.odhoman.api.utilities.audit;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import com.odhoman.api.utilities.config.AbstractConfig;

/**
 * 
 * Consume de manera constante una queue, llenando una lista de auditRecords. 
 * Una vez que la lista alcanza una tamano especifico, la envia a persistir de manera asincronica.
 * 
 * @author Jonatan Flores (jf16203)
 * @version AGO-2016
 *
 */
public class AuditRecordConsumer extends Thread {

	protected  CopyOnWriteArrayList<AuditRecord> availableAuditRecords;
	private AuditDAO<AuditRecord, AuditRecordFilter> dao;
	private AbstractConfig config;
	private Logger logger;
	private int maxAuditRecordPersist;

	public AuditRecordConsumer(AuditDAO<AuditRecord, AuditRecordFilter> dao, 
						 	   AbstractConfig config, int maxAuditRecordPersist){
		this.availableAuditRecords = new CopyOnWriteArrayList<AuditRecord>();
		this.dao = dao;
		this.config = config;
		this.logger = config.getLogger();
		this.maxAuditRecordPersist = maxAuditRecordPersist;
	}

	@Override
	public void run(){
		
		try {
			
			logger.debug("AuditRecordConsumer - run: se inicia el AuditRecordConsumer para la persistencia asincronica de AuditRecords");

			//Se le resta uno para quede un numero redondo.
			maxAuditRecordPersist = (maxAuditRecordPersist-1);
			
			AuditRecord auditRecord;
			
			while ((auditRecord = AuditRecordQueue.getInstance().takeAuditRecord()) != null) {
				
                if (availableAuditRecords!= null && availableAuditRecords.size()>=maxAuditRecordPersist) {
                	
                	addAuditRecord(auditRecord);
        			List<AuditRecord> auditRecords = cloneActualRecords();
        			clearPersistedRecords(auditRecords);
        			
        			logger.debug("AuditRecordConsumer - run: se envian a persistir " +auditRecords.size()+" AuditRecords");
        			
	                new Thread(new AsynchronousAuditRecordPersistence(auditRecords, dao, config)).start();
	                continue;
				}
                
                addAuditRecord(auditRecord);
			}
			
		} catch (InterruptedException e) {
			logger.error("AuditRecordConsumer - run: se interrumpio la lectura de la queue de registros de auditoria ",e);
			logger.error("Quedaron "+availableAuditRecords.size() 
					     + " registros de auditoria sin enviar a persistir en la lista" 
					     + " se intentan registrar con el dao");
			saveRemainingAuditRecords();
		} catch (Exception e) {
			logger.error("AuditRecordConsumer - run: ocurrio una error al querer leer o enviar a persistir los registros de auditoria ",e);
			logger.error("Quedaron "+availableAuditRecords.size() 
				     + " registros de auditoria sin enviar a persistir en la lista" 
				     + " se intentan registrar con el dao");
			saveRemainingAuditRecords();
		}

	}

	/** Agrega en la lista, un AuditRecord. */
	
	private void addAuditRecord(AuditRecord auditRecord){
		availableAuditRecords.add(auditRecord);
	}

	/** Clona la lista de AuditRecord */
	
	@SuppressWarnings("unchecked")
	protected List<AuditRecord> cloneActualRecords(){
		 return (CopyOnWriteArrayList<AuditRecord>) availableAuditRecords.clone();
	}

	/** Limpia los AuditRecord que ya fueron a persistirse */
	
	private void clearPersistedRecords(List<AuditRecord> auditRecords){
		availableAuditRecords.removeAll(auditRecords);
	}
	
	/** Guarda en BD y limpia los registros de auditoria de la lista */
	
	public void saveAndCleanRemainingAuditRecord(){
		
		logger.debug("AuditRecordConsumer - saveAndCleanRemainingAuditRecord: iniciando ");
		
		if(availableAuditRecords != null && availableAuditRecords.size()>0){
			saveRemainingAuditRecords();
			availableAuditRecords.clear();
		}
		
		logger.debug("AuditRecordConsumer - saveAndCleanRemainingAuditRecord: finalizando ");
	}
	
	/** Guarda registros de auditoria remanentes en la lista */
	
	private void saveRemainingAuditRecords(){
		
		if(availableAuditRecords != null && availableAuditRecords.size()>0){
			
			logger.debug("AuditRecordConsumer - saveRemainingAuditRecords: Se intentan persistir "+availableAuditRecords.size()+ " AuditRecords remanentes");
			
			try {
				dao.insertAuditRecords(availableAuditRecords);
			} catch (Exception e) {
				
				logger.error("No se pudieron insertar los AuditRecords remanentes ",e);
				logger.error("Los Registros de auditoria que no se pudieron insertar fueron los siguientes: ");
				for (AuditRecord auditRecord: availableAuditRecords) 
					logger.error(auditRecord);
			}
			
			logger.debug("AuditRecordConsumer - saveRemainingAuditRecords: "+availableAuditRecords.size()
						 +" AuditRecords remanentes, correctamente persistidos");
		}
	}

}