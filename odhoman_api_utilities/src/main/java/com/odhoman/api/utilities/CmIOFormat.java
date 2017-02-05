package com.odhoman.api.utilities;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import com.odhoman.api.utilities.transac.ApplicationErrorException;

public class CmIOFormat {

	public CmIOFormat() {
	}

	public static Long getLong(String s) {
		s = getString(s);
		
		if (s.equals("")){
			return 0L;
		}
		
		return new Long(s);
	}

	public static Long getLong(String s, int strSize) {
		s = getString(s, strSize);
		
		if (s.equals("")){
			return 0L;
		}

		return new Long(s);
	}

	public static String getString(String s) {
		if (s == null)
			s = "";
		return s.trim();
	}

	public static String getString(String s, int strSize) {
		if (s == null)
			s = "";
		else if (s.length() > strSize)
			s = s.substring(0, strSize);
		return s.trim();
	}

	/**
	 * Parsea un double contenido en un string
	 * 
	 * @param number
	 *            El n&uacute;mero que se quiere parsear
	 * @param decimalSeparator
	 *            El separador decimal
	 * @return Un double
	 */
	public static Double getDouble(String number, char decimalSeparator) {
		try {
			String format = "0" + decimalSeparator + "0";

			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setDecimalSeparator(decimalSeparator);

			return new DecimalFormat(format, symbols).parse(number).doubleValue();
		} catch (ParseException e) {
			return 0.0D;
		}
	}

	public static Double getDouble(String number, char decimalSeparator, Locale locale) {
		try {
			String format = "0" + decimalSeparator + "0";

			DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
			symbols.setDecimalSeparator(decimalSeparator);

			return new DecimalFormat(format, symbols).parse(number).doubleValue();
		} catch (ParseException e) {
			return 0.0D;
		}
	}

	/**
	 * Parsea un double contenido en un string
	 * 
	 * @param number
	 *            El double que se quiere parsear
	 * @return El double contenido en <code>number</code>. Se utilizar&aacute; como separador decimal el predeterminado
	 *         por el locale actual
	 */
	public static Double getDouble(String number) {
		return getDouble(number, new DecimalFormatSymbols().getDecimalSeparator());
	}

	// si s es cadena vacia, retorna 0
	public static Long getLDate(String s) {
		Long ret = null;
		int t0 = 0, t1 = 0;
		String ymd = "", d = "", m = "", y = "";

		// si cadena vacia, entonces convertir a 0
		if (s.trim().length() == 0)
			return new Long(0);

		t0 = s.indexOf("/", 0);
		t1 = s.indexOf("/", t0 + 1);
		d = s.substring(0, t0);
		if (d.length() < 2)
			d = "0" + d;
		m = s.substring(t0 + 1, t1);
		if (m.length() < 2)
			m = "0" + m;
		y = s.substring(t1 + 1, s.length());
		ymd = y + m + d;
		ret = new Long(ymd);
		return ret;
	}

	// si s es cadena vacia, retorna 0
	public static Long getLShortDate(String s) {
		Long ret = null;
		int t0 = 0;
		String ym = "", m = "", y = "";

		// si cadena vacia, entonces convertir a 0
		if (s.trim().length() == 0)
			return new Long(0);

		t0 = s.indexOf("/", 0);

		m = s.substring(0, t0);
		if (m.length() < 2)
			m = "0" + m;
		y = s.substring(t0 + 1, s.length());
		ym = y + m;
		ret = new Long(ym);
		return ret;
	}

	// si v es 0 devuelve string vacio
	// sino trata de convertir a mm/yyyy
	public static String LShortDate2Str(Long v) {
		String ym = "", m = "", y = "";

		if (v.longValue() == 0)
			return "";
		ym = v.toString();
		if (ym.length() != 6)
			return "//";
		y = ym.substring(0, 4);
		m = ym.substring(4, 6);

		return m + "/" + y;
	}

	/** retorna un long en fomato MMYY a partir de una cadena en formato mm/yy */
	public static Long getLongMMYY(String s) {
		Long ret = null;
		int t1 = 0;
		String my = "", m = "", y = "";

		// si cadena vacia, entonces convertir a 0
		if (s.trim().length() == 0)
			return new Long(0);

		t1 = s.indexOf("/", 0);
		m = s.substring(0, t1);
		if (m.length() < 2)
			m = "0" + m;
		y = s.substring(t1 + 1, s.length());
		my = m + y;
		ret = new Long(my);
		return ret;
	}

	// si s es cadena vacia, retorna 0
	public static Long getLTime(String s) throws Exception {
		int i = 0;
		String hour = "";
		String minute = "";
		Long ret = null;

		s = s.trim();
		if (s.length() == 0)
			return new Long(0);

		i = s.indexOf(":");
		if (i == 0 || (i == -1 && s.length() != 4))
			throw new Exception("Formato de hora invalido HH:MM o HHMM");

		if (i == -1) {
			hour = s.substring(0, 2);
			minute = s.substring(2, 4);
		} else {
			hour = s.substring(0, i);
			minute = s.substring(i + 1, s.length());
			if (hour.length() == 1)
				hour = "0" + hour;
			if (minute.length() == 1)
				minute = "0" + minute;
		}
		ret = new Long(hour + minute);
		return ret;
	}

	public static String Long2Str(Long v) {
		if (v == null)
			return "0";
		return v.toString();
	}

	public static String Long2StrE(Long v) {
		if (v.longValue() == 0)
			return "";
		return v.toString();
	}

	public static String Long2StrF(Long v) {
		String pretag = "";
		String postag = "";

		String strValue = "";
		DecimalFormat df = new DecimalFormat("#,###,###,###,###");
		strValue = df.format(v.longValue());
		if (v.longValue() < 0) {
			pretag = "<font color=\"red\" >";
			postag = "</font>";
		}
		strValue = "&nbsp;&nbsp;" + strValue;
		return pretag + strValue + postag;
	}
		
	public static String Double2StrForcedToCommas(Double doubleVal) {
		String strValue = "";
		DecimalFormat df = new DecimalFormat("############0.00");
		strValue = df.format(doubleVal.doubleValue());
		return getDoubleWithCommas(strValue);
	}	

	public static String Double2StrForcedToCommas(Double v, String pattern) {
		DecimalFormat df = new DecimalFormat(pattern);
		return getDoubleWithCommas(df.format(v.doubleValue()));
	}
	
	public static String getDoubleWithCommas(String v) {
		Map<Character, Character> m = new HashMap<Character, Character>();
		m.put('.', ',');
		return replaceCharacters(v, m);		
	}
	
	public static String replaceCharacters(String str, Map<Character,Character> replacements) {
		Set<Character> keys = replacements.keySet();
		String newStr = new String(str);
		for(Character s: keys) {
			newStr = newStr.replace(s, replacements.get(s));
		}
		return newStr;
	}
		
	public static String Double2String(Double doubleVal, boolean withSeparator) {
		DecimalFormat df = new DecimalFormat(
				withSeparator ? "###,###,###,###,###,###,###.####" : "#####################.####");
		return df.format(doubleVal != null ? doubleVal.doubleValue() : 0.0D);
	}

	public static String Double2Str(Double doubleVal) {
		return Double2Str(doubleVal, "############0.00");
	}

	public static String Double2Str(Double v, String pattern) {
		DecimalFormat df = new DecimalFormat(pattern, new DecimalFormatSymbols(Locale.ENGLISH));
		return df.format(v != null ? v.doubleValue() : 0.0D);
	}

	public static String Double2Str(Double v, String pattern, Locale locale) {
		DecimalFormat df = new DecimalFormat(pattern, new DecimalFormatSymbols(locale));
		return df.format(v != null ? v.doubleValue() : 0.0D);
	}

	public static String DoubleLarge2StrF(Double doubleVal) {

		String strValue = "";
		DecimalFormat df = new DecimalFormat("#,###,###,###,##0.00");
		strValue = df.format(doubleVal.doubleValue());
		strValue = strValue.replaceAll("\\.", "");

		return strValue;
	}

	public static String Double2StrF(Double doubleVal) {
		String pretag = "";
		String postag = "";

		String strValue = "";
		DecimalFormat df = new DecimalFormat("#,###,###,###,##0.00");
		strValue = df.format(doubleVal.doubleValue());
		if (doubleVal.doubleValue() < 0) {
			pretag = "<font color=\"red\" >";
			postag = "</font>";
		}

		return pretag + strValue + postag;
	}

	// si v es 0 devuelve string vacio
	// sino trata de convertir a dd/mm/yyyy
	public static String LDate2Str(Long v) {
		String ymd = "", d = "", m = "", y = "";

		if (v.longValue() == 0)
			return "";
		ymd = v.toString();
		if (ymd.length() != 8)
			return "//";
		y = ymd.substring(0, 4);
		m = ymd.substring(4, 6);
		d = ymd.substring(6, 8);
		return d + "/" + m + "/" + y;
	}

	// si v es 0 devuelve string vacio
	// sino trata de convertir a hh:mm:ss
	public static String LTime2Str(Long v) {
		String hms = "", h = "", m = "", s = "";

		if (v.longValue() <= 0)
			return "";
		hms = v.toString();

		int tam = hms.length();
		s = hms.substring(tam - 2, tam);
		m = hms.substring(tam - 4, tam - 2);
		h = hms.substring(0, tam - 4);

		return h + ":" + m + ":" + s;
	}

	// si v es 0 devuelve string vacio
	// sino trata de convertir a mm/yy
	// @param v debe estar en formato MMYY, ej: 606->06/06 , 1206->12/06
	public static String LongMMYY2Str(Long v) {
		String ymd = "", m = "", y = "";

		if (v.longValue() == 0)
			return "";
		ymd = v.toString();
		if (ymd.length() == 3)
			ymd = "0" + ymd;
		if (ymd.length() != 4)
			return "/";
		m = ymd.substring(0, 2);
		y = ymd.substring(2, 4);
		return m + "/" + y;
	}

	// si v es 0 devuelve string vacio
	// sino trata de convertir a mm/yy
	// @param v debe estar en formato YYYYMMDD
	public static String LongYYYYMMDD2StrMMYY(Long v) {
		String ymd = "", m = "", y = "";

		if (v.longValue() == 0)
			return "";
		ymd = v.toString();
		if (ymd.length() == 7)
			ymd = "0" + ymd;
		if (ymd.length() != 8)
			return "/";
		m = ymd.substring(4, 6);
		y = ymd.substring(2, 4);
		return m + "/" + y;
	}

	/**
	 * Convierte un Date a un StrD por strd se acepta cualquier cadena que represente una fecha en formato:<br>
	 * DD/MM/AAAA[ HH/mm[/ss[/mili]]]<br>
	 * Si se especifica la hora. debe serparese por un caracter espacio.
	 */
	public static String Date2StrD(Date date) {
		String ss = "", mili = "";
		Calendar cal = Calendar.getInstance();
		if (date == null)
			return "";
		cal.setTime(date);

		ss += cal.get(Calendar.SECOND);
		if (cal.get(Calendar.SECOND) < 10)
			ss = "0" + ss;

		mili += cal.get(Calendar.MINUTE);

		return Date2StrT(date) + ":" + ss + ":" + mili;
	}

	/**
	 * Convierte un Date a un StrD por strd se acepta cualquier cadena que represente una fecha en formato:<br>
	 * DD/MM/AAAA<br>
	 * Si se especifica la hora. debe serparese por un caracter espacio.
	 */
	public static String Date2Str(Date date) {
		String dd = "", mm = "", aaaa = "";
		Calendar cal = Calendar.getInstance();
		Calendar calNull = Calendar.getInstance();
		if (date == null)
			return "";

		calNull.clear();
		calNull.setTime(date);
		if (calNull.get(Calendar.YEAR) == 1900 && calNull.get(Calendar.DAY_OF_MONTH) == 1
				&& calNull.get(Calendar.MONTH) == 0)
			return "";

		cal.setTime(date);
		dd += cal.get(Calendar.DAY_OF_MONTH);
		if (cal.get(Calendar.DAY_OF_MONTH) < 10)
			dd = "0" + dd;

		mm += (cal.get(Calendar.MONTH) + 1);
		if ((cal.get(Calendar.MONTH) + 1) < 10)
			mm = "0" + mm;

		aaaa += cal.get(Calendar.YEAR);

		return dd + "/" + mm + "/" + aaaa;
	}

	/**
	 * Convierte un Date a un StrD por strd se acepta cualquier cadena que represente una fecha en formato:<br>
	 * DD/MM/AAAA HH:MM<br>
	 * Si se especifica la hora. debe serparese por un caracter espacio.
	 */
	public static String Date2StrT(Date date) {
		String hh = "", mm = "";
		Calendar cal = Calendar.getInstance();
		if (date == null)
			return "";

		cal.setTime(date);

		hh += cal.get(Calendar.HOUR_OF_DAY);
		if (cal.get(Calendar.HOUR_OF_DAY) < 10)
			hh = "0" + hh;

		mm += cal.get(Calendar.MINUTE);
		if (cal.get(Calendar.MINUTE) < 10)
			mm = "0" + mm;

		return Date2Str(date) + " " + hh + ":" + mm;
	}

	/**
	 * Toma string formato AAAAMMDD y devueve en Date(). si la cadena no tiene el formato esperaro lanza exception
	 */
	public static java.util.Date LDate2Date(String value) throws Exception {
		Calendar cal = Calendar.getInstance();
		String s = value;
		String d = "", m = "", y = "";

		s = s.trim();
		if (s.length() != 8) {
			cal.clear();
			cal.set(0, 0, 0);
			return cal.getTime();

		}

		y = s.substring(0, 4);
		m = s.substring(4, 6);
		d = s.substring(6, 8);

		cal.clear();
		cal.set(Integer.parseInt(y), Integer.parseInt(m) - 1, Integer.parseInt(d));
		return cal.getTime();
	}

	/**
	 * @author fb70883
	 * @date 03/08/2008 Gestion de combos
	 */

	public static String renderComboByCode(Collection<GeneralDataItem> col, String selectedCode) {
		StringBuffer out = new StringBuffer(col.size() * 25); // 25 tama�o aproximado de un option con un value de 1
		for (GeneralDataItem gdi : col) {
			out.append("<option value=\"");
			out.append(gdi.getCode().trim());
			out.append(gdi.getCode().equals(selectedCode) ? "\" selected " : "\"");
			out.append(">" + Str2Html(gdi.getDescription()));
			out.append("</option><br>");
		}
		return out.toString();
	}

	/**
	 * @author fb70883
	 * @date 03/08/2008 Gestion de combos
	 */

	public static String renderComboByCode(Collection<GeneralDataItem> col, Long selectedCode) {
		Long aux = null;
		StringBuffer out = new StringBuffer(col.size() * 25); // 25 tama�o aproximado de un option con un value de 1
		for (GeneralDataItem gdi : col) {
			aux = new Long(gdi.getCode().trim());
			out.append("<option value=\"");
			out.append(gdi.getCode().trim());
			out.append(aux.equals(selectedCode) ? "\" selected " : "\"");
			out.append(">" + Str2Html(gdi.getDescription()));
			out.append("</option><br>");
		}
		return out.toString();
	}

	public static String renderComboByLongCode(Collection<GeneralDataItem> col, Long selectedCode) {
		return renderComboByCode(col, selectedCode);
	}

	/**
	 * @author fb70883
	 * @date 28/08/2008 Gestion de combos - Seleccion Multiple
	 */

	public static String renderComboByCode(Collection<GeneralDataItem> col, Collection<?> selectedCodes) {
		StringBuffer out = new StringBuffer(col.size() * 25); // 25 tama�o aproximado de un option con un value de 1
		for (GeneralDataItem gdi : col) {
			out.append("<option value=\"");
			out.append(gdi.getCode().trim());
			out.append(contiene(selectedCodes, gdi.getCode()) ? "\" selected " : "\"");
			out.append(">" + Str2Html(gdi.getDescription()));
			out.append("</option><br>");
		}
		return out.toString();
	}

	public static boolean contiene(Collection<?> c, String code) {
		for (Object o : c) {
			if (o.equals(code))
				return true;
		}
		return false;
	}

	/**
	 * @author fb70883
	 * @date 13/08/2008 Gestion de combos
	 */

	public static String renderComboByParentCode(Collection<GeneralDataItem> col, String parentCode, Long selectedCode) {
		Long aux = null;
		StringBuffer out = new StringBuffer(col.size() * 25); // 25 tama�o aproximado de un option con un value de 1
		for (GeneralDataItem gdi : col) {
			if (!gdi.getParentCode().equals(parentCode))
				continue;
			aux = new Long(gdi.getCode().trim());
			out.append("<option value=\"");
			out.append(gdi.getCode().trim());
			out.append((aux.equals(selectedCode)) ? "\" selected " : "\"");
			out.append(">" + Str2Html(gdi.getDescription()));
			out.append("</option><br>");
		}
		return out.toString();
	}

	/**
	 * @author fb70883
	 * @date 12/09/2008 Gestion de combos
	 */

	public static String renderComboByParentCode(Collection<GeneralDataItem> col, String parentCode, Long selectedCode,
			List<String> parentCodeExceptions) {
		Long aux = null;
		StringBuffer out = new StringBuffer(col.size() * 25); // 25 tama�o aproximado de un option con un value de 1
		for (GeneralDataItem gdi : col) {
			if (!gdi.getParentCode().equals(parentCode) && !parentCodeExceptions.contains(gdi.getParentCode()))
				continue;
			aux = new Long(gdi.getCode().trim());
			out.append("<option value=\"");
			out.append(gdi.getCode().trim());
			out.append((aux.equals(selectedCode)) ? "\" selected " : "\"");
			out.append(">" + Str2Html(gdi.getDescription()));
			out.append("</option><br>");
		}
		return out.toString();
	}

	/**
	 * @author ae01772
	 * @date 27/04/2010 Gestion de combos
	 */

	public static String renderComboByParentCode(Collection<GeneralDataItem> col, String selectedCode) {
		StringBuffer out = new StringBuffer(col.size() * 25); // 25 tama�o aproximado de un option con un value de 1
		for (GeneralDataItem gdi : col) {
			out.append("<option value=\"");
			out.append(gdi.getParentCode().trim());
			out.append(gdi.getParentCode().equals(selectedCode) ? "\" selected " : "\"");
			out.append(">" + Str2Html(gdi.getDescription()));
			out.append("</option><br>");
		}
		return out.toString();
	}

	/**
	 * @author ae01772
	 * @date 27/04/2010 Gestion de combos
	 */

	public static String renderComboByParentCode(Collection<GeneralDataItem> col, Long selectedCode) {
		Long aux = null;
		StringBuffer out = new StringBuffer(col.size() * 25); // 25 tama�o aproximado de un option con un value de 1
		for (GeneralDataItem gdi : col) {
			aux = new Long(gdi.getParentCode().trim());
			out.append("<option value=\"");
			out.append(gdi.getParentCode().trim());
			out.append(aux.equals(selectedCode) ? "\" selected " : "\"");
			out.append(">" + Str2Html(gdi.getDescription()));
			out.append("</option><br>");
		}
		return out.toString();
	}

	/**
	 * @author Fabian Benitez (fb70883)
	 * @date 03/08/2008 Gestion de combos
	 */

	public static String getComboDescByCode(Collection<GeneralDataItem> col, String selectedCode) {

		if (null == selectedCode)
			throw new RuntimeException("El codigo seleccionado es nulo");

		for (GeneralDataItem gdi : col) {
			if (gdi.getCode().trim().equals(selectedCode.trim()))
				return Str2Html(gdi.getDescription());
		}
		return Str2Html("");
	}

	/**
	 * @author Fabian Benitez (fb70883)
	 * @date 05/04/2010 Gestion de combos
	 */

	public static String getComboDescByAnyCode(Collection<GeneralDataItem> col, String selectedCode) {

		if (null == selectedCode)
			throw new RuntimeException("El codigo seleccionado es nulo");

		for (GeneralDataItem gdi : col) {
			if (gdi.getCode().trim().equals(selectedCode.trim())
					|| gdi.getParentCode().trim().equals(selectedCode.trim()))
				return Str2Html(gdi.getDescription());
		}
		return Str2Html("");
	}

	/**
	 * @author Fabian Benitez (fb70883)
	 * @date 03/08/2008 Gestion de combos
	 */

	public static String getComboDescByCode(Collection<GeneralDataItem> col, Long selectedCode) {

		if (null == selectedCode)
			throw new RuntimeException("El codigo seleccionado es nulo");

		Long aux = null;
		for (GeneralDataItem gdi : col) {
			aux = new Long(gdi.getCode().trim());
			if (aux.equals(selectedCode))
				return Str2Html(gdi.getDescription());
		}
		return Str2Html("");
	}

	/**
	 * Devuelve la descripci&oacute;n de una tabla de lookup
	 * 
	 * @param col
	 *            La tabla de lookup
	 * @param selectedCode
	 *            C&oacute;digo seleccionado
	 * @param emptyCode
	 *            C&oacute;digo considerado como vac&iacue;o
	 * @return La descripci&oacute;n del c&oacute;digo seleccionado. Si el seleccionado es el c&oacute;digo vac&iacue;o,
	 *         devuelve &nbsp;
	 */
	public static String getComboDescByCode(Collection<GeneralDataItem> col, Long selectedCode, Long emptyCode) {

		if ((null == selectedCode) || selectedCode.equals(emptyCode)) {
			return "&nbsp;";
		}

		return getComboDescByCode(col, selectedCode);
	}

	public static String getComboDescByCode(Collection<GeneralDataItem> col, String selectedCode, String emptyCode) {

		if ((null == selectedCode) || selectedCode.equals(emptyCode)) {
			return "&nbsp;";
		}

		return getComboDescByCode(col, selectedCode);
	}

	/**
	 * @author Fabian Benitez (fb70883)
	 * @date 05/04/2010 Gestion de combos
	 */

	public static String getComboDescByAnyCode(Collection<GeneralDataItem> col, Long selectedCode) {

		if (null == selectedCode)
			throw new RuntimeException("El codigo seleccionado es nulo");

		Long aux = null;
		Long parentAux = null;
		for (GeneralDataItem gdi : col) {
			aux = new Long(gdi.getCode().trim());
			parentAux = new Long(gdi.getParentCode().trim());
			if (aux.equals(selectedCode) || parentAux.equals(selectedCode))
				return Str2Html(gdi.getDescription());
		}
		return Str2Html("");
	}

	/**
	 * @author Fabian Benitez (fb70883)
	 * @date 05/04/2010 Gestion de combos
	 */

	public static String getComboDescByParentCode(Collection<GeneralDataItem> col, String selectedCode) {

		if (null == selectedCode)
			throw new RuntimeException("El codigo seleccionado es nulo");

		for (GeneralDataItem gdi : col) {
			if (gdi.getParentCode().trim().equals(selectedCode.trim()))
				return Str2Html(gdi.getDescription());
		}
		return Str2Html("");
	}

	public static String getComboDescByParentCode(Collection<GeneralDataItem> col, String selectedCode, String emptyCode) {

		if ((null == selectedCode) || selectedCode.equals(emptyCode)) {
			return "&nbsp;";
		}

		return getComboDescByParentCode(col, selectedCode);
	}

	/**
	 * @author Fabian Benitez (fb70883)
	 * @date 05/04/2010 Gestion de combos
	 */

	public static String getComboDescByParentCode(Collection<GeneralDataItem> col, Long selectedCode) {

		if (null == selectedCode)
			throw new RuntimeException("El codigo seleccionado es nulo");

		Long aux = null;
		for (GeneralDataItem gdi : col) {
			aux = new Long(gdi.getParentCode().trim());
			if (aux.equals(selectedCode))
				return Str2Html(gdi.getDescription());
		}
		return Str2Html("");
	}

	public static String getComboDescByParentCode(Collection<GeneralDataItem> col, Long selectedCode, Long emptyCode) {
		if ((null == selectedCode) || selectedCode.equals(emptyCode)) {
			return "&nbsp;";
		}

		return getComboDescByParentCode(col, selectedCode);

	}

	/**
	 * @author fb70883
	 * @date 24/08/2008 Gestion de combos
	 */

	public static String getComboDescByParentCode(Collection<GeneralDataItem> col, String parentCode, Long selectedCode) {

		if (null == selectedCode)
			throw new RuntimeException("El codigo seleccionado es nulo");

		Long aux = null;
		for (GeneralDataItem gdi : col) {
			aux = new Long(gdi.getCode().trim());
			if (gdi.getParentCode().equals(parentCode) && aux.equals(selectedCode))
				return Str2Html(gdi.getDescription());
		}
		return Str2Html("");
	}

	/**
	 * @author fb70883
	 * @date 03/08/2008 Encode string en Html
	 */

	public static String Str2Html(String v) {
		if (v == null)
			v = "";

		int n = v.length();
		if (n == 0) {
			return "&nbsp;";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < n; i++) {
			char c = v.charAt(i);
			switch (c) {
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case '\u00D1':				//�
				//sb.append("&#209;");
				sb.append("&Ntilde;");
				break;
			case '\u00F1':				//�
				//sb.append("&#241;");
				sb.append("&ntilde;");
				break;
			case '\n':
				sb.append("<br>");
				break;
			case '\r':
				sb.append("<br>");
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}

	// TODO Refactorizar y ver si no sirve alguna de las de mas arriba que estan mas simples

	public static String renderComboByKey(Collection<GeneralDataItem> col, Long selectedKey) {
		String out = "";
		for (GeneralDataItem gdi : col) {
			out += "<option value=\"";
			out += gdi.getEntityKey().toString() + "\"";
			if (gdi.getEntityKey().equals(selectedKey))
				out += " selected ";
			out += ">" + Str2Html(gdi.getDescription());
			out += "</option>";
		}
		return out;
	}

	// TODO Refactorizar y ver si no sirve alguna de las de mas arriba que estan mas simples

	public static String getComboDescByKey(Collection<GeneralDataItem> col, Long selectedKey) {
		String out = "";
		for (GeneralDataItem gdi : col) {
			if (gdi.getEntityKey().equals(selectedKey))
				out = gdi.getDescription();
		}
		return Str2Html(out);
	}

	// devuelve string indicando
	// cod - descripcion
	// si no encuentra code en mapName, devuelve code sin descripcion

	// TODO Refactorizar y ver si no sirve alguna de las de mas arriba que estan mas simples
	// TODO Usar Exceptions!!!

	public static String getLKDescription(Collection localCol, String code) {
		String strRet = "-";

		strRet = code;
		try {
			for (Iterator it = localCol.iterator(); it.hasNext();) {
				GeneralDataItem gdi = (GeneralDataItem) it.next();

				// 
				try {
					if (new Integer(gdi.getCode()).intValue() == new Integer(code).intValue()) {
						strRet = gdi.getCode() + " - " + Str2Html(gdi.getDescription());
						break;
					}
				} catch (Exception e) {
					if (gdi.getCode().equals(code)) {
						strRet = gdi.getCode() + " - " + (gdi.getDescription());
						break;
					}
				}

			}
		} catch (Exception e) {
			strRet = "-2";
		}
		return strRet;
	}

	// devuelve string indicando
	// descripcion
	// si no encuentra code en mapName, cadena vacia

	// TODO Refactorizar y ver si no sirve alguna de las de mas arriba que estan mas simples
	// TODO Usar Exceptions!!!

	public static String getLKDesc(Collection localCol, String code) {
		String strRet = "";

		strRet = "";
		try {
			for (Iterator it = localCol.iterator(); it.hasNext();) {
				GeneralDataItem gdi = (GeneralDataItem) it.next();

				// 
				try {
					if (new Integer(gdi.getCode()).intValue() == new Integer(code).intValue()) {
						strRet = Str2Html(gdi.getDescription());
						break;
					}
				} catch (Exception e) {
					if (gdi.getCode().equals(code)) {
						strRet = (gdi.getDescription());
						break;
					}
				}

			}
		} catch (Exception e) {
			strRet = "-2";
		}
		return strRet;
	}

	// devuelve string indicando
	// cod - descripcion
	// si no encuentra key en la coleccion, devuelve "-"

	// TODO Refactorizar y ver si no sirve alguna de las de mas arriba que estan mas simples
	// TODO Usar Exceptions!!!

	public static String getLKDescription(Collection localCol, Long key) {
		String strRet = "-";

		for (Iterator it = localCol.iterator(); it.hasNext();) {
			GeneralDataItem gdi = (GeneralDataItem) it.next();
			if (gdi.getEntityKey().equals(key)) {
				if (gdi.getCode().length() > 0) {
					strRet = gdi.getCode() + " - " + Str2Html(gdi.getDescription());
				} else {
					strRet = Str2Html(gdi.getDescription());
				}
				break;
			}
		}
		return strRet;
	}

	// devuelve string correspondiente al atributo indicado, por ej. atributo = parentCode
	// retorna el valor almacenado en el lookup en en atributo parentCode

	// TODO Refactorizar y ver si no sirve alguna de las de mas arriba que estan mas simples

	public static String getLKDescription(Collection localCol, Long key, String atributo) {
		String strRet = "-";

		for (Iterator it = localCol.iterator(); it.hasNext();) {
			GeneralDataItem gdi = (GeneralDataItem) it.next();
			if (gdi.getEntityKey().equals(key)) {
				if (atributo.equals("parentCode")) {
					strRet = gdi.getParentCode();
				}
				if (atributo.equals("description")) {
					strRet = Str2Html(gdi.getDescription());
				}
				break;
			}
		}
		return strRet;
	}

	// TODO Refactorizar y ver si no sirve alguna de las de mas arriba que estan mas simples
	// TODO Usar Exceptions!!!

	public static String getLKDobleDescription(Collection localCol, String parentCode, String code) {
		String strRet = "-1";
		strRet = code;
		int equalParentCode = 0;
		int equalCode = 0;

		try {
			for (Iterator it = localCol.iterator(); it.hasNext();) {
				GeneralDataItem gdi = (GeneralDataItem) it.next();

				equalParentCode = 0;
				try {
					if ((new Integer(gdi.getParentCode()).intValue() == new Integer(parentCode).intValue()))
						equalParentCode = 1;
				} catch (Exception e) {
					if (gdi.getParentCode().equals(parentCode)) {
						equalParentCode = 1;
					}
				}

				equalCode = 0;
				try {
					if ((new Integer(gdi.getCode()).intValue() == new Integer(code).intValue()))
						equalCode = 1;
				} catch (Exception e) {
					if (gdi.getCode().equals(code)) {
						equalCode = 1;
					}
				}

				// ahora chequea
				if (equalParentCode == 1 && equalCode == 1) {
					strRet = gdi.getCode() + " - " + Str2Html(gdi.getDescription());
					break;
				}

			}
		} catch (Exception e) {
			strRet = "-2";
		}
		return strRet;
	}

	/**
	 * Genera el array de Js requerido por la funcion FillList() para modificar<br>
	 * dinemicamente el valor de los combos
	 * 
	 * @param tipoDes
	 *            =1 escribe los codigos tipoDes=2 esribe las descripciones
	 */

	// TODO Refactorizar y ver si no sirve alguna de las de mas arriba que estan mas simples
	// TODO usar exceptions!!!

	public String getArrayJsOld(String arrName, Collection colGdi, int tipoDes) {
		String strRet = "";

		try {
			int i = -1;
			int j = 0;

			long actualCode = 9878;

			if (colGdi == null)
				return "col nula";

			String strSubItem = "";
			for (Iterator it = colGdi.iterator(); it.hasNext();) {
				GeneralDataItem gdi = (GeneralDataItem) it.next();

				if (gdi == null)
					return "gdi nulo";

				if (gdi.getParentEntityKey() == null)
					return "parent nulo";

				// si es el primero
				if (i == -1) {
					actualCode = gdi.getParentEntityKey().longValue();
					strSubItem = "";
					j = 0;
					i = 0;

				} else {

					// verifica si cambio
					if (gdi.getParentEntityKey().longValue() != actualCode) {
						actualCode = gdi.getParentEntityKey().longValue();

						// agrega el string de inicializacion de subitems
						strRet += arrName + " [" + actualCode + "] = new Array(" + j + ");\n";

						// graba los subitems
						strRet += strSubItem;

						// inicializa el contador de subitemsy string de subitems
						j = 0;
						strSubItem = "";

						// incrementa el contador de items
						i++;
					}
				}

				if (tipoDes == 1) {
					// carga la lines
					strSubItem += arrName + " [" + i + "] [" + j + "] = " + gdi.getEntityKey().longValue() + ";\n";

				} else {
					// carga la lines

					if (gdi.getDescription() == null)
						return "gdi.esription nula";

					strSubItem += arrName + " [" + i + "] [" + j + "] = '" + Str2Html(gdi.getDescription()) + "';\n";
				}
				j++;
			}

			// si el ultimo elemento correspondia a un nuevo grupo, agraga el nuevo grupo
			strRet += arrName + " [" + i + "] = new Array(" + j + ");\n";

			// graba los subitems
			strRet += strSubItem;

			// creacion del array general
			strRet = arrName + " = new Array(" + (i + 1) + ");\n" + strRet;

		} catch (Exception e) {
			strRet = "<ERROR>";
		}
		return strRet;
	}

	/**
	 * Genera el array de Js requerido por la funcion FillList() para modificar<br>
	 * dinemicamente el valor de los combos
	 * 
	 * Como siempre se trata de subcolecciones, agrega un Todos para todos los grupos!!
	 * 
	 * @param tipoDes
	 *            =1 escribe los codigos tipoDes=2 esribe las descripciones addAll=1 Agrega el item Todos (para reporte)
	 *            addAll=0 NO agrega el item Todos (para formulario)
	 */

	// TODO Refactorizar y ver si no sirve alguna de las de mas arriba que estan mas simples
	// TODO usar exceptions!!!

	public String getArrayJs(String arrName, Collection colGdi, int tipoDes, int addAll) {
		String strRet = "";

		try {
			int i = -1;
			int j = addAll;
			long actualCode = 9878;

			if (colGdi == null)
				return "ERROR: col nula";

			String strSubItem = "";
			for (Iterator it = colGdi.iterator(); it.hasNext();) {
				GeneralDataItem gdi = (GeneralDataItem) it.next();

				if (gdi == null)
					return "ERROR: gdi nulo";

				if (gdi.getParentEntityKey() == null)
					return "ERROR: parent nulo";

				// si es el primero
				if (i == -1) {
					actualCode = gdi.getParentEntityKey().longValue();
					strSubItem = "";
					j = addAll;
					i = 0;

				} else {
					// verifica si cambio
					if (gdi.getParentEntityKey().longValue() != actualCode) {

						// agrega el string de inicializacion de subitems
						strRet += arrName + " [" + actualCode + "] = new Array(" + j + ");\n";

						if (addAll == 1) {
							// agrega el elemento 0-Todos
							if (tipoDes == 1) {
								strSubItem += arrName + " [" + actualCode + "] [0] = 0" + ";\n";
							} else {
								strSubItem += arrName + " [" + actualCode + "] [0] = 'Todos'" + ";\n";
							}
						}

						// graba los subitems
						strRet += strSubItem;

						// inicializa el contador de subitemsy string de subitems
						actualCode = gdi.getParentEntityKey().longValue();

						j = addAll;
						strSubItem = "";

						// incrementa el contador de items
						i++;
					}
				}

				// carga la lines
				if (tipoDes == 1) {
					strSubItem += arrName + " [" + gdi.getParentEntityKey().longValue() + "] [" + j + "] = "
							+ gdi.getEntityKey().longValue() + ";\n";
				} else {
					strSubItem += arrName + " [" + gdi.getParentEntityKey().longValue() + "] [" + j + "] = '"
							+ Str2Html(gdi.getDescription()) + "';\n";
				}
				j++;
			}

			// agraga el nuevo grupo (ultimo)
			strRet += arrName + " [" + actualCode + "] = new Array(" + j + ");\n";

			if (addAll == 1) {
				// agrega el elemento 0-Todos
				if (tipoDes == 1) {
					strSubItem += arrName + " [" + actualCode + "] [0] = 0" + ";\n";
				} else {
					strSubItem += arrName + " [" + actualCode + "] [0] = 'Todos'" + ";\n";
				}
			}

			// graba los subitems
			strRet += strSubItem;

			// creacion del array general
			strRet = arrName + " = new Array(" + (i + 1) + ");\n" + strRet;

		} catch (Exception e) {
			strRet = "<ERROR>";
		}
		return strRet;
	}

	/**
	 * Genera el array de Js requerido por la funcion FillList() para modificar<br>
	 * dinemicamente el valor de los combos
	 * 
	 * Como siempre se trata de subcolecciones, agrega un Todos para todos los grupos!!
	 * 
	 * @param tipoDes
	 *            =1 escribe los codigos tipoDes=2 esribe las descripciones addAll=1 Agrega el item Todos (para reporte)
	 *            addAll=0 NO agrega el item Todos (para formulario) parentCode = 1 Agrega la cadena almacenada en el
	 *            atributo parentCode del gdi parentCode = 0 No agrega la cadena almacenada en el atributo parentCode
	 *            del gdi
	 */

	// TODO Refactorizar y ver si no sirve alguna de las de mas arriba que estan mas simples
	// TODO usar exceptions!!!

	public String getArrayJs(String arrName, Collection colGdi, int tipoDes, int addAll, int parentCode) {
		String strRet = "";

		try {
			int i = -1;
			int j = addAll;
			long actualCode = 9878;

			if (colGdi == null)
				return "ERROR: col nula";

			String strSubItem = "";
			for (Iterator it = colGdi.iterator(); it.hasNext();) {
				GeneralDataItem gdi = (GeneralDataItem) it.next();

				if (gdi == null)
					return "ERROR: gdi nulo";

				if (gdi.getParentEntityKey() == null)
					return "ERROR: parent nulo";

				// si es el primero
				if (i == -1) {
					actualCode = gdi.getParentEntityKey().longValue();
					strSubItem = "";
					j = addAll;
					i = 0;

				} else {
					// verifica si cambio
					if (gdi.getParentEntityKey().longValue() != actualCode) {

						// agrega el string de inicializacion de subitems
						strRet += arrName + " [" + actualCode + "] = new Array(" + j + ");\n";

						if (addAll == 1) {
							// agrega el elemento 0-Todos
							if (tipoDes == 1) {
								strSubItem += arrName + " [" + actualCode + "] [0] = 0" + ";\n";
							} else {
								strSubItem += arrName + " [" + actualCode + "] [0] = 'Todos'" + ";\n";
							}
						}

						// graba los subitems
						strRet += strSubItem;

						// inicializa el contador de subitemsy string de subitems
						actualCode = gdi.getParentEntityKey().longValue();

						j = addAll;
						strSubItem = "";

						// incrementa el contador de items
						i++;
					}
				}

				// carga la lines
				if (tipoDes == 1) {
					strSubItem += arrName + " [" + gdi.getParentEntityKey().longValue() + "] [" + j + "] = "
							+ gdi.getEntityKey().longValue() + ";\n";
				} else {
					strSubItem += arrName + " [" + gdi.getParentEntityKey().longValue() + "] [" + j
							+ "] = new Array(2);\n";
					strSubItem += arrName + " [" + gdi.getParentEntityKey().longValue() + "] [" + j + "] [0] = '"
							+ Str2Html(gdi.getDescription()) + "';\n";
					strSubItem += arrName + " [" + gdi.getParentEntityKey().longValue() + "] [" + j + "] [1] = '"
							+ Str2Html(gdi.getParentCode()) + "';\n";
				}
				j++;
			}

			// agraga el nuevo grupo (ultimo)
			strRet += arrName + " [" + actualCode + "] = new Array(" + j + ");\n";

			if (addAll == 1) {
				// agrega el elemento 0-Todos
				if (tipoDes == 1) {
					strSubItem += arrName + " [" + actualCode + "] [0] = 0" + ";\n";
				} else {
					strSubItem += arrName + " [" + actualCode + "] [0] = 'Todos'" + ";\n";
				}
			}

			// graba los subitems
			strRet += strSubItem;

			// creacion del array general
			strRet = arrName + " = new Array(" + (i + 1) + ");\n" + strRet;

		} catch (Exception e) {
			strRet = "<ERROR>";
		}
		return strRet;
	}

	public static java.util.Date nullDate() {
		Calendar calNull = Calendar.getInstance();
		calNull.clear();
		calNull.set(1900, 0, 1);
		return calNull.getTime();
	}

	public static String fillWithCeros(int valor, int cantidad) {
		String valorString = String.valueOf(valor);
		while (valorString.length() < cantidad) {
			valorString = "0" + valorString;
		}
		return valorString;
	}

	public static String fillWithCeros(Object object, int cantidad) {
		String valorString = String.valueOf(object);
		while (valorString.length() < cantidad) {
			valorString = "0" + valorString;
		}
		return valorString;
	}

	// Toma string formato DD/MM/AAAA o DD/MM/AAAA HH:MM
	// y devueve en Date().
	public static java.util.Date Str2Date(String value) throws Exception {
		Calendar cal = Calendar.getInstance();
		String s = value;
		int t0 = 0, t1 = 0, h24 = 0, mi = 0, ss = 0, mili = 0, ph0 = 0, ph1 = 0, ph2 = 0, ph3 = 0;
		String d = "", m = "", y = "";

		s = s.trim();
		// si cadena vacia, entonces convertir a 0
		if (s.length() == 0)
			return new java.util.Date();

		s = s + " ";
		ph0 = s.indexOf(" ");

		t0 = s.indexOf("/", 0);
		t1 = s.indexOf("/", t0 + 1);
		d = s.substring(0, t0);
		m = s.substring(t0 + 1, t1);
		y = s.substring(t1 + 1, ph0);

		s = s.trim() + ":"; // asi nos aseguramos que siempre la hora estara delimitada entre :
		ph0 = s.lastIndexOf(" ");
		if (ph0 > -1) {
			ph1 = s.indexOf(":", ph0 + 1);
			ph2 = s.indexOf(":", ph1 + 1);
			ph3 = s.indexOf(":", ph2 + 1);

			if (ph0 > -1 && ph0 < ph1)
				h24 = Integer.parseInt(s.substring(ph0 + 1, ph1));
			if (ph1 < ph2)
				mi = Integer.parseInt(s.substring(ph1 + 1, ph2));
			if (ph2 < ph3)
				ss = Integer.parseInt(s.substring(ph2 + 1, ph3));
			if (ph3 > -1 && ph3 < s.length() - 1)
				mili = Integer.parseInt(s.substring(ph3, s.length()));
		}
		cal.clear();
		cal.set(Integer.parseInt(y), Integer.parseInt(m) - 1, Integer.parseInt(d), h24, mi, ss);
		cal.set(Calendar.MILLISECOND, mili);
		return cal.getTime();
	}

	// Toma Date y lo converte a Long AAAAMMDD
	public static Long Date2Long(Date value) throws Exception {
		String tmp = Date2Str(value);
		return getLDate(tmp);
	}

	// retorna un string con los elementos de list, separados por ","
	public static String join(Collection col) throws Exception {
		return CmIOFormat.join(col, ",");
	}

	// retorna un string con los elementos de list, separados por sep
	public static String join(Collection col, String sep) throws Exception {
		StringBuffer sb = new StringBuffer();
		Iterator it = col.iterator();
		while (it.hasNext()) {
			Object ele = it.next();
			sb.append(ele.toString());
			if (it.hasNext()) {
				sb.append(sep);
			}
		}
		return sb.toString();
	}

	// retorna un string con la mascara de value, de start a end reemplazados
	// por X
	public static String strMaskX(String value, int start, int end) {
	
		return strMask(value, "X", start, end);
	}

	// retorna un string con la mascara de value, de start a end reemplazados
	// por el caracter pasado por parametro
	public static String strMask(String value, String maskChar, int start, int end) {
		
		String mask = "", valueMasked = "";
		
		if (end < start) {

		} else {
			for (int i = start; i < end; ++i) {
				mask += maskChar;
			}
			valueMasked = value.replaceAll(value.substring(start, end), mask);
		}
		
		return valueMasked;
	}

	/**
	 * Tranforma una fecha 20061205 (aaaaMMdd) a 0605(mmAA)
	 * 
	 * @param v
	 *            Long con una fecha del tipo 20061205 (aaaaMMdd)
	 * @return Long 0605(mmAA)
	 */
	public static Long convertLDate2SSDate(Long v) throws Exception {

		String longDate = v.toString();
		String mm = "";
		String aa = "";
		String shortDate = "";

		if (longDate.length() == 8) {
			aa = longDate.substring(2, 4);
			mm = longDate.substring(4, 6);
			shortDate = mm + "/" + aa;
		} else
			throw new Exception("No se puede convertir el numero: " + v + ".");

		return getLongMMYY(shortDate);
	}

	// si s es cadena vacia, retorna 0
	public static Long getLMMAAShortDate(String s) {
		Long ret = null;
		int t0 = 0;
		String ym = "", m = "", y = "";

		// si cadena vacia, entonces convertir a 0
		if (s.trim().length() == 0)
			return new Long(0);

		t0 = s.indexOf("/", 0);

		m = s.substring(0, t0);
		if (m.length() < 2)
			m = "0" + m;
		y = s.substring(t0 + 1, s.length());
		ym = m + y;
		ret = new Long(ym);
		return ret;
	}

	/**
	 * Convierte una fecha en el formato aaMM a mm/AA
	 * 
	 * @param v
	 * @return
	 */
	public static String LShortDate2StringDate(Long v) throws Exception {

		String strDate = "";

		if (v != null) {

			if (v.compareTo(new Long(0)) == 0) {
				return "";
			}

			strDate = v.toString();

			if (strDate.length() == 3)
				strDate = "0" + strDate;

			if (strDate.length() == 4) {
				strDate = strDate.substring(2, 4) + "/" + strDate.substring(0, 2);
			} else {
				return "//";
			}
		}

		return strDate;
	}

	public static String LShortDate2StringDateAAMM(Long v) throws Exception {

		String strDate = "";

		if (v != null) {

			if (v.compareTo(new Long(0)) == 0) {
				return "";
			}

			strDate = v.toString();

			if (strDate.length() == 3)
				strDate = "0" + strDate;

			if (strDate.length() == 4) {
				strDate = strDate.substring(0, 2) + "/" + strDate.substring(2, 4);
			} else {
				return "//";
			}
		}

		return strDate;
	}

	// Alinea a Derecha y completa con espacios a izquierda.
	public static String alignRightString(String value, int size, String fillValue) {
		int toComplete = size - value.length();
		if (toComplete >= 0) {
			for (int i = 0; i < toComplete; i++) {
				value = fillValue + value;
			}
		}
		return value;
	}

	/**
	 * Escapea las comillas para JavaScript
	 * 
	 * @param string
	 *            Lo que se quiere escapear que puede contener comillas dobles
	 * @return El string siministrado con las comillas dobles (") reemplazadas por su secuencia de escape (\")
	 */
	public static String escapearComillasJS(String string) {
		String nueva = string.replaceAll("\"", "\\\\\"");

		return nueva;
	}

	/**
	 * Devuelve el primer string no nulo y no vac&iacute;o
	 * 
	 * @param strings
	 *            Cantidad variable de par�metros de tipo <code>String</code>
	 * @return El primer string no nulo o vac&iacute;o. Si todos los strings son nulos o vac&iacute;os, devuelve
	 *         <code>""</code>
	 */
	public static String getFirstValidString(String... strings) {

		for (String string : strings) {
			if ((string != null) && !string.equals("")) {
				return string;
			}
		}

		return "";
	}

	/**
	 * Devuelve un string vac&iacute;o si el string suministrado solo contiene espacios o ceros
	 * 
	 * @param s
	 *            El string que se quiere analizar
	 * @return <code>""</code> Si <code>s</code> solo tiene ceros o espacios. Si tiene otros caracters devuelve el
	 *         string original
	 */
	public static String getNonZeroString(String s) {
		for (byte b : s.getBytes()) {
			if ((b != '0') && (b != ' ')) {
				return s;
			}
		}

		return "";
	}

	/**
	 * Concatena dos arrays de byte y devuelve el resultado
	 * 
	 * @param a
	 *            El primer array de bytes
	 * @param b
	 *            El segundo array de bytes
	 * @return a + b
	 */
	public static byte[] concatenate(byte[] a, byte[] b) {

		byte[] result = new byte[a.length + b.length];

		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);

		return result;
	}

	/**
	 * Convierte a may�sculas todos los elementos de una Lista de String
	 * 
	 * @param lista
	 *            La lista a convertir
	 * @return newlista La lista convertida
	 */
	public static List<String> listToUpperCase(List<String> lista) {
		if (lista != null) {
			List<String> newlist = new ArrayList<String>();
			for (String data : lista) {
				String upperCaseData = data.toUpperCase();
				newlist.add(upperCaseData);
			}
			return newlist;
		} else
			return null;
	}

	/**
	 * Dado un n&uacute;mero entero, devuelve un string que representa una unidad de magnitud de memoria
	 * 
	 * @param memory
	 *            bytes de memoria
	 * @return Una representaci&oacute;n en orden de magnitud, por ejemplo dado 17230 devuelve 17 MB
	 */
	public static String formatMemorySize(long memory) {

		if (memory > 1000000000000L) {
			return (memory / 1000000000000L) + " TB";
		} else if (memory > 1000000000L) {
			return (memory / 1000000000L) + " GB";
		} else if (memory > 1000000L) {
			return (memory / 1000000L) + " MB";
		} else if (memory > 1000L) {
			return (memory / 1000L) + " KB";
		}

		return memory + " B";
	}

	/**
	 * Interpreta una lista del tipo "clave11=valor1,clave2=valor2,...,claveN=valorN" como un mapa del tipo clave=valor
	 * donde clave es num&eacute;rico y valor una cadena de texto
	 * 
	 * @param list
	 *            La lista representada en una cadena que se quiere interpretar
	 * @return Un mapa cargado con los elementos de la lista. Ante cualquier error devuelve un mapa vac&iacute;o
	 */
	public static Map<Long, String> parseMapFromList(String list) {
		return parseMapFromList(list, ",", "=");
	}

	/**
	 * Interpreta una lista del tipo"clave1[keyValueSeparator]valor1[itemSeparator]clave2[keyValueSeparator]valor2[itemSeparator]...[itemSeparator]claveN[keyValueSeparator]valorN"
	 * como un mapa del tipo clave=valor donde clave es num&eacute;rico y valor una cadena de texto
	 * 
	 * @param list
	 *            La lista representada en una cadena que se quiere interpretar
	 * @param itemSeparator
	 *            Expresi&oacute;n regular utilizada para separar &iacute;tems del mapa
	 * @param itemSeparator
	 *            Expresi&oacute;n regular utilizada para separar, en cada &iacute;tems del mapa, la clave del valor
	 * @return Un mapa cargado con los elementos de la lista. Ante cualquier error devuelve un mapa vac&iacute;o
	 */
	public static Map<Long, String> parseMapFromList(String list, String itemSeparator, String keyValueSeparator) {
		Map<Long, String> mapa = new HashMap<Long, String>();

		try {
			String[] mapItems = list.split(itemSeparator);

			for (String mapItem : mapItems) {
				String[] claveValor = mapItem.split(keyValueSeparator);
				mapa.put(new Long(claveValor[0]), claveValor[1]);
			}
		} catch (Exception e) {

		}

		return mapa;
	}
	
	/** Arma y retorna un String de manera optimizada */
	
	public static String buildMsg(Object... objects) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < objects.length; i++) {
			sb.append( null == objects[i] ? "" : objects[i].toString());
		}
		return sb.toString();
	}
	
	/** 
	 * @author Fabian Benitez (fb70883)
	 * @version 9/11/2013
	 * 
	 * Arma y retorna el date especificado segun el patron/formato y timezone definidos
	 *  
	 */
	
	public static String getFormattedDate(Date date, String pattern) {
		return getFormattedDate(date, pattern, TimeZone.getDefault());
	}
	
	/** 
	 * @author Fabian Benitez (fb70883)
	 * @version 10/04/2012
	 * 
	 * Arma y retorna el date especificado segun el patron/formato y timezone definidos
	 *  
	 */
	
	public static String getFormattedDate(Date date, String pattern, TimeZone timezone) {

		//Validacion del pattern...
		if(pattern == null || 
				(!pattern.contains("dd") 
				&& !pattern.contains("MM")
				&& !pattern.contains("yyyy")
				&& !pattern.contains("HH")
				&& !pattern.contains("mm")
				&& !pattern.contains("ss"))
			) {
			throw new ApplicationErrorException("Formato de fecha invalido: \"" + pattern + "\"");
		}
		
		String fechaFormateada = pattern;
		
		//Validacion de la fecha...
		Calendar calendario = Calendar.getInstance(timezone);		
		if(date != null)
			calendario.setTime(date);

		//Armado de la fecha formateada...
		int dia = calendario.get(Calendar.DATE);
		int mes = calendario.get(Calendar.MONTH)+1;
		int anio = calendario.get(Calendar.YEAR);
		int hora = calendario.get(Calendar.HOUR_OF_DAY);
		int minutos = calendario.get(Calendar.MINUTE);
		int segundos = calendario.get(Calendar.SECOND);
		
		String dd = (dia < 10 ? "0": "") + String.valueOf(dia);
		String mm = (mes < 10 ? "0": "") + String.valueOf(mes);
		String yyyy = String.valueOf(anio);
		
		String hh = (hora < 10 ? "0" : "") + String.valueOf(hora);
		String mins = (minutos < 10 ? "0" : "") + String.valueOf(minutos);
		String secs = (segundos < 10 ? "0" : "") + String.valueOf(segundos);
		
		//Reemplazo de caracteres...
		return fechaFormateada
					.replace("dd", dd)
					.replace("MM", mm)
					.replace("yyyy", yyyy)
					.replace("HH", hh)
					.replace("mm", mins)
					.replace("ss", secs);
	}
	
	/** 
	 * @author Fabian Benitez (fb70883)
	 * @version 8/11/2013
	 * 
	 * Arma y retorna el date actual segun el patron/formato y timezone definidos
	 *  
	 */
	
	public static String getFormattedDate(String pattern, TimeZone timezone) {
		Date date = null;
		return getFormattedDate(date, pattern, timezone);
	}
		
		
	/** 
	 * @author Fabian Benitez (fb70883)
	 * @version 10/04/2012
	 * 
	 * Arma y retorna el date actual segun el patron/formato y timezone definidos
	 *  
	 */
	
	public static String getFormattedDate(String pattern) {
		Date date = null;
		return getFormattedDate(date, pattern);
	}
	
	/** 
	 * @author Fabian Benitez (fb70883)
	 * @version 10/04/2012
	 * 
	 * Arma y retorna el date actual segun el patron/formato definido
	 *  
	 */
	public static Date getFormattedDate(String date, String pattern) {
		
		if(date == null)
			throw new ApplicationErrorException("Fecha especificada invalida");
		
		if(pattern == null)
			throw new ApplicationErrorException("Patron especificado invalido");
		
		try {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.parse(date);
		} catch (ParseException e) {
			throw new ApplicationErrorException("No se pudo obtener la fecha " + date + " con formato " + pattern, e);
		}
	}
	
	public static String compactText(String text, int maxSize) {
		String s = text;
		if(text.length() >= maxSize && maxSize > 10) {
			s = text.substring(0, maxSize-3) + "...";
		}			
		return s;
	}
}
