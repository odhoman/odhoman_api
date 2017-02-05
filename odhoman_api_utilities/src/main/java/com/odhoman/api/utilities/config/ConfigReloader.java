package com.odhoman.api.utilities.config;

import java.io.FileInputStream;

/**
 * 
 * Permite recargar el archivo de configuracion
 * 
 * @author Fabian Benitez (fb70883)
 * @date 23/11/2011
 * 
 */

public class ConfigReloader implements Runnable {

	private AbstractConfig config;
	private String path;
	private Long reloadingPeriod;	//en msecs
	
	public ConfigReloader(AbstractConfig config, String path, Long reloadingPeriod) {
		this.config = config;
		this.path = path;
		this.reloadingPeriod = reloadingPeriod;
	}

	public void run() {
		config.getLogger().info("ConfigReloader - run: Iniciando ejecucion...");
		
		while(true) {
			try {
				Thread.sleep(reloadingPeriod);
				
				//Se despierta y ejecuta despues de "sleep" msecs
				
				config.getLogger().debug("ConfigReloader: Iniciando reloading...");
				
				FileInputStream in = new FileInputStream(path);
				config.load(in);
				in.close();
				
				//Se verifica si cambiaron las condiciones de reloading...
				
				String reloadable = config.getProperty(ConfigConstants.CONFIG_RELOADABLE, "N");
				
				if("Y".equals(reloadable)) {
					String period = config.getProperty(ConfigConstants.CONFIG_RELOADING_PERIOD, "3600000");
					reloadingPeriod = Long.valueOf(period);
				} else {
					config.getLogger().debug("ConfigReloader: Luego de la recarga se solicita finalizar el proceso...");
					break;
				}

			} catch (Exception e) {
				config.getLogger().error("ConfigReloader - Ocurrio un error al ejecutar el reloader", e);
				break;
			}				
		}
		config.getLogger().info("ConfigReloader - run: Finalizando ejecucion del proceso...");
	}

}
