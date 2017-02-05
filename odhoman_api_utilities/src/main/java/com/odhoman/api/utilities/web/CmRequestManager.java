/**
 * Manejador del Request. Obtiene y convierte los valores del request
 */

package com.odhoman.api.utilities.web;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.odhoman.api.utilities.CmIOFormat;
import com.odhoman.api.utilities.UserSession;
import com.odhoman.api.utilities.security.filter.InputFilterManager;

public class CmRequestManager {

	private HttpServletRequest request = null;
	
	private InputFilterManager filterMgr = null;

	public CmRequestManager(HttpServletRequest request) {
		this(request, new InputFilterManager());
	}
	
	public CmRequestManager(HttpServletRequest request, InputFilterManager filterManager) {
		this.request = request;
		this.filterMgr = filterManager;
	}

	public Integer getInt(String propname) throws CmRequestManagerException {
		try {			
			return Integer.valueOf(filterMgr.filter(getString(propname)));
		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getInt. Propiedad: " + propname, e);
		}
	}

	public Double getDouble(String propname) throws CmRequestManagerException {
		// XXX implemtar mejor conversion
		try {
			String v = filterMgr.filter(getString(propname));
			if ("".equals(v))
				v = "0.0";
			v = v.replace(',', '.');
			return Double.valueOf(v);

		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getDouble. Propiedad: " + propname, e);
		}
	}

	public Double getDouble(String propname, double defv) throws CmRequestManagerException {
		try {
			String v = filterMgr.filter(getString(propname, ""));
			if ("".equals(v))
				return new Double(defv);
			v = v.replace(',', '.');
			return CmIOFormat.getDouble(v, '.');
		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getDouble. Propiedad: " + propname, e);			
		}
	}

	public Long getLong(String propname) throws CmRequestManagerException {
		try {
			return Long.valueOf(filterMgr.filter(getString(propname)));
		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getLong. Propiedad: " + propname, e);
		}
	}

	public Long getLong(String propname, long value) throws CmRequestManagerException {
		try {
			String v = filterMgr.filter(getString(propname, ""));
			return ("".equals(v)) ? new Long(value) : Long.valueOf(v);
		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getLong. Propiedad: " + propname, e);
		}
	}
	
	/** @date 3/09/2008 */
	
	public List<Long> getLongValues(String propname, long value) throws CmRequestManagerException {
		try {
			String [] v = request.getParameterValues(propname);
			List<Long> l = new ArrayList<Long>();
			if (v == null) {
				l.add(new Long(value));
				return l;
			} // end of if ()
			
			for(int i = 0; i < v.length; i++) {
				String s = filterMgr.filter(v[i].trim());
				l.add("".equals(s) ? 0L : new Long(s));
			}
			return l;
		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getLongValues. Propiedad: " + propname, e);
		}
	}

	public String getString(String propname) throws CmRequestManagerException {
		try {
			String v = filterMgr.filter(request.getParameter(propname));
			if (v == null)
				throw new Exception("No existe el parametro");
			return v.trim();
		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getString. Propiedad: " + propname, e);
		}
	}

	public String getString(String propname, String value) throws CmRequestManagerException {
		try {
			String v = filterMgr.filter(request.getParameter(propname));
			if (v == null)
				return value.trim();
			return v.trim();
		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getString. Propiedad: " + propname, e);			
		}
	}
	
	/** @date 3/09/2008 */
	
	public List<String> getStringValues(String propname, String [] value) throws CmRequestManagerException {
		try {
			String [] v = request.getParameterValues(propname);
			if (v == null) {
				return Arrays.asList(value == null ? new String[0] : value);
			} // end of if ()
			return filterMgr.filter(Arrays.asList(v));
		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getStringValues. Propiedad: " + propname, e);
		}
	}

	/**
	 * Toma string formato DD/MM/AAAA o DD/MM/AAAA HH:MM y devueve en Date()<br>
	 * Si la propiedad del request contiene un valor <code>null</code> o una cadena vacia, la funcion utiliza el valor
	 * del parametro <code>dafault</code>
	 */
	public java.util.Date getDate(String propname, String def) throws CmRequestManagerException {
		String s = filterMgr.filter(request.getParameter(propname));
		try {
			if (s == null || s.length() == 0)
				s = def;
			return getDateValue(s);
		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getDate. Propiedad: " + propname, e);
		}
	}

	/**
	 * Toma string formato DD/MM/AAAA o DD/MM/AAAA HH:MM y devueve en Date()<br>
	 */
	public java.util.Date getDate(String propname) throws CmRequestManagerException {
		String s = filterMgr.filter(request.getParameter(propname));
		try {
			return getDateValue(s);
		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getDate. Propiedad: " + propname, e);
		}
	}

	// Toma string formato DD/MM/AAAA o DD/MM/AAAA HH:MM
	// y devueve en Date().
	private java.util.Date getDateValue(String value) throws Exception {
		return CmIOFormat.Str2Date(value);
	}

	// Toma string formato DD/MM/AAAA o DD/MM/AAAA HH:MM
	// y devueve en Date().
	public java.util.Date getDateAN(String propname) throws CmRequestManagerException {
		Calendar cal = Calendar.getInstance();
		String s = filterMgr.filter(request.getParameter(propname));

		int t0 = 0, t1 = 0, h24 = 0, mi = 0, ss = 0, mili = 0, ph0 = 0, ph1 = 0, ph2 = 0, ph3 = 0;
		String d = "", m = "", y = "";

		try {
			if (s == null)
				s = "";
			s = s.trim();

			// si cadena vacia, define que 01/01/1980 es una fecha nula
			if (s.length() == 0)
				s = "01/01/1900";

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
		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getDate. Propiedad: " + propname, e);
		}
	}

	/** Convierte StringDate en Date */
	public java.util.Date getStrD(String propname) throws CmRequestManagerException {
		try {			
			return CmIOFormat.Str2Date(getString(propname));
		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getDate. Propiedad: " + propname, e);			
		}
	}

	/* getAttributes */
	
	public String getAttributeString(String propname) throws CmRequestManagerException {
		try {
			String v = filterMgr.filter((String) request.getAttribute(propname));
			return v == null ? "" : v.trim();
		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getAttributeString. Attribute: " + propname, e);			
		}
	}
	
	public int getAttributeInt(String propname) throws CmRequestManagerException {
		try {
			String v = getAttributeString(propname);
			return "".equals(v) ? 0 : Integer.parseInt(v);
		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getAttributeInt. Attribute: "	+ propname, e);			
		}
	}

	public double getAttributeDouble(String propname) throws CmRequestManagerException {
		try {
			String v = getAttributeString(propname);
			return "".equals(v) ? 0.0 : Double.parseDouble(v);
		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getAttributeDouble. Attribute: " + propname, e);			
		}
	}

	public long getAttributeLong(String propname) throws CmRequestManagerException {
		try {
			String v = getAttributeString(propname);
			return "".equals(v) ? 0 : Long.parseLong(v);
		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getAttributeLong. Attribute: " + propname, e);			
		}
	}

	public java.util.Date getAttributeDate(String propname) throws CmRequestManagerException {
		try {
			String v = getAttributeString(propname);
			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, new Locale("es", "ES"));
			if (v == null)
				v = "01/01/1969";
			return df.parse(v);
		} catch (Exception e) {
			throw new CmRequestManagerException("Error en getAttributeDate. Attribute: " + propname, e);			
		}
	}
	
	/**
	 * Devuelve la sesi�n del usuario
	 * @param request Request
	 * @return La sesi�n del usuario
	 */
	
	public static UserSession getUserSession(HttpServletRequest request) {
		return (UserSession)request.getSession(true).getAttribute("usTemp");
	}

}
