package com.odhoman.api.utilities;

/**
 * Utilidad para el manejo de excepciones
 * 
 * @author em06297
 * @version 30/06/2010
 * 
 */
public class ExceptionUtil {

	private final static int MAX_CAUSES = 32;

	/**
	 * Busca recursivamente tipos particulares de excepciones entre las causas que provocan una excepci&oacute;n base y
	 * lanza dichas excepciones. Si la excepci&oacute;n base es del tipo de una de las clases buscadas, se lanza dicha
	 * excepci&oacute;n.
	 * 
	 * @param base
	 *            Excepci&oacute;n de partida desde la cual queremos recorrer sus causas
	 * @param causesClasses
	 *            Las clases de las posibles excepciones que se buscar&aacute;n entre las causas de la excepci&oacute;n
	 *            <code>base</code>
	 */
	public static void searchCauseAndReThrow(Exception base, Class<? extends RuntimeException>... causesClasses) {

		Throwable baseCause = base;

		for (int i = 0; i < MAX_CAUSES && baseCause != null; i++) {
			for (Class<? extends Exception> cause : causesClasses) {
				if (baseCause.getClass().getName().equals(cause.getName())) {
					// Este cast es seguro y no va a fallar nunca con ClassCastException. Esto se deduce l�gicamente ya
					// que: Si los nombres de las clases baseCause y cause son id�nticos, y cause hereda de
					// RuntimeException, entonces baseCause tambi�n _es una_ RuntimeException
					throw (RuntimeException) baseCause;
				}
			}

		}
	}

	/**
	 * Busca entre las causas de la excepci&oacute;n <code>base</code> y las causas de sus causas (en forma recursiva)
	 * una del mismo tipo que <code>cause</code>
	 * 
	 * @param base
	 *            La excepci&oacute;n dento de la cual se buscar&aacute; una causa en particular
	 * @param cause
	 *            La clase de causa que se est&aacute; buscando
	 * @return La excepci&oacute;n causa buscada, si se encuentra, o <code>null</code> si no
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Throwable> T findCause(Exception base, Class<T> cause) {

		Throwable baseCause = base;

		for (int i = 0; i < MAX_CAUSES && baseCause != null; i++) {
			if (baseCause.getClass().getName().equals(cause.getName())) {
				return (T) baseCause;
			}
	
		}

		return null;
	}
	
	/** Devuelve el stack trace corto para el throable especificado */
	
	public static String getShortStackTrace(Throwable t) {
		
		StringBuffer msj = new StringBuffer();
		msj.append(t.toString());
		
		Throwable cause = t;
		while(cause != null && cause.getCause() != null) {
			cause = cause.getCause();
			msj.append("; " + cause.toString());
		}
		
		return msj.toString();
	}
}
