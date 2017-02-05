package com.odhoman.api.utilities.dependencyinjector;

import java.lang.reflect.Method;

import com.odhoman.api.utilities.CmIOFormat;

public class SetterManager {

	public static final String PARAM_SEPARATOR = ",";
	
	public static final String PARAM_VALUE_SEPARATOR = "=";
	
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	
	public SetterManager() {
		
	}
	
	/** 
	 *  Carga un objeto segun los parametros indicados cumpliendo el siguiente patron:
	 *  
	 *  	<paramName><PARAM_VALUE_SEPARATOR><paramValue> [... <PARAM_SEPARATOR><paramName><PARAM_VALUE_SEPARATOR><paramValue> ]
	 */
	
	public void loadObject(Object filter, String params) throws Exception {
		
		String [] filterParams = params.split(PARAM_SEPARATOR);
		
		Method [] methods = filter.getClass().getMethods();
		
		String [] param = null;
		Method method = null;
		for(String p : filterParams) {
			param = p.split(PARAM_VALUE_SEPARATOR);
			method = getMethod(methods, param[0]);
			method.invoke(filter, getParameter(method, param[1]));
		}	
	}
	
	/** Obtiene de la lista de metodos aquel setter que tenga el nombre del atributo especificado */
	
	private Method getMethod(Method [] methods, String name) {
		for(Method m : methods) {
			if(m.getName().equalsIgnoreCase("set" + name.trim()))
				return m;
		}
		throw new RuntimeException("Metodo no encontrado para atributo " + name);
	}
	
	/** Obtiene del metodo especificado el parametro cargado segun el valor informado */
	
	private Object getParameter(Method method, String paramValue) {
		Class<?> [] params = method.getParameterTypes();
		
		if(params.length != 1)
			throw new RuntimeException("Setter con mas de un parametro!");
		
		String name = params[0].getName();
		
		Object o = null;
		if(name.contains("Long")) {
			o = new Long(paramValue.trim());
		} else if(name.contains("Integer")) {
			o = new Integer(paramValue.trim());
		} else if(name.contains("String")) {
			o = new String(paramValue.trim());
		} else if(name.contains("java.util.Date")) {
			o = CmIOFormat.getFormattedDate(paramValue.trim(), DATE_FORMAT);
		} else if(name.contains("Boolean")) {
			o = new Boolean(paramValue.trim());
		} else {
			throw new RuntimeException("Tipo de parametro desconocido!");
		}		
		return o;
				
	}
	
}
