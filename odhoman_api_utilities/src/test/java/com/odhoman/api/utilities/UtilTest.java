package com.odhoman.api.utilities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;

import com.odhoman.api.utilities.security.filter.InputFilterManager;

public class UtilTest {

	@Test
	public void replacements() {
		String str = "<script>alert('hola')</script>";
		System.out.println(str);
		String str2 = str.replaceAll("<script>", ""); 
		System.out.println(str2);
		assertFalse(str2.contains("<script>"));
		
		String filteredInput = new InputFilterManager().filter(str);
		System.out.println("FilteredInput: " + filteredInput);
		assertTrue(filteredInput.equals("alert('hola')"));
	}
	
	@Test
	public void uuid() {
		UUID id = UUID.randomUUID();
		System.out.println("id generado (bits mas significativos): " + id.getMostSignificantBits());
		System.out.println("id generado (bits menos significativos): " + id.getLeastSignificantBits());
		
		if(id.getMostSignificantBits() < 0) {
			System.out.println("id generado (bits mas significativos +): " + id.getMostSignificantBits()*(-1));
		}
	}
	
}
