package com.odhoman.api.utilities.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.odhoman.api.utilities.config.AbstractConfig;
import com.odhoman.api.utilities.config.ConfigConstants;
import com.odhoman.api.utilities.dao.DAOException;
import com.odhoman.api.utilities.transac.ApplicationErrorException;

/**
 * Provee una conexi�n a base de datos
 * 
 * @author em06297 (ajustes por fb70883)
 * @date 14/09/2008
 * 
 */
public class DatabaseConnection {

	/**
	 * Configuraci�n
	 */
	private AbstractConfig config = null;

	/**
	 * Data source
	 */
	private DataSource dataSource = null;

	/**
	 * Conexi�n
	 */
	private Connection connection = null;
	
	private String connection_type = ConfigConstants.CONN_TYPE_SIMPLE;	//valor por default

	/**
	 * Inicializa el dataSource utilizando JNDI y la configuraci�n
	 * 
	 * @param config
	 *            Configuraci�n a partir de la cual se tomar� el datasource
	 * @throws NamingException
	 *             Si no puede encontrar el dataSource
	 */
	public void setConfigure(AbstractConfig config) throws NamingException {

		this.config = config;		
		connection_type = config.getString(ConfigConstants.APP_DB_CONNECTION_TYPE);
		
		if("".equals(connection_type)){
			throw new NamingException("La property " + ConfigConstants.APP_DB_CONNECTION_TYPE + " no est� correctamente definida");
		}
		
		//Conexion via DS => se deja configurado el datasource a utilizar luego al conectarse
		if(connection_type.equals(ConfigConstants.CONN_TYPE_DS)) {
		
			String dsJNDIName = config.getString(ConfigConstants.COMMON_DB_DATASOURCE);			
			if("".equals(dsJNDIName)){
				throw new NamingException("La property "+ ConfigConstants.COMMON_DB_DATASOURCE + " no est� correctamente definida");
			}			
			
			// TODO: Ver de adaptarlo a distintos servidores, Esto esta hecho para TOMCAT
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
						
			dataSource = (DataSource) envContext.lookup(dsJNDIName);			
		}
		
		config.getLogger().debug("DatabaseConnection: Conexion de tipo " + connection_type + " configurada!");
	}

	/**
	 * Se conecta a la base de datos con el metodo que se haya configurado y devuelve la conexion
	 * 
	 * @throws DAOException Si no se pudo conectar
	 */
	public Connection connect() throws DAOException {
		try {
			
			if(connection != null) {
				throw new ApplicationErrorException("DatabaseConnection: Ya existe una conexion abierta!");
			}
			
			if(null == config)
				throw new ApplicationErrorException("DatabaseConnection: Nunca se ha realizado la configuracion!");
			
			config.getLogger().debug("DatabaseConnection: Realizando conexion de tipo " + connection_type + "...");			
					
			if(connection_type.equals(ConfigConstants.CONN_TYPE_DS)) {

				//Conexion via DS	
				if(null == dataSource)
					throw new ApplicationErrorException("DatabaseConnection: No existe un datasource valido");	
				
				connection = dataSource.getConnection();
				
			} else if(connection_type.equals(ConfigConstants.CONN_TYPE_SIMPLE)) {
				
				//Conexion via JDBC simple
				Class.forName(config.getString(ConfigConstants.COMMON_DB_DRIVER));
				connection = DriverManager.getConnection(
						config.getString(ConfigConstants.COMMON_DB_URL),
						config.getString(ConfigConstants.COMMON_DB_USER),
						config.getString(ConfigConstants.COMMON_DB_PASSWORD)
						);
			}
			return connection;
		} catch (Exception e) {
			config.getLogger().error("No se pudo conectar a la base de datos", e);
			throw new DAOException("No se pudo conectar a la base de datos", e);
		}
	}

	/**
	 * Devuelve un PreparedStatement. Este m�todo no hace nada muy interesante. Solo se provee por compatibilidad con el
	 * ex PoolkiConnect
	 * 
	 * @see java.sql.Connection#prepareStatement(String)
	 */
	public PreparedStatement prepare(String sql) throws SQLException {

		if (null == connection) {
			throw new ApplicationErrorException("DatabaseConnection: No existe una conexion abierta para operar");
		}
		
		return connection.prepareStatement(sql);
	}
	
	/**
	 * Devuelve un CallableStatement
	 * 
	 * @see java.sql.Connection#prepareCall(String)
	 */
	public CallableStatement prepareCall(String sql) throws SQLException {

		if (null == connection) {
			throw new ApplicationErrorException("DatabaseConnection: No existe una conexion abierta para operar");
		}
		
		return connection.prepareCall(sql);
	}


	/**
	 * Cierra la conexi�n
	 * 
	 */
	public void close(){
		
		if (null == connection) {
			return;
		}
		
		try {
			config.getLogger().debug("DatabaseConnection: Cerrando la conexion de tipo " + connection_type + "...");
			connection.close();
			config.getLogger().debug("DatabaseConnection: Conexion de tipo " + connection_type + " cerrada!");
			connection = null;
		} catch (SQLException e) {
			config.getLogger().error("No se pudo cerrar la conexi�n a la base de datos", e);
		}
	}
}
