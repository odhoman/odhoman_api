package com.odhoman.api.utilities.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.odhoman.api.utilities.CmIOFormat;
import com.odhoman.api.utilities.transac.ApplicationErrorException;

/**
 * Configuracion Abstracta del Config
 * 
 * @author Fabian Benitez (fb70883)
 * @date 22/07/2008
 * 
 */
public abstract class AbstractConfig extends Properties {

	/**
	 * Autogenerated serializable ID
	 */
	private static final long serialVersionUID = -4698215509307721294L;

	protected Logger logger = null;
	
	protected Thread configReloader = null;
	
	/**
	 * Realiza la configuracion inicial correspondiente al AbstractConfig. Realiza la carga de properties desde un archivo .properties. 
	 * La ubicacion del archivo la obtiene leyendo el atributo de sistema propertyName
	 */
	protected void init(String propertyName) {
		
		String configPath = System.getProperty(propertyName, "");
		
		if("".equals(configPath)) {
			throw new ApplicationErrorException("No se pudo crear la configuracion para la propiedad " + propertyName + " (EJECUTAR JVM CON -D " + propertyName + "=<PATH_ABSOLUTO_AL_CONFIG_FILE>)");
		}		
		System.out.println("AbstractConfig: Leyendo configuracion en: " + configPath);
		loadConfig(configPath);
	}	

	/**
	 * Carga las propiedades desde un archivo
	 * 
	 * @param path
	 *            Ruta del archivo de properties
	 */
	protected void loadConfig(String path) {
		InputStream in;
		try {
			
			in = getClass().getClassLoader().getResourceAsStream(path);
			load(in);
			in.close();
			
			preloadClasses();
			
			checkReloadSettings(path);
			
		} catch (FileNotFoundException e1) {
			throw new ApplicationErrorException("AbstractConfig: No se encontr� el archivo " + path + ". Causa: " + e1.getLocalizedMessage(), e1);			
		} catch (IOException e2) {
			throw new ApplicationErrorException("AbstractConfig: Fallo lectura del archivo de configuracion " + path + ". Causa: " + e2.getLocalizedMessage(), e2);		
		}
	}

	/**
	 * Realiza la precarga de ciertas clases (si corresponde)
	 */
	protected abstract void preloadClasses();

	/**
	 *	Verifica si el archivo de configuracion se debe recargar o no periodicamente y bajo que condiciones 
	 */
	
	protected void checkReloadSettings(String path) {
		
		String reloadable = getProperty(ConfigConstants.CONFIG_RELOADABLE, "N");
		
		if("Y".equals(reloadable)) {
			String period = getProperty(ConfigConstants.CONFIG_RELOADING_PERIOD, "3600000");
			configReloader = new Thread(new ConfigReloader(this, path, Long.valueOf(period))); 
			configReloader.run();
		}
	}
	
	/**
	 * Obtiene un log4j
	 * 
	 * @return Log
	 */
	protected Logger getLogger(String logPathProperty, String logger) {	
		if(getProperty(logPathProperty) == null)
			throw new ApplicationErrorException("AbstractConfig: Error al intentar obtener el logger!!!. La property " + logPathProperty + " no se encuentra!!!");
		
		try {
			
			Properties props = new Properties();
			props.load(getClass().getClassLoader().getResourceAsStream(getProperty(logPathProperty).trim()));
			PropertyConfigurator.configure(props);
			
			return Logger.getLogger(logger.trim());
		} catch(Exception e) {
			throw new ApplicationErrorException("AbstractConfig: Error al intentar obtener el logger. No se pudo configurar el log!!!. Causa: " + e.getLocalizedMessage(), e);
		}
	}

	/**
	 * Obtiene un log4j 
	 * 
	 * @return Logger
	 */
	public Logger getLogger() {
		return logger;
	}
	
	public void setLogger(Logger logger) {
		this.logger = logger;
	}


	/**
	 * Devuelve un nuevo objecto de tipo Properties con el contendio de �ste. Hace una especie de copia, pero de la
	 * clase padre. Es utilizado principalmente para crear contextos JNDI ya que JRun no acepta objetos que no sean
	 * nativos de Java
	 * 
	 * @return Las propiedades de �ste objecto
	 */
	public Properties getProperties() {
		Properties p = new Properties();
		p.putAll(this);
		return p;
	}

	/**
	 * Devuelve el valor de una property como string
	 * 
	 * @param propertyName
	 *            El nombre de la property cuyo valor queremos obtener
	 * @return El valor de la property
	 */
	public String getString(String propertyName) {
		String string = CmIOFormat.getString(containsKey(propertyName.trim()) ? (String) get(propertyName.trim()) : "");
		return string.trim();
	}

	/**
	 * Retorna el path del directorio fijado en la propiedad de sistema especificada
	 * 
	 * @return Un directorio especifico 
	 */
	public static String getDir(String propertyName) {
		String configPath = System.getProperty(propertyName, "");

		int idx = configPath.lastIndexOf('/');
		if (idx < 0)
			return "";

		return configPath.substring(0, idx);
	}

}
