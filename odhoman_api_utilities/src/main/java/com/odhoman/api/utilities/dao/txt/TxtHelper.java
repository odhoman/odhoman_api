package com.odhoman.api.utilities.dao.txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.odhoman.api.utilities.GeneralDataItem;
import com.odhoman.api.utilities.config.AbstractConfig;

public class TxtHelper {
	
	private String pathFiles = "";

	private Logger logger = null;

	public TxtHelper() {
		super();
	}

	/**
	 * Recibe un objeto de configuracion
	 */
	public void setConfigure(AbstractConfig config, String tablesPathId) {
		//TODO Controlar que pueden ocurrir errores.
		this.logger = config.getLogger();
		this.pathFiles = config.getString(tablesPathId);

	}

	public Collection<GeneralDataItem> getSimpleCol(String fileName, int codFrom, int codLength, int descFrom, int descLength)
			throws TxtHelperException {
		return getSimpleCol(fileName, codFrom, codLength, descFrom, descLength, false);
	}

	/**
	 * getSimpleCol() Lee el archivo de texto pasado como parametro. Devuelve una coleccion de objetos GeneralDataItem
	 * 
	 */
	public Collection<GeneralDataItem> getSimpleCol(String fileName, int codFrom, int codLength, int descFrom, int descLength,
			boolean canRepeat) throws TxtHelperException {
		Collection<GeneralDataItem> localCol = new LinkedList<GeneralDataItem>();
		String thisLine;

		String strCode = "";
		String strDescription = "";

		HashMap<String, String> codigosProcesados = new HashMap<String, String>();

		try {

			FileInputStream fin = new FileInputStream(pathFiles + fileName);
			BufferedReader myInput = new BufferedReader(new InputStreamReader(fin));

			// 
			while ((thisLine = myInput.readLine()) != null) {

				// toma el codigo y descripcion
				strCode = getSubStr(thisLine, codFrom, codLength);
				strDescription = getSubStr(thisLine, descFrom, descLength);

				// si no existe, lo agrega a la coleccion (a menos que querramos repetir claves expl�citamente con lo
				// cual ni verificamos)
				if (canRepeat || (codigosProcesados.get(strCode) == null)) {

					// instancia un GeneralDataItem
					GeneralDataItem gdi = new GeneralDataItem(strCode, strDescription);

					// lo agrega a la coleccion
					localCol.add(gdi);

					// agrega al string de control
					codigosProcesados.put(strCode, "OK");
				}
			}

		} catch (Exception e) {
			logger.error("Error en la lectura del archivo: " + fileName + "\n");
			// TxtHelperException gtEx = new TxtHelperException("Error en la lectura del archivo: " + fileName);
			TxtHelperException gtEx = new TxtHelperException(e);
			throw gtEx;
		}
		return localCol;
	}
	
	
	public Collection<GeneralDataItem> getSimpleColFiltered(String fileName, int codFrom, int codLength, int descFrom, int descLength,
			boolean canRepeat, String code) throws TxtHelperException
			{	
		Collection<GeneralDataItem> listaFinal = new LinkedList<GeneralDataItem>();
				Collection<GeneralDataItem> lista = getSimpleCol(fileName, codFrom, codLength, descFrom, descLength, canRepeat);
				for(GeneralDataItem gdi: lista)
				{
					if(gdi.getCode().equals(code))
						listaFinal.add(gdi);
				}
				return listaFinal;
			}
	

	/**
	 * getSimpleColByCode() Lee el archivo de texto pasado como parametro. Devuelve una coleccion de objetos
	 * GeneralDataItem relacionados con el codigo informado
	 * 
	 */
	public Collection<GeneralDataItem> getSimpleColByCode(String fileName, int codFrom, int codLength, String codValue, int subCodFrom,
			int subCodLength, int subDescFrom, int subDescLength) throws TxtHelperException {
		Collection<GeneralDataItem> localCol = new LinkedList<GeneralDataItem>();
		String thisLine;

		String strCode = "";

		String strSubCode = "";
		String strSubDescription = "";

		String strCodes = "";

		try {

			FileInputStream fin = new FileInputStream(pathFiles + fileName);
			BufferedReader myInput = new BufferedReader(new InputStreamReader(fin));

			// 
			while ((thisLine = myInput.readLine()) != null) {

				// toma el codigo y descripcion
				strCode = getSubStr(thisLine, codFrom, codLength);

				// si el codigo leido coincide con el valor solicitado
				if (strCode.equals(codValue)) {

					// toma el subcodigo
					strSubCode = getSubStr(thisLine, subCodFrom, subCodLength);
					strSubDescription = getSubStr(thisLine, subDescFrom, subDescLength);

					// si no existe, lo agrega a la coleccion
					if (strCodes.indexOf("<" + strSubCode + ">") == -1) {

						// instancia un GeneralDataItem
						GeneralDataItem gdi = new GeneralDataItem(strSubCode, strSubDescription);

						// lo agrega a la coleccion
						localCol.add(gdi);

						// agrega al string de control
						strCodes += "<" + strSubCode + ">";
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error en la lectura del archivo: " + fileName + "\n");
			TxtHelperException gtEx = new TxtHelperException("Error en la lectura del archivo: " + fileName);
			throw gtEx;
		}
		return localCol;
	}

	/**
	 * getSimpleColByCode() Lee el archivo de texto pasado como parametro. Devuelve una coleccion de objetos
	 * GeneralDataItem relacionados con el codigo informado
	 * 
	 */
	public Collection<GeneralDataItem> getDobleCol(String fileName, int codFrom, int codLength, int subCodFrom, int subCodLength,
			int subDescFrom, int subDescLength) throws TxtHelperException {
		Collection<GeneralDataItem> localCol = new LinkedList<GeneralDataItem>();
		String thisLine;

		String strCode = "";
		String strSubCode = "";
		String strSubDescription = "";

		try {

			FileInputStream fin = new FileInputStream(pathFiles + fileName);
			BufferedReader myInput = new BufferedReader(new InputStreamReader(fin));

			// 
			while ((thisLine = myInput.readLine()) != null) {

				// toma el codigo y descripcion
				strCode = getSubStr(thisLine, codFrom, codLength);

				// toma el subcodigo
				strSubCode = getSubStr(thisLine, subCodFrom, subCodLength);
				strSubDescription = getSubStr(thisLine, subDescFrom, subDescLength);

				// instancia un GeneralDataItem
				GeneralDataItem gdi = new GeneralDataItem(strSubCode, strSubDescription, strCode);

				// lo agrega a la coleccion
				localCol.add(gdi);

			}
		} catch (Exception e) {
			logger.error("Error en la lectura del archivo: " + fileName + "\n");
			TxtHelperException gtEx = new TxtHelperException("Error en la lectura del archivo: " + fileName, e);
			throw gtEx;
		}
		return localCol;
	}

	/**
	 * getSubStr() Devuelve el substring considerando que la linea puede ser menor que lo esperado
	 * 
	 */
	private String getSubStr(String txtLine, int ssFrom, int ssLength) throws Exception {
		String subStr = "";
		for (int i = ssFrom; i < txtLine.length() && i < (ssFrom + ssLength); i++) {
			subStr += txtLine.substring(i, i + 1);
		}
		return subStr;
	}

	/**
	 * Lectura s�per eficiente de insptabs. ��Atenci�n!! Solo sirve para leer insptabs de ancho fijo!!
	 * 
	 * @author Emilio M�ller (em06297)
	 * 
	 * @param fileName
	 *            Nombre del archivo
	 * @param startCodePos
	 *            Posici�n de cominezo del c�digo
	 * @param codeLenght
	 *            Longitud del c�digo
	 * @param startDescPos
	 *            Posici�n de comienzo de la descripci�n
	 * @param descLength
	 *            Longitud de fin de la descripci�n
	 * @return Una colecci�n de GeneralDataItem con los c�digos y descripciones le�das del archivo <code>fileName</code>
	 * @throws TxtHelperException
	 *             Si ocurre un problema con el archivo
	 */
	public Collection<GeneralDataItem> fastReadTable(String fileName, int startCodePos, int codeLenght, int startDescPos, int descLength)
			throws TxtHelperException {

		Collection<GeneralDataItem> tabla = new ArrayList<GeneralDataItem>();

		// Creamos el archivo y el flujo para leerlo
		File file = new File(pathFiles + fileName);

		try {
			FileReader reader = new FileReader(file);
			int fileSize = (int) file.length();

			// Alocamos el espacio necesario para un buffer del tama�o del
			// archivo completo y leemos todo el archivo de un solo saque en �l
			char[] buffer = new char[fileSize];
			reader.read(buffer);

			// Buscamos el fin de l�nea
			int lineLength = 0;
			for (; buffer[lineLength] != '\n'; lineLength++)
				;
			lineLength++;

			// Parseamos el buffer de a l�neas, obteniendo el c�digo y descripci�n
			// de acuerdo a los �ndices y longitudes suministrados
			int totalLines = fileSize / lineLength;
			for (int line = 0; line < totalLines; line++) {

				GeneralDataItem item = new GeneralDataItem(new String(buffer, (line * lineLength) + startCodePos,
						codeLenght), new String(buffer, (line * lineLength) + startDescPos, descLength));

				tabla.add(item);
			}

		} catch (Exception e) {
			logger.error("Error en la lectura del archivo: " + fileName + "\n");
			throw new TxtHelperException("Error en la lectura del archivo: " + fileName + ". Excepci�n: " +e);
		}
		return tabla;
	}

}
