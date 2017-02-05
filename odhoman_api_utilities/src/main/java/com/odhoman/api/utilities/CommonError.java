package com.odhoman.api.utilities;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

/**
 * Clase de constantes de errores y mensajes.
 * Por convencion errores de systema tiene codigos menores que 100
 * Los errores con prefijos deben tomar valores mayor o igual a 100. Y estan reservados para errores de la logica de la aplicacion.
*/
public class CommonError {
    private static HashMap errorModelMap = new HashMap();
    private static HashMap errstrMap = new HashMap();
    public static int E_SYS = -100; // de aqui hacia abajo se consideran errores de sistemas

    public static int E_WEB_REQ_PARAM = -99;
    public static int E_RESOURCE = -10;
    public static int E_REMOTE_CALL = -11;
    public static int E_DAO_SELECT = -110;
    public static int E_DAO_INSERT = -112;
    public static int E_DAO_UPDATE = -114;
    public static int E_DAO_DELETE = -116;
    public static int E_DAO_LOCK = -118;
    public static int E_ACCESS = -200;
    public static int E_LOCK_INFO = -300;

    
    static {
	/* errores criticos */
	errstrMap.put(new Integer(E_WEB_REQ_PARAM), "Parametros invalidos en request.");
	errstrMap.put(new Integer(E_RESOURCE), "Falla al intentar utilizar el recurso.");
	errstrMap.put(new Integer(E_REMOTE_CALL), "Se produjo un error en un componente remoto de aplicacion.");

	errstrMap.put(new Integer(E_DAO_SELECT), "Se produjo un error al intentar obtener registros de la DB.");
	errstrMap.put(new Integer(E_DAO_INSERT), "Se produjo un error al intentar insertar registros en la DB.");
	errstrMap.put(new Integer(E_DAO_UPDATE), "Se produjo un error al intentar modificar registros en la DB.");
	errstrMap.put(new Integer(E_DAO_DELETE), "Se produjo un error al intentar eliminar registros en la DB.");
	
        errstrMap.put(new Integer(E_DAO_LOCK), "-");
	errstrMap.put(new Integer(E_ACCESS), "No tiene permisos para realizar la operacion.");
	errstrMap.put(new Integer(E_LOCK_INFO), "No se encontro informacion del registro lock.");
        
	/* Errores del modelo */
	/* Login */
	addModelError("LOGIN", "001", "Usuario incorrecto");
        addModelError("LOGIN", "002", "Contrase&ntilde;a incorrecta");	
        addModelError("LOGIN", "003", "Contrase&ntilde; vencida");
        addModelError("LOGIN", "004", "Perfil Inhabilitado");
        addModelError("LOGIN", "005", "Fallo el Login");
        addModelError("LOGIN", "006", "Usuario sin perfiles asignados");
        addModelError("LOGIN", "007", "Debe Cambiar su Clave");
        addModelError("LOGIN", "008", "Usuario deshabilitado");
        addModelError("LOGIN", "009", "Ha excedido el n�mero de intentos");
        /* changePasswd */
        addModelError("changePasswd", "001", "No puede cambiarse el perfil de usuario del sistema");
        addModelError("changePasswd", "002", "La contrase&ntilde;a nueva no puede ser la misma que la contrase&ntilde;a actual");
        addModelError("changePasswd", "003", "El sistema detect� errorres en el mandato");
        addModelError("changePasswd", "004", "La contrase&ntilde;a no cumple las reglas de contrase&ntilde;as.");
        addModelError("changePasswd", "005", "La contrase&ntilde;a no cumple con el m�nimo de caracteres requeridos");
        addModelError("changePasswd", "006", "La contrase&ntilde;a excede el m�ximo de caracteres permitidos");
        addModelError("changePasswd", "007", "La contrase&ntilde;a coincide con una de las 32 contrase&ntilde;as anteriores");
        addModelError("changePasswd", "008", "La contrase&ntilde;a contiene caracteres invalidos");
        addModelError("changePasswd", "009", "La contrase&ntilde;a contiene dos n�meros uno junto al otro");
        addModelError("changePasswd", "010", "La contrase&ntilde;a contiene un caracter utilizado m�s de una vez");
        addModelError("changePasswd", "011", "Hay un mismo caracter en la misma posici�n que en la contrase&ntilde;a anterior");
        addModelError("changePasswd", "012", "La contrase&ntilde;a debe contener un n�mero");
        addModelError("changePasswd", "013", "La contrase&ntilde;a contiene un caracter repetido consecutivamente");
        addModelError("changePasswd", "014", "La contrase&ntilde;a no puede ser la misma que el ID del usuario");
        addModelError("changePasswd", "015", "No se ha encontrado el programa de aprobaci�n de contrase&ntilde;as");
        addModelError("changePasswd", "016", "No se permite utilizar el programa de aprobaci�n de contrase&ntilde;as.");
        addModelError("changePasswd", "017", "Los par�metros en el programa de aprobaci�n de contase&ntilde;as no son correctos");
        addModelError("changePasswd", "018", "Contrase&ntilde;a incorrecta para el perfil del usuario");
        addModelError("changePasswd", "019", "El perfil del usuario est� inhabilitado");
        addModelError("changePasswd", "020", "No se permite el valor de la nueva contrase&ntilde;a.");
        addModelError("changePasswd", "021", "La nueva contrase&ntilde;a no puede ser *NONE");
        addModelError("changePasswd", "022", "El perfil de usuario no es correcto");
        addModelError("changePasswd", "023", "No se ha podido asignar el perfil de usuario");
        addModelError("changePasswd", "024", "Se necesita autorizaci�n especial");
        addModelError("changePasswd", "025", "No se ha podido asignar objeto interno del sistema.");
        addModelError("changePasswd", "026", "Se necesita *SECADM para crear o cambiar perfiles de usuario");
        addModelError("changePasswd", "027", "CCSID fuera del rango valido");
        addModelError("changePasswd", "028", "La longitud especificada en el parametro no es v�lida");
        addModelError("changePasswd", "029", "No es v�lido el n�mero de par�metros introducidos para esta API.");
        addModelError("changePasswd", "030", "El valor literal no se puede cambiar");
        addModelError("changePasswd", "031", "El par�metro de c�digo de error no es v�lido");
        addModelError("changePasswd", "032", "No se ha encontrado el objeto en la liber�a");
        addModelError("changePasswd", "033", "No tiene autorizaci�n sobre el objeto");
        addModelError("changePasswd", "034", "No puede asignarse el objeto de la biblioteca");
        addModelError("changePasswd", "035", "No se puede asignar la biblioteca");
        addModelError("changePasswd", "036", "El programa o programa de servicio de la bilblioteca ha finalizado.");
        addModelError("changePasswd", "038", "Contactese sobre otros errores al admistrador");


	addModelError("SetDatosCliente", "004", "Cliente se esta duplicando");
	addModelError("SetDatosCliente", "006", "Los datos obligatorios no fuero enviados");
	addModelError("SetDatosCliente", "010", "Valores numericos en nombre o apellido");
	addModelError("SetDatosCliente", "011", "Palabra de un solo caracter");
	addModelError("SetDatosCliente", "012", "3 caracteres consecutivos");
	addModelError("SetDatosCliente", "014", "Documento menor 10000");
	addModelError("SetDatosCliente", "015", "Cliente con tramite en SFE");
	addModelError("SetDatosCliente", "016", "Conyuge con tramite en SFE");
	addModelError("SetDatosCliente", "017", "Casamiento con conyuges del mismo sexo");
	addModelError("SetDatosCliente", "018", "Cliente es referido de otro usuario");
	//addModelError("SetDatosCliente", "019", "Fecha invalida");  
        addModelError("SetDatosCliente", "019", "Mail incorrecto");
	addModelError("SetDatosCliente", "020", "Tipo de documento invalido");
	addModelError("SetDatosCliente", "021", "Identificaion de sexo invalido");
	addModelError("SetDatosCliente", "022", "Estado civil invalido");
	addModelError("SetDatosCliente", "023", "Codigo Provincia invalido");
	addModelError("SetDatosCliente", "024", "Codigo Pais invalido");

	addModelError("SetProducto", "109", "Falta codigo postal");
	addModelError("SetProducto", "110", "Codigo postal inexistente");
	addModelError("SetProducto", "111", "Falta codigo de Prvincia");
	addModelError("SetProducto", "112", "Codigo de Provincia inexistente");
	addModelError("SetProducto", "113", "Codigo de Provincia no se corresponde con codigo postal");
	addModelError("SetProducto", "114", "Codigo area no se corresponde con codigo postal");
	addModelError("SetProducto", "115", "Codigo de area erroneo");
	addModelError("SetProducto", "116", "Nro telefono erroneo");
	addModelError("SetProducto", "117", "Fecha informada invalidad");
	addModelError("SetProducto", "118", "Cuenta no pertenece a Diners");
	addModelError("SetProducto", "119", "No se pudo determinar Relacion cliente");
	addModelError("SetProducto", "120", "No puede dar de alta una cuenta ya existente");

		addModelError("AltaDeCliente", "001", "SUCURSAL INV&Aacute;LIDA");
	    addModelError("AltaDeCliente", "002", "FECHA DE ENTRADA AL SISTEMA INV&Aacute;LIDA");
	    addModelError("AltaDeCliente", "003", "APELLIDO NO INFORMADO");
	    addModelError("AltaDeCliente", "004", "NOMBRE NO INFORMADO");
	    addModelError("AltaDeCliente", "005", "TIPO DE DOCUMENTO INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "006", "NRO DE DOCUMENTO NO INFORMADO CON TIPO DE DOC");
	    addModelError("AltaDeCliente", "007", "NRO DE C&Eacute;DULA MENOR A 0");
	    addModelError("AltaDeCliente", "008", "EXPEDIDO POR DE C&Eacute;DULA INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "009", "PAIS DE ORIGEN DEL PASAPORTE INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "010", "CUIT INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "011", "RAZ&Oacute;N DE NO INSCRIPTO CON CARACTERES INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "012", "NACIONALIDAD INV&Aacute;LIDA");
	    addModelError("AltaDeCliente", "013", "FECHA DE NACIMIENTO INV&Aacute;LIDA");
	    addModelError("AltaDeCliente", "014", "LUGAR DE NACIMIENTO INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "015", "C&Oacute;DIGO DE SEXO INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "016", "C&Oacute;DIGO DE ESTADO CIVIL INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "017", "CANTIDAD DE HIJOS MENOR A 0");
	    addModelError("AltaDeCliente", "018", "CANTIDAD DE PERSONAS DEPENDIENTES MENOR A 0");
	    addModelError("AltaDeCliente", "019", "C&Oacute;NYUGE INEXISTENTE");
	    addModelError("AltaDeCliente", "020", "NOMBRE DE PADRE CON CARACTERES INV&Aacute;LIDOS");
	    addModelError("AltaDeCliente", "021", "NOMBRE DE MADRE CON CARACTERES INV&Aacute;LIDOS");
	    addModelError("AltaDeCliente", "022", "MARCA DE STAFF INV&Aacute;LIDA");
	    addModelError("AltaDeCliente", "023", "NUMER&Oacute; DE LEGAJO INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "024", "MARCA DE EMPLEADOR INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "025", "SITUACI&Oacute;N FISCAL INV&Aacute;LIDA");
	    addModelError("AltaDeCliente", "026", "SITUACI&Oacute;N FRENTE AL IVA INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "027", "C&Oacute;DIGO DE BCRA INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "028", "TIPO DE CONTRIBUYENTE INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "029", "MARCA DE PRESENTACION DE ULTIMO PAGO INVALIDA");
	    addModelError("AltaDeCliente", "030", "MARCA DE CLIENTE INVALIDO");
	    addModelError("AltaDeCliente", "031", "USUARIO INGRESANTE NO INFORMADO");
	    addModelError("AltaDeCliente", "032", "NUMERO DE CLIENTE INEXISTENTE");
	    addModelError("AltaDeCliente", "033", "CLIENTE BLOQUEADO POR OTRA APLICACION");
	    addModelError("AltaDeCliente", "034", "NO FUE INFORMADO NING&Uacute;N DOCUMENTO");
	    addModelError("AltaDeCliente", "035", "CLIENTE PRINCIPAL Y CONYGUE DEL MISMO SEXO");
	    addModelError("AltaDeCliente", "036", "CLIENTE PRINCIPAL CASADO CON OTRO CLIENTE");
	    addModelError("AltaDeCliente", "037", "CLIENTE CONYUGE CASADO CON OTRO CLIENTE");
	    addModelError("AltaDeCliente", "038", "CLIENTE DUPLICADO");
	    addModelError("AltaDeCliente", "039", "ERROR EN AUTORIZACION DE PROSPECT");
	    addModelError("AltaDeCliente", "040", "ERROR EN MODIFICACION ESTADO PROSPECT");
	    addModelError("AltaDeCliente", "041", "SUCURSAL INV&Aacute;LIDA");
	    addModelError("AltaDeCliente", "042", "FECHA DE ENTRADA AL SISTEMA INV&Aacute;LIDA");
	    addModelError("AltaDeCliente", "043", "APELLIDO NO INFORMADO");
	    addModelError("AltaDeCliente", "044", "NOMBRE NO INFORMADO");
	    addModelError("AltaDeCliente", "045", "TIPO DE DOCUMENTO INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "046", "NRO DE DOCUMENTO NO INFORMADO CON TIPO DE DOC");
	    addModelError("AltaDeCliente", "047", "NRO DE C&Eacute;DULA MENOR A 0");
	    addModelError("AltaDeCliente", "048", "EXPEDIDO POR DE C&Eacute;DULA INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "049", "PAIS DE ORIGEN DEL PASAPORTE INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "050", "CUIT INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "051", "RAZ&Oacute;N DE NO INSCRIPTO CON CARACTERES INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "052", "NACIONALIDAD INV&Aacute;LIDA");
	    addModelError("AltaDeCliente", "053", "FECHA DE NACIMIENTO INV&Aacute;LIDA");
	    addModelError("AltaDeCliente", "054", "LUGAR DE NACIMIENTO INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "055", "C&Oacute;DIGO DE SEXO INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "056", "C&Oacute;DIGO DE ESTADO CIVIL INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "057", "CANTIDAD DE HIJOS MENOR A 0");
	    addModelError("AltaDeCliente", "058", "CANTIDAD DE PERSONAS DEPENDIENTES MENOR A 0");
	    addModelError("AltaDeCliente", "059", "C&Oacute;NYUGE INEXISTENTE");
	    addModelError("AltaDeCliente", "060", "NOMBRE DE PADRE CON CARACTERES INV&Aacute;LIDOS");
	    addModelError("AltaDeCliente", "061", "NOMBRE DE MADRE CON CARACTERES INV&Aacute;LIDOS");
	    addModelError("AltaDeCliente", "062", "MARCA DE STAFF INV&Aacute;LIDA");
	    addModelError("AltaDeCliente", "063", "NUMER&Oacute; DE LEGAJO INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "064", "MARCA DE EMPLEADOR INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "065", "SITUACI&Oacute;N FISCAL INV&Aacute;LIDA");
	    addModelError("AltaDeCliente", "066", "SITUACI&Oacute;N FRENTE AL IVA INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "067", "C&Oacute;DIGO DE BCRA INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "066", "TIPO DE CONTRIBUYENTE INV&Aacute;LIDO");
	    addModelError("AltaDeCliente", "069", "MARCA DE PRESENTACION DE ULTIMO PAGO INVALIDA");
	    addModelError("AltaDeCliente", "070", "MARCA DE CLIENTE INVALIDO");
	    addModelError("AltaDeCliente", "071", "USUARIO INGRESANTE NO INFORMADO");
	    addModelError("AltaDeCliente", "072", "NUMERO DE CLIENTE INEXISTENTE");
	    addModelError("AltaDeCliente", "073", "CLIENTE BLOQUEADO POR OTRA APLICACION");
	    addModelError("AltaDeCliente", "074", "NO FUE INFORMADO NING&Uacute;N DOCUMENTO");
	    addModelError("AltaDeCliente", "075", "CLIENTE PRINCIPAL Y CONYGUE DEL MISMO SEXO");
	    addModelError("AltaDeCliente", "076", "CLIENTE PRINCIPAL CASADO CON OTRO CLIENTE");
	    addModelError("AltaDeCliente", "077", "CLIENTE CONYUGE CASADO CON OTRO CLIENTE");
	    addModelError("AltaDeCliente", "078", "CLIENTE DUPLICADO");
	    addModelError("AltaDeCliente", "079", "ERROR EN AUTORIZACION DE PROSPECT");
	    addModelError("AltaDeCliente", "080", "ERROR EN MODIFICACION ESTADO PROSPECT");
	    addModelError("AltaDeCliente", "081", "NO SE PERMITE LA MODIFICACI�N. EL CLIENTE NO ES STAND ALONE DE SEGUROS.");
	    addModelError("AltaDeCliente", "082", "NO SE PUDO DAR DE ALTA EL CLIENTE.");	
	    addModelError("AltaDeCliente", "083", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE TEL�FONO: USUARIO INGRESANTE NO INFORMADO");
	    addModelError("AltaDeCliente", "084", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE TEL�FONO: N�MERO DE CLIENTE INEXISTENTE");
	    addModelError("AltaDeCliente", "085", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE TEL�FONO: CLIENTE BLOQUEADO POR OTRA APLICACI�N");
	    addModelError("AltaDeCliente", "086", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE TEL�FONO: REGISTRO INEXISTENTE PARA MODIFICACI�N O BAJA");
	    addModelError("AltaDeCliente", "087", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE TEL�FONO: NO ENVIAR SECUENCIA PARA ALTAS");
	    addModelError("AltaDeCliente", "088", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE TEL�FONO: TEL�FONO NO INFORMADO");
	    addModelError("AltaDeCliente", "089", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE TEL�FONO: �REA DE TEL�FONO INV�LIDO");
	    addModelError("AltaDeCliente", "090", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE TEL�FONO: N�MERO DE TEL�FONO INV�LIDO");
	    addModelError("AltaDeCliente", "091", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE TEL�FONO: C�DIGO DE TIPO DE TEL�FONO INV�LIDO");
	    addModelError("AltaDeCliente", "092", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE TEL�FONO: C�DIGO DE TIPO DE CONTACTO INV�LIDO");
	    addModelError("AltaDeCliente", "093", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE TEL�FONO: TIPO DE SOLICITUD INV�LIDA");
	    addModelError("AltaDeCliente", "094", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE TEL�FONO: USUARIO AUTORIZANTE DEBE SER DIFERENTE AL INGRESANTE");
	    addModelError("AltaDeCliente", "095", "CLIENTE DADO DE ALTA CON �XITO. NO SE PUDO DAR DE ALTA EL PRIMER NRO TELEFONICO");	
	    addModelError("AltaDeCliente", "096", "CLIENTE DADO DE ALTA CON �XITO. NO SE PUDO DAR DE ALTA EL SEGUNDO NRO TELEFONICO");	
	    addModelError("AltaDeCliente", "097", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE E-MAIL:	USUARIO INGRESANTE NO INFORMADO");
	    addModelError("AltaDeCliente", "098", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE E-MAIL:	NUMERO DE CLIENTE INEXISTENTE");
	    addModelError("AltaDeCliente", "099", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE E-MAIL:	REGISTR INEXIST.P/MODIF.O BAJA");
	    addModelError("AltaDeCliente", "100", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE E-MAIL:	NO ENVIAR SECUENCIA PARA ALTAS");
	    addModelError("AltaDeCliente", "101", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE E-MAIL:	TIPO DE SOLICITUD INVALIDA");
	    addModelError("AltaDeCliente", "102", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE E-MAIL:	USER AUTORIZ.IGUAL A INGRESAN");
	    addModelError("AltaDeCliente", "103", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE E-MAIL:	TIPO DE DOMICILIO INV�LIDO");
	    addModelError("AltaDeCliente", "104", "CLIENTE DADO DE ALTA CON �XITO. FALL� EL ALTA DE E-MAIL:	E-MAIL INCORRECTO");
	    addModelError("AltaDeCliente", "105", "CLIENTE DADO DE ALTA CON �XITO. NO SE PUDO DAR DE ALTA EL E-MAIL");	



    }

    public static String errorString(int err) {
	return (String) errstrMap.get((Object) new Integer(err));
    }

    public static String errorMessage(int err, String extra) {
        String str = errorString(err);
        if (str == null) {
	  return extra;
	} // end of if ()
	
	return errorString(err) + extra + " (" + err + ")";
    }

    public static String getStackTrace(Throwable exception)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return sw.toString();
    }
    
    public static void addModelError(String nameModel, String codeError, String descError) {
		HashMap hm = (HashMap) errorModelMap.get(nameModel);
		if (hm == null) {
		    hm = new HashMap();
		    errorModelMap.put(nameModel, hm); 
		}
		hm.put(codeError, descError);
    }
    
    public static String getModelError(String nameModel, String codeError) {
		HashMap hm = (HashMap) errorModelMap.get(nameModel);
		String ret = null;
	
	    // Agregado para  el caso en que no hay error y para errores del sistema
	    if (codeError.equals("0"))
	    	return "";
	    else if (new Integer(codeError).intValue()<E_SYS) 
	    	return errorString(new Integer(codeError).intValue());
        
		if (hm == null) {
		    return "Clase: " + nameModel + " Codigo: (" + codeError + ")";
		}
		ret = (String) hm.get( codeError);
		if (ret == null) {
		    return "Clase: " + nameModel + " Codigo: (" + codeError + ")";
		}
		return ret + " - Codigo de mensaje: " + codeError;
    }

    public static boolean trOk(Common cm, String name, String okFlag, String errCode) {	
	System.out.println("CommonError.trOk(): okFlag=" + okFlag + " errCode=" + errCode);
	if (okFlag.equals("N")) {
	    if (!"".equals(errCode)) {
                cm.setCmErrCode(new Long(errCode));
                cm.setCmErrDescription(CommonError.getModelError(name, errCode));
            } else {
                cm.setCmErrCode(new Long(-1));
                cm.setCmErrDescription("La transaccion no indica el codigo de error.");
            }
	    return false;
	}
	return true;
    }

    public static boolean trOk(Common cm, String name, String okFlag, Long errCode) {	
    	return CommonError.trOk(cm,name,okFlag,errCode.toString());	
    }

}
