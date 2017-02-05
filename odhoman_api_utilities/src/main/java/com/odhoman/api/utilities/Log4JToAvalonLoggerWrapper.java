package com.odhoman.api.utilities;

import org.apache.avalon.framework.logger.Logger;
import org.apache.log4j.Level;

/**
 * Utilidad que tranforma un Log4J en un Logger de Avalon
 *
 * @author em06297
 * @version 13/08/2010
 *
 */
public class Log4JToAvalonLoggerWrapper implements Logger {

	org.apache.log4j.Logger logger;
	
	public Log4JToAvalonLoggerWrapper(org.apache.log4j.Logger logger){
		this.logger = logger;
	}
	
	public void debug(String arg0) {
		this.logger.debug(arg0);
	}

	public void debug(String arg0, Throwable arg1) {
		this.logger.debug(arg1);
	}
	
	public void error(String arg0) {
		this.logger.error(arg0);
	}
	
	public void error(String arg0, Throwable arg1) {
		this.logger.error(arg0, arg1);
	}
	
	public void fatalError(String arg0) {
		this.logger.fatal(arg0);
	}
	
	public void fatalError(String arg0, Throwable arg1) {
		this.logger.fatal(arg0, arg1);
	}
	
	public Logger getChildLogger(String arg0) {
		//TODO: Ver qu� hacer con arg0 y qu� hay que devolver realmenet aqu�
		return this; 
	}

	public void info(String arg0) {
		this.logger.info(arg0);
	}

	public void info(String arg0, Throwable arg1) {
		this.logger.info(arg0, arg1);
	}

	public boolean isDebugEnabled() {
		return this.logger.isDebugEnabled();
	}
	
	public boolean isErrorEnabled() {
		return this.logger.isEnabledFor(Level.ERROR);
	}
	
	public boolean isFatalErrorEnabled() {
		return this.logger.isEnabledFor(Level.FATAL);
	}
	
	public boolean isInfoEnabled() {
		return this.logger.isInfoEnabled();
	}

	public boolean isWarnEnabled() {
		return this.logger.isEnabledFor(Level.WARN);
	}
	
	public void warn(String arg0) {
		this.logger.warn(arg0);
	}
	
	public void warn(String arg0, Throwable arg1) {
		this.logger.warn(arg0, arg1);		
	}
}
