package com.odhoman.api.utilities.db;

import org.apache.log4j.Logger;

import com.odhoman.api.utilities.config.AbstractConfig;

/** Configuracion de cada configuracion particular de BD */

public class DbConfig extends AbstractConfig {
	
	private static final long serialVersionUID = 8697950407282345077L;

	@Override
	protected void preloadClasses() {
					
	}
	
	public DbConfig(Logger logger) {
		setLogger(logger);
	}
	
	public void loadConfig(String path) {
		super.loadConfig(path);
	}
	
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
}
