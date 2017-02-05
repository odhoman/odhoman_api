package com.odhoman.api.utilities;

import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odhoman.api.utilities.transac.ApplicationErrorException;

/**
 *  @author Fabian Benitez (fb70883)
 *  @date 21/11/2011
 *  
 *  Test unitario del CmIOFormat
 * 
 */

public class CmIOFormatTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test 
	public void doubleToStr_replaceCharacters() {
		String s = "1,235.457%4512,12";
		Map<Character,Character> replacements = new HashMap<Character, Character>();
		replacements.put(',', '*');
		replacements.put('.', '*');
		replacements.put('%', '*');
		String ns = CmIOFormat.replaceCharacters(s, replacements);
		System.out.println("### doubleToStr_replaceCharacters ###: original= " + s + " modificado=" + ns);
		assertTrue("1*235*457*4512*12".equals(ns));		
	}
	
	@Test 
	public void doubleToStr_forcedToCommas() {
		Double d = 11231231.45d;
		String s = CmIOFormat.Double2StrForcedToCommas(d);
		System.out.println("### doubleToStr_forcedToCommas ###: original= " + d + " modificado=" + s);
		assertTrue("11231231,45".equals(s));		
	}
	
	@Test 
	public void doubleToStr_localeEnglish() {
		Double d = 11231231.45d;
		String s = CmIOFormat.Double2Str(d);
		System.out.println("### doubleToStr_localeEnglish ###: original= " + d + " modificado=" + s);		
		assertTrue("11231231.45".equals(s));
	}
	
	@Test 
	public void getFormattedDate() {
		Date d1 = Calendar.getInstance().getTime();
		System.out.println("Formatted Date 1: " + CmIOFormat.getFormattedDate(d1, "dd-MM-yyyy HH:mm"));
				
		Date d2 = Calendar.getInstance().getTime();
		System.out.println("Formatted Date 2: " + CmIOFormat.getFormattedDate(d2, "A�o yyyy, estamos en el dia dd del mes MM"));
		
		System.out.println("Formatted Date 3: " + CmIOFormat.getFormattedDate("A�o yyyy, estamos en el dia dd del mes MM"));
		
		try {
			System.out.println("Formatted Date 4: " + CmIOFormat.getFormattedDate("Este no es un formato valido..."));
			assertTrue(false);
		} catch (ApplicationErrorException e) {
			e.printStackTrace(System.out);
			assertTrue(true);
		}
		
		System.out.println("Formatted Date 5: " + CmIOFormat.getFormattedDate("Pasaron mm minutos y ss segundos de la hora HH"));
		
		System.out.println("Formatted Date 6: " + CmIOFormat.getFormattedDate("18/05/1994 20:15:34", "dd/MM/yyyy HH:mm:ss"));
		
		System.out.println("Formatted Date 7: " + CmIOFormat.getFormattedDate(
					CmIOFormat.getFormattedDate("18/05/1994 20:15:34", "dd/MM/yyyy HH:mm:ss"),
					"Pasaron mm minutos y ss segundos de la hora HH del dia dd del mes MM del a�o yyyy")
				);
	}
	
	@Test
	public void getFormattedDateTimezones() {
		
		Date d1 = Calendar.getInstance().getTime();
		
		System.out.println(TimeZone.getDefault());
		System.out.println("DEFAULT: " + CmIOFormat.getFormattedDate(d1, "dd/MM/yyyy HH:mm:ss"));
	
		System.out.println(TimeZone.getTimeZone("UTC"));
		System.out.println("UTC: " + CmIOFormat.getFormattedDate(d1, "dd/MM/yyyy HH:mm:ss", TimeZone.getTimeZone("UTC")));		//UTC
		
		System.out.println(TimeZone.getTimeZone("ART"));
		System.out.println("ART: " + CmIOFormat.getFormattedDate(d1, "dd/MM/yyyy HH:mm:ss", TimeZone.getTimeZone("ART")));		//Argentina
		
		System.out.println(TimeZone.getTimeZone("BRT"));
		System.out.println("BRT: " + CmIOFormat.getFormattedDate(d1, "dd/MM/yyyy HH:mm:ss", TimeZone.getTimeZone("BRT")));		//Brasilea
		
		System.out.println(TimeZone.getTimeZone("ADT"));
		System.out.println("ADT: " + CmIOFormat.getFormattedDate(d1, "dd/MM/yyyy HH:mm:ss", TimeZone.getTimeZone("ADT")));		//Atlantico
		
		TimeZone myTz = TimeZone.getTimeZone("UTC");
		myTz.setID("TDU_TimeZone");
		myTz.setRawOffset(-10800000);
		
		System.out.println(myTz);
		System.out.println("CUSTOM: " + CmIOFormat.getFormattedDate(d1, "dd/MM/yyyy HH:mm:ss", myTz));		//Custom
		
	}
	
}
