package com.odhoman.api.utilities;

import java.sql.Connection;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odhoman.api.utilities.config.AbstractConfig;
import com.odhoman.api.utilities.config.ConfigConstants;
import com.odhoman.api.utilities.db.DatabaseConnection;
import com.odhoman.api.utilities.db.DbConnectionManager;

/**
 * 
 * Test para obtencion de conexiones a base de datos
 *  
 * @author Fabian Benitez (fb70883)
 * @date 22/11/2011
 * 
 */

public class DbConnectionTest {

	protected TstConfig config;	//Configuracion de Test
	
	@Before
	public void setUp() throws Exception {
		config = new TstConfig();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/** Clase de configuracion para testing */
	
	public class TstConfig extends AbstractConfig {
		
		private static final long serialVersionUID = 1L;

		public TstConfig() {
					
			setProperty("act.log4j.path", "src/test/resources/log4j.properties");
			setLogger(getLogger("act.log4j.path", "test"));
		}
		
		@Override
		protected void preloadClasses() {
						
		}		
		
	}
	
	/**
	 * Se realiza la configuracion de las conexiones de bd con una sola configuracion.
	 * 
	 * Se valida que funcione como corresponde para casos en los que no existen configuraciones 
	 * realizadas 
	 * 
	 */
	
	@Test
	public void getDbConnection_UnaConfiguracionSimple_OK() {
	
		config.setProperty(ConfigConstants.APP_DB_CONN_IDS, "test1");
		config.setProperty(ConfigConstants.APP_DB_CONN_CONFIG_PATHS, "src/test/resources/test1.properties");
		
		DbConnectionManager mgr = new DbConnectionManager(config);
		DatabaseConnection connection = mgr.getDatabaseConnection("test1");
		
		Assert.assertTrue(connection != null);
		
		try {
			mgr.getDatabaseConnection("test");
		} catch (Exception e) {			
			Assert.assertTrue(true);
		}		
		
		try {
			mgr.getDatabaseConnection("test2");
		} catch (Exception e) {			
			Assert.assertTrue(true);
		}
	}
	
	/**
	 * Se realiza la configuracion de las conexiones de bd con dos configuraciones.
	 * 
	 * Se valida que funcione como corresponde para casos en los que no existen configuraciones 
	 * realizadas
	 * 
	 */
	
	@Test
	public void getDbConnection_DosConfiguracionesSimples_OK() {
	
		config.setProperty(ConfigConstants.APP_DB_CONN_IDS, "test1,test2");
		config.setProperty(ConfigConstants.APP_DB_CONN_CONFIG_PATHS, "src/test/resources/test1.properties,src/test/resources/test2.properties");
		
		DbConnectionManager mgr = new DbConnectionManager(config);
		
		Assert.assertTrue(mgr.getDatabaseConnection("test1") != null);
		
		try {
			mgr.getDatabaseConnection("test");
		} catch (Exception e) {			
			Assert.assertTrue(true);
		}		
		
		Assert.assertTrue(mgr.getDatabaseConnection("test2") != null);		
	}
	
	/**
	 * Se realiza la configuracion de las conexiones de bd con una sola configuracion y realiza 
	 * una conexion exitosa
	 */
	
	@Test
	public void getDbConnection_UnaConfiguracionSimpleConConexion_OK() {
	
		config.setProperty(ConfigConstants.APP_DB_CONN_IDS, "test1");
		config.setProperty(ConfigConstants.APP_DB_CONN_CONFIG_PATHS, "src/test/resources/test1.properties");
		
		DbConnectionManager mgr = new DbConnectionManager(config);
		DatabaseConnection connection = mgr.getDatabaseConnection("test1");
		
		Assert.assertTrue(connection != null);
		
		try {
			Connection c = connection.connect();
			Assert.assertFalse(c.isClosed());
			connection.close();
			Assert.assertTrue(c.isClosed());
		} catch (Exception e) {
			e.printStackTrace(System.out);
			Assert.assertFalse(true);
		}
	}

}
