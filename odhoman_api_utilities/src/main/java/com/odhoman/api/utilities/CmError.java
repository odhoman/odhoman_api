package com.odhoman.api.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.odhoman.api.utilities.transac.ApplicationError;
import com.odhoman.api.utilities.transac.ApplicationErrorException;
import com.odhoman.api.utilities.transac.ApplicationWarning;
import com.odhoman.api.utilities.transac.ApplicationWarningException;
import com.odhoman.api.utilities.transac.ErrorCode;
import com.odhoman.api.utilities.transac.MultipleErrorsTransaction;
import com.odhoman.api.utilities.transac.TransactionStatus;

/**
 * Clase de constantes de errores y mensajes. Por convencion errores de systema tiene codigos menores que 100 Los
 * errores con prefijos deben tomar valores mayor o igual a 100. Y estan reservados para errores de la logica de la
 * aplicacion.
 * 
 * @author em06297 ("practicamente reescrita" por) / Fabian Benitez (fb70883)
 * @date 12/02/2010
 * 
 */
public class CmError {

	private static Map<String, Map<String, String>> errorModelMap = new HashMap<String, Map<String, String>>();

	/**
	 * Mensajes de error de usuario
	 */
	private static Map<String, Map<String, String>> userErrorsMap = new HashMap<String, Map<String, String>>();

	private static Map<String, Map<String, String>> warningModelMap = new HashMap<String, Map<String, String>>();

	private static Map<Integer, String> errstrMap = new HashMap<Integer, String>();

	/**
	 * De aqui hacia abajo se consideran errores de sistemas
	 */
	private static final int E_SYS = -100;

	private static final int E_WEB_REQ_PARAM = -100;

	private static final int E_RESOURCE = -10;

	private static final int E_REMOTE_CALL = -11;

	private static final int E_DAO_SELECT = -13;

	private static final int E_DAO_INSERT = -12;

	private static final int E_DAO_UPDATE = -14;

	private static final int E_DAO_DELETE = -16;

	private static final int E_DAO_LOCK = -18;

	private static final int E_ACCESS = -20;

	/** Otras constantes */
	
	private static int ERROR_CODE_SIZE = 3;
	
	static {
		/* Errores cr�ticos y de sistema */
		errstrMap.put(E_WEB_REQ_PARAM, "Parametros invalidos en request.");
		errstrMap.put(E_RESOURCE, "Falla al intentar utilizar el recurso.");
		errstrMap.put(E_REMOTE_CALL, "Se produjo un error en un componente remoto de aplicacion.");

		errstrMap.put(E_DAO_SELECT, "Se produjo un error al intentar obtener registros de la DB.");
		errstrMap.put(E_DAO_INSERT, "Se produjo un error al intentar insertar registros en la DB.");
		errstrMap.put(E_DAO_UPDATE, "Se produjo un error al intentar modificar registros en la DB.");
		errstrMap.put(E_DAO_DELETE, "Se produjo un error al intentar eliminar registros en la DB.");

		errstrMap.put(E_DAO_LOCK, "-");
		errstrMap.put(E_ACCESS, "No tiene permisos para realizar la operacion.");

		errstrMap.put(2009, "Se perdi� la conexi�n contra MQ");
		errstrMap.put(2011, "Error en la configuraci�n del entorno de MQ");
		errstrMap.put(2012, "Error en la configuraci�n del entorno de MQ");
		errstrMap.put(2016, "La cola de mensajes MQ est� inhabilitada para GET (leer mensajes)");
		errstrMap.put(2017, "Se super� la cantidad m�xima de handles abiertos permitidos en MQ Manager");
		errstrMap.put(2018, "El handle de conexi�n de MQ es inv�lido");
		errstrMap.put(2024, "Se super� la cantidad m�xima permitida de mensajes sin enviar en MQ Manager");
		errstrMap.put(2025, "Se super� la cantidad m�xima permitida de conexiones contra MQ");
		errstrMap.put(2030, "El mensaje enviado a la cola MQ supera el tama�o m�ximo permitido");
		errstrMap.put(2031, "El mensaje enviado a la cola MQ supera el tama�o m�ximo permitido");
		errstrMap.put(2033, "No se pudo resolver la transacci�n en el tiempo esperado");
		errstrMap.put(2034, "La cola de mensajes MQ no contiene un mensaje bajo el cursor");
		errstrMap.put(2035, "Error de autorizaci�n en la cola de mesajes de MQ");
		errstrMap.put(2036, "La cola de mensajes MQ no est� abierta para ver mensajes");
		errstrMap.put(2037, "La cola de mensajes MQ no est� abierta para GET (leer mensajes)");
		errstrMap.put(2039, "La cola de mensajes MQ no est� abierta para PUT (colocar mensajes)");
		errstrMap.put(2042, "No se pudo abrir la cola de mensajes MQ porque ya esta abierta o est� en uso");
		errstrMap.put(2045, "La cola de mensajes MQ no admite las opciones suministradas o son inconsistentes");
		errstrMap.put(2046, "La cola de mensajes MQ no admite las opciones suministradas o son inconsistentes");
		errstrMap.put(2051, "La cola de mensajes MQ est� inhabilitada para PUT (colocar mensajes)");
		errstrMap.put(2052, "La cola de mensajes MQ fue eliminada o no existe");
		errstrMap.put(2053, "La cola de mensajes MQ est� llena");
		errstrMap.put(2056, "No hay suficiente espacio en disco para la cola de mensajes MQ");
		errstrMap.put(2058, "Error en la configuraci�n del entorno de MQ");
		errstrMap.put(2059, "No se pudo establecer una conexi�n contra MQ Manager");
		errstrMap.put(2063, "Error de seguridad en MQ");
		errstrMap.put(2082, "Error en la configuraci�n del entorno de MQ");
		errstrMap.put(2085, "Error en la configuraci�n del entorno de MQ");
		errstrMap.put(2086, "Error en la configuraci�n del entorno de MQ");
		errstrMap.put(2087, "Error en la configuraci�n del entorno de MQ");
		errstrMap.put(2101, "Objeto en MQ Manager da�ado");
		errstrMap.put(2152, "Error en la configuraci�n del entorno de MQ");
		errstrMap.put(2153, "Error en la configuraci�n del entorno de MQ");
		errstrMap.put(2184, "Error en la configuraci�n del entorno de MQ");
		errstrMap.put(2195, "Error inesperado en MQ Manager");
		
	}

	public static String getStackTrace(Throwable exception) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * Agrega un warning al model
	 * 
	 * @param nameModel
	 *            Nombre del model
	 * @param codeError
	 *            C�digo de warning
	 * @param descError
	 *            Descripci�n del warning
	 */
	protected static void addModelWarning(String nameModel, String codeWarning, String descWarning) {
		Map<String, String> hm = warningModelMap.get(nameModel);
		if (hm == null) {
			hm = new HashMap<String, String>();
			warningModelMap.put(nameModel, hm);
		}
		hm.put(codeWarning, descWarning);
	}

	/**
	 * Agrega un error al model
	 * 
	 * @param nameModel
	 *            Nombre del model
	 * @param codeError
	 *            C�digo de error
	 * @param descError
	 *            Descripci�n del error
	 */
	protected static void addModelError(String nameModel, String codeError, String descError) {
		Map<String, String> hm = errorModelMap.get(nameModel);
		if (hm == null) {
			hm = new HashMap<String, String>();
			errorModelMap.put(nameModel, hm);
		}
		hm.put(codeError, descError);
	}

	/**
	 * @author FB7883
	 * @date 17/09/08 Para soportar los errores de usuario
	 */

	protected static void addModelError(String nameModel, String codeError, String descError, String userError) {
		Map<String, String> hm = errorModelMap.get(nameModel);
		if (hm == null) {
			hm = new HashMap<String, String>();
			errorModelMap.put(nameModel, hm);
		}
		hm.put(codeError, descError);

		Map<String, String> userErrors = userErrorsMap.get(nameModel);
		if (userErrors == null) {
			userErrors = new HashMap<String, String>();
			userErrorsMap.put(nameModel, userErrors);
		}
		userErrors.put(codeError, userError);
	}
	
	/** 
	 * 	Utilizado por compatibilidad con algunas cosas que no usan el esquema de excepciones para los errores
	 * 
	 * @author fb70883 
     * @date 17/09/2008
	 * 
	 */
		
	public static String getModelError(String nameModel, String codeError) {
		Map<String, String> hm = errorModelMap.get(nameModel);		
		
		String dftmsg = nameModel + " - Error: " + codeError;		
		if (hm == null) {
		    return dftmsg;
		}
		
		String ret = hm.get(getStdErrorCode(codeError));
		if (ret == null) {
		    return dftmsg;
		}
		return dftmsg + " - " + ret;
    }
	
	/** 
	 *	Para obtener la descripcion de usuario del error. Si no tiene entonces obtiene la descripcion del modelo
	 *
     * @author fb70883 
     * @date 17/09/2008
     * 
     */
    
    public static String getUserError(String nameModel, String codeError) {
    	Map<String, String> hm = userErrorsMap.get(nameModel.trim());		
		if (hm == null) { 
			return getModelError(nameModel, codeError);
		} else {
			String msg = (String) hm.get(getStdErrorCode(codeError));
			return (msg == null) ? getModelError(nameModel, codeError) : msg;
		}
    }
	
	/** 
     * @author fb70883 
     * @date 8/08/2008
     * Para obtener el string estandarizado del codigo de error
     */
    
    protected static String getStdErrorCode(String errcode) {
    	String stdcode = "";
    	for(int i = 0; i < (ERROR_CODE_SIZE - errcode.length()); i++) {
    		stdcode += "0";    			
    	}
    	stdcode += errcode;    	
    	return stdcode; 
    }    

	/**
	 * Coloca la descripci�n del error o warning.
	 * 
	 * @param model
	 *            D�nde se va a colocar la descripci�n del error
	 * @param nameModel
	 *            Nombre del model
	 * @param codeError
	 *            C�digo de error
	 * @return <code>true</code> si no hay un error (codeError == 0) o si hay solo un warning. <code>false</code>
	 * 
	 * @deprecated Ver su reemplazo en checkTransaction
	 * @see CmError#checkTransaction(String, TransactionStatus)
	 */
	private static boolean setModelErrorOrWarning(Common model, String nameModel, String codeError) {

		// No hay error
		if (codeError.equals("0")) {
			model.setCmErrDescription("");
			return true;
		} // Es un error de sistema
		else if (new Integer(codeError).intValue() < E_SYS) {
			model.setCmErrDescription(errstrMap.get(new Integer(codeError)));
			return false;
		}

		// Buscamos en los errores del sistema ...
		Map<String, String> hm = errorModelMap.get(nameModel);
		boolean bRet = false;

		if (hm == null) {
			// ... o si no en los warnings
			hm = warningModelMap.get(nameModel);

			if (hm != null) {
				// Si encontramos un warning no vamos a devolver false
				bRet = true;
			}
		}

		// Si no encontramos el model, ponemos el nombre del model : c�digo de error
		if (hm == null) {
			model.setCmErrDescription(nameModel + ": " + codeError);
		} else {
			String textoError = hm.get(codeError);
			// Si no encontramos una descripci�n, ponemos el nombre del model : c�digo de error
			if (textoError == null) {
				model.setCmErrDescription(nameModel + ": " + codeError);
			}

			model.setCmErrDescription(codeError + " " + textoError);
		}

		return bRet;
	}

	/**
	 * Verifica si hay un error en la transacci�n
	 * 
	 * @param cm
	 *            Model
	 * @param name
	 *            Nombre de la transacci�n
	 * @param okFlag
	 * @param errCode
	 * @return <code>false</code> si se encontr� un error. <true> si no hay error o existe solo un warning
	 * 
	 * @deprecated Ver su reemplazo en checkTransaction
	 * @see CmError#checkTransaction(String, TransactionStatus)
	 */
	public static boolean trOk(Common cm, String name, String okFlag, String errCode) {
		if (okFlag.equals("N")) {
			if (!"".equals(errCode)) {
				boolean bRet = setModelErrorOrWarning(cm, name, errCode);

				// TODO: Reveer todo el manejo de errores y warnings! Hoy solo s� si tengo errors vs warnings con este
				// "flag" del errCode que se setea s�lo si tengo errores
				if (!bRet) {
					cm.setCmErrCode(new Long(errCode));
				}

				return bRet;
			} else {
				cm.setCmErrCode(-1L);
				cm.setCmErrDescription("La transaccion no indica el codigo de error.");

			}
			return false;
		}
		return true;
	}

	/**
	 * Maneja errores o warnings en transacciones que pueden devolver varios
	 * 
	 * @param cm
	 *            Una transacci�n con (posiblemente) varios errores y/o warnings
	 * @param name
	 *            Nombre del model
	 * @param okFlag
	 *            Flag que indica el estado de la transacci�n
	 * @return <code>false</code> si hubo error o <code>true</code> si no hubo errores, o hubo solo warnings
	 * 
	 * @deprecated Ver su reemplazo en checkTransaction
	 * @see CmError#checkTransaction(String, TransactionStatus)
	 */
	public static boolean trOk(MultipleErrorsTransaction cm, String name, String okFlag, String errCode) {
		String lastErrCode = "";
		StringBuffer listaErrores = new StringBuffer();

		// S�lo devolveremos false si hay alg�n error. Si hay solo warnings devolvemos true;
		boolean bRet = true;

		if (okFlag.equals("N")) {

			for (ErrorCode err : cm.getErrores()) {

				// Si setModelErrorOrWarning devuelve false es porque encontr� un error "de verdad".
				if (!setModelErrorOrWarning(cm, name, err.getErrorCode())) {
					bRet = false;
				}

				// Vamos agregando los errores y warnings a la lista separados por un salto de l�nea HTML (<br>) para
				// que se puedan ver bien en el status loader
				listaErrores.append(cm.getCmErrDescription());
				listaErrores.append("<br>");
				lastErrCode = err.getErrorCode();
			}

			// TODO: Reveer todo el manejo de errores y warnings! Hoy solo s� si tengo errors vs warnings con este
			// "flag" del errCode que se setea s�lo si tengo errores
			if (!bRet) {
				cm.setCmErrCode(new Long(lastErrCode));
			}

			cm.setCmErrDescription(listaErrores.toString());
			return bRet;
		} else if (okFlag.equals("Y")) {
			return true;
		} else {
			// Si no viene ni Y ni N devuelvo false, por ejemplo algunas veces llega ""
			cm.setCmErrCode(-1L);
			cm.setCmErrDescription("Error en la Transaccion.");
			return false;
		}
	}

	/**
	 * Valida si hay errores y/o warnings y los informa a traves de excepciones <br>
	 * <br>
	 * Todo dao deber&iacute;a usar esta interfaz ignorando cualquier forma para el manejo de errores. Si ocurriese una
	 * exception de cualquier tipo y alguien necesitara hacer algo especial, deber&aacute; atraparla, hacer lo que deba
	 * hacer y luego relanzarla para que sea el controlador quien redireccione a la pantalla de error correspondiente.<br>
	 * La pantalla de error sera la que entienda si debe listar errores o warnings. <br>
	 * <br>
	 * No m&aacute;s control de errores por okflag en Y o N, o cualquier forma similar. Si ocurriese alg&uacute;n error,
	 * se cortar&aacute; el flujo normal de ejecucion inmediamente y el usuario se enterar&aacute;.
	 * 
	 * @author Fabian Benitez (fb70883)
	 * @version 5/03/2010
	 * 
	 * @param name Nombre de la transacci&oacute;n
	 * @param status Referencia al estado de la transacci&oacute;n
	 */
	public static void checkTransaction(String name, TransactionStatus status) {
		if (status.isOk())
			return;

		// Hay errores o warnings...
		Map<String, String> mappedErrors = errorModelMap.containsKey(name) ? errorModelMap.get(name)
				: new HashMap<String, String>();
		Map<String, String> mappedUserErrors = userErrorsMap.containsKey(name) ? userErrorsMap.get(name)
				: new HashMap<String, String>();
		Map<String, String> mappedWarnings = warningModelMap.containsKey(name) ? warningModelMap.get(name)
				: new HashMap<String, String>();

		if (mappedErrors.isEmpty() && mappedWarnings.isEmpty()) {
			throw new ApplicationErrorException("No hay errores y/o warnings definidos para la transaccion " + name);
		}

		List<ApplicationError> errors = new ArrayList<ApplicationError>();
		List<ApplicationWarning> warnings = new ArrayList<ApplicationWarning>();
		boolean isThereError = false;
		String code;

		// Obtendra la descripcion de cada error o warning. Si hay al menos un error, luego ignora cualquier warning
		// ya que un error anula cualquier warning.
		for (ErrorCode e : status.getErrorCodes()) {
			code = e.getErrorCode();
			if (mappedErrors.containsKey(code)) {
				if (!isThereError)
					isThereError = true;
				if (!mappedUserErrors.containsKey(code))
					errors.add(new ApplicationError(code, code + " - " + mappedErrors.get(code)));
				else
					errors.add(new ApplicationError(code, code + " - " + mappedErrors.get(code), code + " - "
							+ mappedUserErrors.get(code)));
			} else if (mappedWarnings.containsKey(code)) {
				if (isThereError)
					continue;
				warnings.add(new ApplicationWarning(code + " - " + mappedWarnings.get(code)));
			} else {
				// No hay una descripcion asociada al codigo de error se lo considera un error...
				if (!isThereError)
					isThereError = true;
				errors.add(new ApplicationError(code, "Error " + code + " sin mensaje definido en la transaccion "
						+ name));
			}
		}

		if (!errors.isEmpty()) {
			ApplicationErrorException e = new ApplicationErrorException("Hay errores en la transaccion " + name);
			e.setErrors(errors);
			throw e;
		}

		if (!warnings.isEmpty()) {
			ApplicationWarningException e = new ApplicationWarningException("Hay warnings en la transaccion " + name);
			e.setWarnings(warnings);
			throw e;
		}
	}

	/**
	 * @see CmError#trOk(Common, String, String, String)
	 * 
	 * @deprecated Ver su reemplazo en checkTransaction
	 * @see CmError#checkTransaction(String, TransactionStatus)
	 * 
	 */
	public static boolean trOk(Common cm, String name, String okFlag, Long errCode) {
		// Pasa errCode a String de longitud 3 complentandolo con "0" por delante. Y luego invoca a trOk donde errCode
		// es String. O sea si erroCode es 1, lo pasa a "001" e invoca a la version con errCode String.
		String str = "000" + errCode.toString();
		String pad = str.substring(str.length() - 3);
		return CmError.trOk(cm, name, okFlag, pad);
	}

	/**
	 * @see CmError#trOk(MultipleErrorsTransaction, String, String, String)
	 * 
	 * @deprecated Ver su reemplazo en checkTransaction
	 * @see CmError#checkTransaction(String, TransactionStatus)
	 */
	public static boolean trOk(MultipleErrorsTransaction cm, String name, String okFlag, Long errCode) {
		String str = "000" + errCode.toString();
		String pad = str.substring(str.length() - 3);
		return CmError.trOk((MultipleErrorsTransaction) cm, name, okFlag, pad);
	}

	/**
	 * @see CmError#trOk(MultipleErrorsTransaction, String, String, String)
	 * 
	 * @deprecated Ver su reemplazo en checkTransaction
	 * @see CmError#checkTransaction(String, TransactionStatus)
	 */
	public static boolean trOk(MultipleErrorsTransaction cm, String name, String okFlag) {
		return CmError.trOk((MultipleErrorsTransaction) cm, name, okFlag, (String) null);
	}

	/**
	 * Obtiene la descripci�n de un error de sistema
	 * 
	 * @param codigoError
	 *            C�digo de error
	 * @return Descripci�n del error
	 */
	public static String errorString(int codigoError) {
		return errstrMap.get(codigoError);
	}

}
