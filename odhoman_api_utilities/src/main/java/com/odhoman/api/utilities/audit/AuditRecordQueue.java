package com.odhoman.api.utilities.audit;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import com.odhoman.api.utilities.config.AbstractConfig;
import com.odhoman.api.utilities.config.AppConfig;
import com.odhoman.api.utilities.config.ConfigConstants;

/**
 * 
 * Singleton que se encarga de manejar una queue de AuditRecord de manera sincronizada.
 * Solo se podran poner y sacar AuditRecords.
 * 
 * @author Jonatan Flores (jf16203)
 * @version AGO-2016
 */
public class AuditRecordQueue {

	private static AuditRecordQueue auditRecordQueue;
	private static BlockingQueue<AuditRecord> auditRecords;
	private AbstractConfig config;
	private Logger logger;
	
	private AuditRecordQueue(){
		
		int maxItems = 100;
		
		try {
			config = AppConfig.getInstance();
			logger = config.getLogger();
			String maxItemsQueue = config.getProperty(ConfigConstants.MAX_ITEMS_QUEUE_AUDITRECORDS, "100");
			maxItems = new Integer(maxItemsQueue);
		} catch (Exception e) {
			logger = Logger.getLogger(AuditRecordQueue.class);
			logger.error("Ocurrio un error al querer inicializar AuditRecordQueue");
		} catch (Error e) {
			logger = Logger.getLogger(AuditRecordQueue.class);
			logger.error("Ocurrio un error al querer inicializar AuditRecordQueue");
		}

		auditRecords = new ArrayBlockingQueue<AuditRecord>(maxItems);
		
	}

	public static AuditRecordQueue getInstance(){
		
		if(auditRecordQueue == null){
			auditRecordQueue = new AuditRecordQueue();
		}
		
		return auditRecordQueue;
	}

	/** Ingresa un item en la queue */
	
	public void putAuditRecord(AuditRecord auditRecord) throws InterruptedException{
		auditRecords.put(auditRecord);
	}

	/** Toma una item de la queue */
	
	public AuditRecord takeAuditRecord() throws InterruptedException{
		return auditRecords.take();
	}
	
	/** Indica si queda espacio en la queue */
	
	public boolean isRemainingCapacity(){
		return (auditRecords.remainingCapacity()>=1);
	}
	
	public int getRemainingCapacity(){
		return auditRecords.remainingCapacity();
	}

}