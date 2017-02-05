package com.odhoman.api.utilities.db;

import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.odhoman.api.utilities.config.AbstractConfig;
import com.odhoman.api.utilities.config.AppConfig;
import com.odhoman.api.utilities.config.ConfigConstants;
import com.odhoman.api.utilities.transac.ApplicationErrorException;

/**
 * 
 * Gestiona las conexiones de base de datos a traves de identificadores
 * 
 * @author Fabian Benitez (fb70883)
 * @date 21/11/2011
 * 
 */

public class DbConnectionManager {

	private Map<String,AbstractConfig> dbConfigs = null;
	
	private Logger logger = null;
	
	public DbConnectionManager() {
		this(AppConfig.getInstance());
	}
	
	public DbConnectionManager(AbstractConfig config) {
		
		dbConfigs = new HashMap<String, AbstractConfig>();
		
		if(null == config)
			throw new ApplicationErrorException("DbConnectionManager: La configuracion recibida es invalida");
		
		init(config);		
	}
	
	public DbConnectionManager(AbstractConfig config, Map<String, AbstractConfig> dbConfigs) {

		if(null == config)
			throw new ApplicationErrorException("DbConnectionManager: La configuracion recibida es invalida");

		init(config, dbConfigs);
	}
	
	private void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	/** Inicializa el manager dejando disponibles las configuraciones de base de datos */
	
	private void init(AbstractConfig config) {
			
		setLogger(config.getLogger());
		
		logger.debug("DbConnectionManager: Inicializando el componente...");
		
		//Se limpia el mapa actual para dar lugar a una nueva posible configuracion
		dbConfigs.clear();
		
		String connectionIds = config.getProperty(ConfigConstants.APP_DB_CONN_IDS, "");	//Ids de conexion
		logger.debug("DbConnectionManager: Parametro leido \"connectionIds\" = " + connectionIds);
				
		if("".equals(connectionIds))
			return;	//No se hace nada mas
		
    	String [] connIds = connectionIds.split(",");
    	
    	String connectionConfigPaths = config.getProperty(ConfigConstants.APP_DB_CONN_CONFIG_PATHS, "");	//Paths a los configs de DB
    	logger.debug("DbConnectionManager: Parametro leido \"connectionConfigPaths\" = " + connectionConfigPaths);
    	
    	if("".equals(connectionConfigPaths))
			throw new ApplicationErrorException("No se pudo inicializar el DbConnectionManager. Las configuraciones estan mal definidas");
    	
    	String [] connConfigPaths = connectionConfigPaths.split(",");
    	
    	if(connIds.length != connConfigPaths.length)
    		throw new ApplicationErrorException("No se pudo inicializar el DbConnectionManager. Las configuraciones estan mal definidas");
    	
    	DbConfig cfg = null;
    	for(int i=0; i < connIds.length; i++) {
    		cfg = new DbConfig(config.getLogger());
    		cfg.loadConfig(connConfigPaths[i]);    		
    		dbConfigs.put(connIds[i], cfg);
    	}
    	
    	logger.debug("DbConnectionManager: Configuraciones inicializadas = " + dbConfigs);
    	
	}

	private void init(AbstractConfig config, Map<String, AbstractConfig> dbConfigs) {
		
		setLogger(config.getLogger());
		this.dbConfigs = dbConfigs;
    	
    	logger.debug("DbConnectionManager: Configuraciones inicializadas = " + dbConfigs);    	
	}

	/** Retorna un DatabaseConnection para el id solicitado */
	
	public DatabaseConnection getDatabaseConnection(String id) {
		
		if(!dbConfigs.containsKey(id))
			throw new ApplicationErrorException("No se encontro ninguna configuracion con id " + id);
		
		try {
			DatabaseConnection dbc = new DatabaseConnection();
			AbstractConfig config = dbConfigs.get(id);
			
			if(null == config)
				throw new ApplicationErrorException("No hay una configuracion valida para el id " + id);
			
			logger.debug("DbConnectionManager: Obteniendo DatabaseConnection para el id " + id);
			dbc.setConfigure(config);		
			return dbc;
		} catch (NamingException e) {
			logger.error("No se pudo crear el objeto DatabaseConnection", e);
			throw new ApplicationErrorException("No se pudo crear el objeto DatabaseConnection", e);
		}
	}

}
