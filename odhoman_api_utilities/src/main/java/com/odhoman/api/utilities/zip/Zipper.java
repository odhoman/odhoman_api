package com.odhoman.api.utilities.zip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.odhoman.api.utilities.CmIOFormat;

/**
 * 
 *  Comprime / descomprime files en base a varios parametros.
 *  
 *  Posible ejecucion via jar.
 *  
 *  Puede ir procesando los files a comprimir en base a ciertos criterios definidos 
 *  o bien tomar una lista ya dada de files sobre los cuales trabajar.
 *   
 *  @author Fabian Benitez (fb70883)
 *  @date MAY-2015
 * 
 */

public class Zipper {

	private List<String> exceptions;
	
	private static final String ACTION_ZIP = "ZIP";
	private static final String ACTION_ZIPS = "ZIPS";
	private static final String ACTION_UNZIP = "UNZIP";
	
	public static void main(String[] args) throws Throwable {

		//TOTAL PARAMS: 1 + 2/3 obligatorios (min 3/4) + 2/3 opcionales (max 5/7)
		
		if(args == null || args.length < 3 || args.length > 7)
			throw new RuntimeException("Zipper: Cantidad de parametros incorrecta.");
		
		//Obtencion de la accion deseada...
		
		String action = args[0].trim();
		
		if(action == null || action.isEmpty() || 
			(!ACTION_ZIP.equalsIgnoreCase(action) && !ACTION_ZIPS.equalsIgnoreCase(action) && !ACTION_UNZIP.equalsIgnoreCase(action)))
			
			throw new RuntimeException("Zipper: La accion solicitada es incorrecta: " + action);
		
		//Se crea el zipper correspondiente...
		
		Zipper zipper = new Zipper();
		
		if(ACTION_ZIP.equalsIgnoreCase(action) || ACTION_ZIPS.equalsIgnoreCase(action)) {
						
			zipper.zip(action, args);
			
		} else if(ACTION_UNZIP.equalsIgnoreCase(action)) {
			
			zipper.unzip(action, args);
			
		} else {
			throw new RuntimeException("Zipper: La accion solicitada es incorrecta: " + action);
		}
		
	}
	
	public Zipper() {
		exceptions = new ArrayList<String>();
	}
	
	public List<String> getExceptions() {
		return Collections.unmodifiableList(exceptions);
	}
	
	public void clearExceptions() {
		exceptions.clear();
	}
	
	/** Obtiene los parametros y ejecuta el zip correspondiente */
	
	private void zip(String action, String [] args) throws Throwable {
		
		//CreateZipFile: 6 params (3 opcionales)
		//CreateZipFiles: 6 params (3 opcionales)

		if(args.length < 4)
			throw new RuntimeException("Zipper: Cantidad de parametros incorrecta para la accion " + action);
		
		String zipTargetFolder = args[1].trim();
		String zipFileName = args[2].trim();
		String srcFilePath = args[3].trim();
		
		List<String> includes = null;
		List<String> excludes = null;
		SelectionCriteria criterio = null;
		
		if(args.length > 4) {
			String list = args[4].trim(); 
			
			if(!list.isEmpty() && !"NULL".equalsIgnoreCase(list))
				includes = Arrays.asList(list.split(","));
			
			if(args.length > 5) {
				list = args[5].trim(); 
				
				if(!list.isEmpty() && !"NULL".equalsIgnoreCase(list))
					excludes = Arrays.asList(list.split(","));
				
				if(args.length > 6) {
					String c = args[6].trim();
					
					if(!c.isEmpty() && !"NULL".equalsIgnoreCase(c))
						criterio = new SelectionCriteria(c);
				}
			}
		}
		
		if("ZIP".equalsIgnoreCase(action))
			createZipFile(zipTargetFolder, zipFileName, srcFilePath, includes, excludes, criterio);
		else
			createZipFiles(zipTargetFolder, zipFileName, srcFilePath, includes, excludes, criterio);
		
		System.out.println("Zipper - Exceptions: " + getExceptions());
	}
	
	/** Obtiene los parametros y ejecuta el unzip correspondiente */
	
	private void unzip(String action, String [] args) throws Throwable {
		
		//UnzipFile: 4 params (2 opcionales)
		//UnzipFiles: 4 params (2 opcionales)
		
		if(args.length > 5)
			throw new RuntimeException("Zipper: Cantidad de parametros incorrecta para la accion " + action);
		
		String zipFileNames = args[1].trim();		
		String zipTargetPath = args[2].trim();
				
		List<String> includes = null;
		List<String> excludes = null;
		//SelectionCriteria criterio = null;
		
		if(args.length > 3) {
			String list = args[3].trim(); 
			
			if(!list.isEmpty() && !"NULL".equalsIgnoreCase(list))
				includes = Arrays.asList(list.split(","));
			
			if(args.length > 4) {
				list = args[4].trim(); 
				
				if(!list.isEmpty() && !"NULL".equalsIgnoreCase(list))
					excludes = Arrays.asList(list.split(","));
			}
		}
		
		if(zipFileNames.contains(","))	//Se supone que hay mas de un zip a descomprimir...
			unzipFiles(Arrays.asList(zipFileNames.split(",")), zipTargetPath, includes, excludes);
		else
			unzipFile(zipFileNames, zipTargetPath, includes, excludes);
		
		System.out.println("Zipper - Exceptions: " + getExceptions());
		
	}
	
	/** Crea un zip con cada directorio contenido en el path especificado a excepcion de los excluidos o bien incluyendo a los incluidos */
	
	public File createZipFile(
					String zipTargetFolder, 
					String zipFileName, 
					String srcFilePath, 
					List<String> includes, 
					List<String> excludes) throws Throwable {
		
		return createZipFile(zipTargetFolder, zipFileName, getSrcFilePaths(srcFilePath, includes, excludes), null);
		
	}
	
	/** Crea un zip con cada directorio contenido en el path especificado a excepcion de los excluidos o bien incluyendo a los incluidos */
	
	public File createZipFile(
					String zipTargetFolder, 
					String zipFileName, 
					String srcFilePath, 
					List<String> includes, 
					List<String> excludes,
					SelectionCriteria selectionCriteria) throws Throwable {
		
		return createZipFile(zipTargetFolder, zipFileName, getSrcFilePaths(srcFilePath, includes, excludes), selectionCriteria);
		
	}
	
	/** Crea un zip con cada file especificado en la lista de paths */
	
	public File createZipFile(
					String zipFilePath,					
					List<String> srcFilePaths) throws Throwable {
		
		return createZipFile(zipFilePath, srcFilePaths, null);
		
	}
	
	/** Crea un zip por cada directorio contenido en el path especificado a excepcion de los excluidos o bien incluyendo a los incluidos */
	
	public void createZipFiles(
					String zipTargetFolder,
					String zipFileNameSuffix,
					String srcFilePath, 
					List<String> includes, 
					List<String> excludes) throws Throwable {
		
		createZipFiles(zipTargetFolder, zipFileNameSuffix, srcFilePath, includes, excludes, null);
		
	}
	
	/** Crea un zip por cada directorio contenido en el path especificado a excepcion de los excluidos o bien incluyendo a los incluidos */
	
	public void createZipFiles(
					String zipTargetFolder,
					String zipFileNameSuffix,
					String srcFilePath, 
					List<String> includes, 
					List<String> excludes,
					SelectionCriteria selectionCriteria) throws Throwable {
		
		List<String> paths = getSrcFilePaths(srcFilePath, includes, excludes);
		
		String zipFileName = null;
		for(String path: paths) {
			
			zipFileName = zipFileNameSuffix + path.substring(path.lastIndexOf(File.separator) + 1);
					
			createZipFile(zipTargetFolder, zipFileName, Arrays.asList(path), null);
		}
		
	}
	
	/** Devuelve la lista de paths a procesar en funcion de un origen y una lista de exclusiones */
	
	private List<String> getSrcFilePaths(String srcFilePath, List<String> includes, List<String> excludes) throws Throwable {
		
		File srcFile = new File(srcFilePath);
		
		if(!srcFile.exists() || !srcFile.isDirectory() || !srcFile.canRead())
			throw new IOException("srcFile '" + srcFilePath + "' invalid");
		
		List<String> subItems = Arrays.asList(srcFile.list());
		
		System.out.println("Items in '" + srcFilePath + "': " + subItems);
		
		System.out.println("Including files: " + includes);
		
		System.out.println("Excluding files: " + excludes);
		
		List<String> finalList = new ArrayList<String>();
		for(String subitem: subItems) {
			
			if(excludes != null && !excludes.isEmpty()) {
			
				if(excludes.contains(subitem))
					continue;
				else 
					finalList.add(srcFilePath + File.separator + subitem);
				
			} else if(includes != null && !includes.isEmpty()) {
				
				if(includes.contains(subitem))
					finalList.add(srcFilePath + File.separator + subitem);
				else 
					continue;
			} else {
				
				finalList.add(srcFilePath + File.separator + subitem);
			}
		}
		
		System.out.println("Final list of items to be compressed: " + finalList);
		
		return finalList;
	}

	/** Crea un zip con el nombre especificado a partir de paths definidos */
	
	private File createZipFile(String zipTargetFolder, String zipFileName, List<String> srcFilePaths, SelectionCriteria criteria) throws Throwable {
		
		String zipFilePath = zipTargetFolder + CmIOFormat.getFormattedDate("yyyyMMdd_") + zipFileName;
		
		return createZipFile(zipFilePath, srcFilePaths, criteria);
		
	}
	
	/** Crea un zip con el nombre especificado a partir de paths definidos */
	
	private File createZipFile(String zipFilePath, List<String> srcFilePaths, SelectionCriteria criteria) throws Throwable {
		
		File zipFile = new File(zipFilePath + ".zip");
		
		FileOutputStream fos = new FileOutputStream(zipFile);
		ZipOutputStream zos = new ZipOutputStream(fos);
		
		zos.setComment("Created by fb70883 on " + CmIOFormat.getFormattedDate("dd/MM/yyyy HH:mm:ss"));
		//zos.setLevel(level);
		//zos.setMethod(method)
		
		for(String srcFilePath : srcFilePaths) {
			addZipEntry(zos, srcFilePath, criteria);
		}
		
		zos.close();
		fos.close();
		
		System.out.println("*** ZIP " + zipFile.getName() + " creado correctamente (" + zipFile.length()/(1024*1024) + "Mb)");
		
		return zipFile;
		
	}
	
	/** Agrega una entrada nueva al zip. Si es un directorio, continua hacia su contenido. */
	
	private void addZipEntry(ZipOutputStream zos, String srcFilePath, SelectionCriteria criteria) throws Throwable {
		
		File f = new File(srcFilePath);
		
		System.out.println("srcFilePath=" + f.getCanonicalFile() + " - type=" + (f.isDirectory() ? "dir" : "file"));
		
		if(!f.canRead()) {
			exceptions.add(f.getCanonicalPath());
			return;
		}			

		if(f.isFile()) {
			
			if(!isSelected(criteria, f)) {
				return;
			}
			
			ZipEntry zipEntry = new ZipEntry(srcFilePath);
			
			zos.putNextEntry(zipEntry);
			
			try {
				
				FileInputStream fis = new FileInputStream(f);
				
				write(fis, zos);
				
			} catch (Exception e) {
				System.out.println("Exception al intentar grabar el archivo");
				e.printStackTrace();
				exceptions.add(f.getCanonicalPath());
			}
			
		} else {	//directory
			
			List<String> fileList = Arrays.asList(f.list());
			
			System.out.println("Content of " + srcFilePath + ": " + fileList);
			
			for(String item : fileList) {
				addZipEntry(zos, srcFilePath + File.separator + item, criteria);
			}
			
		}
	}
	
	/** Determina si un file es seleccionado o no */
	
	private boolean isSelected(SelectionCriteria criteria, File f) {
		
		if(criteria == null) 
			return true;
		
		if(!criteria.getFilenameKeywords().isEmpty()) {
			
			String filename = f.getPath();
			
			for(String k : criteria.getFilenameKeywords()) {
				if(filename.contains(k)) {
					return false;
				}
			}
			
		}
		
		boolean selected = false;
		
		if(criteria.getFechaHoraAltaDesde() != null || criteria.getFechaHoraAltaHasta() != null) {
			
			try {
				Long creationTime = getCreationTime(f);
				
				if(criteria.getFechaHoraAltaDesde() != null) {
					selected = selected || criteria.getFechaHoraAltaDesde().getTime() <= creationTime;
				}
				
				if(criteria.getFechaHoraAltaHasta() != null) {
					selected = selected || criteria.getFechaHoraAltaHasta().getTime() >= creationTime;
				}
				
				if(selected)
					return true;
				
			} catch (Exception e) {
			}
						
		}
		
		if(criteria.getFechaHoraModificacionDesde() != null || criteria.getFechaHoraModificacionHasta() != null) {
			
			Long lastModified = f.lastModified();
			
			if(criteria.getFechaHoraModificacionDesde() != null) {
				selected = selected || criteria.getFechaHoraModificacionDesde().getTime() <= lastModified;
			}
			
			if(criteria.getFechaHoraModificacionHasta() != null) {
				selected = selected || criteria.getFechaHoraModificacionHasta().getTime() >= lastModified;
			}
			
			if(selected)
				return true;
			
		}		
		
		return selected;
	}
	
	/** Devuelve la fecha hora de creacion del file especificado */
	
	private Long getCreationTime(File f) throws Exception {
		
		String command = "cmd /c dir \"" + f.getCanonicalPath() + "\" /tc";
		
		Process process = Runtime.getRuntime().exec(command);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		String data = "";
		
		for(int i=0; i < 6; i++) {
			data = br.readLine();	//la 6ta linea contiene el dato necesario
		}
		br.close();
		
		StringTokenizer st = new StringTokenizer(data);
		
		String date = st.nextToken();
		//String time = st.nextToken(); //0303
		//String ampm = st.nextToken(); //am o pm => sumar 1200 al time cuando sea pm
		
		return CmIOFormat.getFormattedDate(date, "dd/MM/yyyy").getTime();
		
	}
	
	/** Descomprime una lista de archivos zip */
	
	public void unzipFiles(
						List<String> zipFileNames, 
						String targetPath, 
						List<String> includes, 
						List<String> excludes) throws Throwable {
		
		for(String zipFile : zipFileNames) {
			
			unzipFile(
				zipFile, 
				targetPath, 
				includes, 
				excludes);
		}
		
	}
	
	/** Descomprime un archivo zip */
	
	public void unzipFile(
						String zipFileName, 
						String targetPath, 
						List<String> includes, 
						List<String> excludes) throws Throwable {
		
		//Se valida que el zip file exista y sea valido...
		
		File zipFile = new File(zipFileName);
		
		if(!zipFile.exists() || !zipFile.isFile() || !zipFile.canRead())
			throw new IOException("Archivo zip " + zipFileName + " invalido");
		
		//Se valida que el directorio destino exista...
		
		File target = new File(targetPath);
		
		if(!target.exists())
			target.mkdir();
		
		//Se procesan las entradas del zip...
		
		FileInputStream fis = new FileInputStream(zipFile);
		ZipInputStream zis = new ZipInputStream(fis);
		
		ZipEntry zipEntry = zis.getNextEntry();
		
		while(zipEntry != null) {
			
			processZipEntry(zis, targetPath, zipEntry, includes, excludes);
			
			zipEntry = zis.getNextEntry();
		}
		
		zis.close();
		fis.close();
		
		System.out.println("*** ZIP " + zipFile.getName() + " procesado correctamente");
		
	}
	
	/** Procesa cada entrada del zip y evalua si se descomprimira o no */
	
	private void processZipEntry(
					ZipInputStream zis,
					String targetPath,
					ZipEntry zipEntry, 
					List<String> includes, 
					List<String> excludes) throws Throwable {
		
		String entryName = zipEntry.getName();
		
		System.out.println("EntryName: " + entryName);
			
		//Se filtran aquellos deseados / no deseados...
		
		if(excludes != null && !excludes.isEmpty()) {
				
			for(String exclude : excludes) {
				if(entryName.contains(exclude))
					return;
			}
			
		} 
		
		if(includes != null && !includes.isEmpty()) {

			boolean exists = false;
			
			for(String include : includes) {
				if(entryName.contains(include)) {
					exists = true;
					break;
				}
			}
			
			if(!exists)
				return;
			
		}
		
		//Se intenta crear el file...
		
		File f = new File(targetPath + File.separator + entryName);
		
		if(zipEntry.isDirectory()) {
			
			return;
			
		} else {	//Es un file...
			
			System.out.println("Unzipping file in " + f.getAbsolutePath());
			
			//Se crean los directorios intermedios...
			
			new File(f.getParent()).mkdirs();
			
			FileOutputStream fos = new FileOutputStream(f);
			
			try {
				write(zis, fos);
			} catch (Exception e) {
				System.out.println("Exception al intentar grabar el archivo");
				e.printStackTrace();
				exceptions.add(f.getCanonicalPath());
			}
		}
		
	}
	
	/** Realiza el ciclo de lectura / escritura de buffers y luego cierra los streams (haya o no errores) */
	
	private void write(InputStream is, OutputStream os) throws Throwable {
		
		byte [] buffer = new byte[8192];	//buffer de 8kb

		int readBytes = 0;
		
		try {
		
			while((readBytes = is.read(buffer)) != -1) {
				os.write(buffer, 0, readBytes);				
			}
		
		} catch (Throwable t) {
			
			System.out.println("No se pudo grabar el stream especificado");			
			throw t;
			
		} finally {
		
			if(is instanceof ZipInputStream)
				((ZipInputStream) is).closeEntry();
			else
				is.close();
			
			if(os instanceof ZipOutputStream)
				((ZipOutputStream) os).closeEntry();
			else {
				os.flush();
				os.close();
			}
				
		}
		
	}

}
