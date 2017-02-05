package com.odhoman.api.utilities.dependencyinjector;

import bsh.BshClassManager;
import bsh.Interpreter;

/**
 * 
 * Dependency Injector basado en Bean Shell
 * 
 * @author Fabian Benitez (fb70883)
 * 
 */

public class DependencyInjector {
	
	/** Genera y devuelve una instancia del tipo especificado */
	
	@SuppressWarnings("unchecked")
	public static <T>T getInstance(String clazz) throws DependencyInjectorException {
		try {			
			Interpreter bsh = new Interpreter();			
			bsh.eval("injectedObject = new " + clazz + "();");			
			Object obj = bsh.get("injectedObject");
			return (T) obj; 
		} catch (Exception e) {
			throw new DependencyInjectorException("No se pudo crear la instancia de la clase " + clazz, e);
		}
	}
	
	/** Genera y devuelve una instancia del tipo especificado creada con parametros. Para esto el objeto debe implementar Initializable */
	
	@SuppressWarnings("unchecked")
	public static <T>T getInstance(String clazz, Object...params) throws DependencyInjectorException {
		Initializable obj = getInstance(clazz);
		try {			
			obj.init(params);
			return (T) obj;
		} catch (Exception e) {
			throw new DependencyInjectorException("No se pudo crear la instancia de la clase " + clazz, e);
		}
	}
	
	/** Carga la clase especificada */
	
	public static void loadClass(String clazz) throws DependencyInjectorException {
		try {			
			BshClassManager.createClassManager().classForName(clazz);			
		} catch (Exception e) {
			throw new DependencyInjectorException("No se pudo cargar la clase especificada " + clazz, e);
		}
	}

}
