package com.odhoman.api.utilities.zip;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odhoman.api.utilities.CmIOFormat;

public class ZipperTest {

	private Zipper zipper = null;
	
	@Before
	public void createZipper() {
		zipper = new Zipper();
	}
	
	@After
	public void deleteZipper() {
		
		zipper.clearExceptions();
		zipper = null;
	}
	
	@Test
	public void createZip() throws Throwable {
		
		zipper.createZipFile(
				"C:/temp/", 
				"oracle", 
				"C:/Oracle", 
				null,
				null);
		
		System.out.println("Exceptions: " + zipper.getExceptions());
		
		assertTrue(true);
		
	}
	
	@Test
	public void createZips() throws Throwable {
		
		zipper.createZipFiles(
				"C:/temp/",
				"psts",
				"C:/Documents and Settings/fb70883/My Documents/Mails - Outlook/2005 - 2011",
				null,
				null);
		
		System.out.println("Exceptions: " + zipper.getExceptions());
		
		assertTrue(true);
	}
	
	@Test 
	public void createZipForUpdatedFiles() throws Throwable {
		
		SelectionCriteria selectionCriteria = new SelectionCriteria();
		selectionCriteria.setFechaHoraAltaDesde(CmIOFormat.getFormattedDate("21042015", "ddMMyyyy"));
		selectionCriteria.setFechaHoraModificacionDesde(CmIOFormat.getFormattedDate("21042015", "ddMMyyyy"));
		selectionCriteria.addFilenameKeyword("Mails - Outlook");
		selectionCriteria.addFilenameKeyword("My Music");
		
		System.out.println(selectionCriteria);
		
		zipper.createZipFile(
				"C:/temp/", 
				"updates1", 
				"C:/desarrollo", 
				null,
				null,
				selectionCriteria);
		
		zipper.createZipFile(
				"C:/temp/", 
				"updates2", 
				"C:/Documents and Settings/fb70883", 
				null,
				null,
				selectionCriteria);
		
		System.out.println("Exceptions: " + zipper.getExceptions());
		
		assertTrue(true);
		
	}
	
	@Test
	public void unzipFile() throws Throwable {
		
		zipper.unzipFile(
			"C:/temp/unzip/gcats-20120618.zip", 
			"C:/temp/unzip/content2", 
			Arrays.asList("gcats-core"), 
			Arrays.asList(".metadata", "target", "build", "CVS", ".settings",".cvsignore"));
		
		System.out.println("Exceptions: " + zipper.getExceptions());
		
		assertTrue(true);
	}
	
	@Test
	public void unzipFiles() throws Throwable {
		
		List<String> zipFiles = new ArrayList<String>();
		zipFiles.add("C:/temp/unzip/gcats-20120618.zip");
		zipFiles.add("C:/temp/unzip/indigo64.zip");
		zipFiles.add("C:/temp/unzip/fruta.zip");
		
		zipper.unzipFiles(
				zipFiles, 
				"C:/temp/unzip/content2",
				Arrays.asList("gcats-core", "features"),
				Arrays.asList(".metadata", "target", "build", "CVS", ".settings",".cvsignore"));
		
		System.out.println("Exceptions: " + zipper.getExceptions());
		
		assertTrue(true);
	}
	
}
