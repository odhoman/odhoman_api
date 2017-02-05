package com.odhoman.api.utilities;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.odhoman.api.utilities.transac.ApplicationErrorException;
import com.odhoman.api.utilities.transac.ApplicationWarningException;
import com.odhoman.api.utilities.transac.ErrorCode;
import com.odhoman.api.utilities.transac.TransactionStatus;
import com.odhoman.api.utilities.transac.TransactionStatusImpl;

/**
 *  @author Fabian Benitez (fb70883)
 *  @date 9/03/2010
 *  
 *  Test unitario del CmError
 * 
 */

public class CmErrorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	private void loadError(String name, String errorCode) {
		CmError.addModelError(name, errorCode, "ERROR " + errorCode + " EN LA TRANSACCION " + name.toUpperCase());		
	}
	
	private void loadWarning(String name, String errorCode) {
		CmError.addModelWarning(name, errorCode, "WARNING " + errorCode + " EN LA TRANSACCION " + name.toUpperCase());		
	}
	
	/** 
	 *  Verifica el funcionamiento del checkTransaction del CmError para una transaccion dada 
	 *  cuando la transaccion tiene solo un error.	
	 */
	
	@Test
	public void checkTransaction_singleError() {
		System.out.println("\nEjecutando test checkTransaction_singleError...");
		try {
			String transactionName = "SetActTransaccionalProfileLim";
			String errorCode = "007";			
		
			loadError(transactionName, errorCode);
	
			//Armado del TransactionStatus
			ErrorCode e = new ErrorCode(errorCode);
			TransactionStatus status = new TransactionStatusImpl(false, e);
			CmError.checkTransaction(transactionName, status);
		} catch (ApplicationErrorException e1) {
			e1.printStackTrace(System.out);
			System.out.println("Errores: " + e1.getErrors());
			assertTrue(e1.getErrors().size() == 1);
			System.out.println("Detailed Msg: " + e1.getDetailedMessage());
			System.out.println("Detailed Msg with <br>: " + e1.getDetailedMessage().replace("\r", "<br>"));
			System.out.println("User Msg: " + e1.getUserMessage());
		} catch (ApplicationWarningException e2) {
			e2.printStackTrace(System.out);
			System.out.println("Warnings: " + e2.getWarnings());
			assertTrue(false);
		}		
	}
	
	/** 
	 *  Verifica el funcionamiento del checkTransaction del CmError para una transaccion dada 
	 *  cuando la transaccion tiene solo un warning.	 
	 */
	
	@Test
	public void checkTransaction_singleWarning() {
		System.out.println("\nEjecutando test checkTransaction_singleWarning...");
		try {
			String transactionName = "SetActTransaccionalProfileLim";
			String errorCode = "150";			
		
			loadWarning(transactionName, errorCode);
					
			//Armado del TransactionStatus
			ErrorCode e = new ErrorCode(errorCode);
			TransactionStatus status = new TransactionStatusImpl(false, e);
			CmError.checkTransaction(transactionName, status);
		} catch (ApplicationErrorException e1) {
			e1.printStackTrace(System.out);
			System.out.println("Errores: " + e1.getErrors());
			assertTrue(false);
		} catch (ApplicationWarningException e2) {
			e2.printStackTrace(System.out);
			System.out.println("Warnings: " + e2.getWarnings());
			assertTrue(e2.getWarnings().size() == 1);
			System.out.println("Detailed Msg: " + e2.getDetailedMessage());			
		}		
	}
	
	/** 
	 *  Verifica el funcionamiento del checkTransaction del CmError para una transaccion dada 
	 *  cuando la transaccion tiene varios errores.	
	 */
	
	@Test
	public void checkTransaction_multipleErrors() {
		System.out.println("\nEjecutando test checkTransaction_multipleErrors...");
		try {
			String transactionName = "SetActTransaccionalProfileLim";
			String errorCode1 = "007";			
			String errorCode2 = "008";
			String errorCode3 = "009";
			String errorCode4 = "010";
		
			loadError(transactionName, errorCode1);
			loadError(transactionName, errorCode2);
			loadError(transactionName, errorCode3);
			loadError(transactionName, errorCode4);
	
			//Armado del TransactionStatus
			ErrorCode e1 = new ErrorCode(errorCode1);
			ErrorCode e2 = new ErrorCode(errorCode2);
			ErrorCode e3 = new ErrorCode(errorCode3);
			ErrorCode e4 = new ErrorCode(errorCode4);
			List<ErrorCode> errors = new ArrayList<ErrorCode>();
			errors.add(e1);
			errors.add(e2);
			errors.add(e3);
			errors.add(e4);
			TransactionStatus status = new TransactionStatusImpl(false, errors);
			CmError.checkTransaction(transactionName, status);
		} catch (ApplicationErrorException e1) {
			e1.printStackTrace(System.out);
			System.out.println("Errores: " + e1.getErrors());
			assertTrue(e1.getErrors().size() == 4);
			System.out.println("Detailed Msg: " + e1.getDetailedMessage());
			System.out.println("User Msg: " + e1.getUserMessage());
		} catch (ApplicationWarningException e2) {
			e2.printStackTrace(System.out);
			System.out.println("Warnings: " + e2.getWarnings());
			assertTrue(false);
		}		
	}
	
	/** 
	 *  Verifica el funcionamiento del checkTransaction del CmError para una transaccion dada 
	 *  cuando la transaccion tiene varios warnings.	 
	 */
	
	@Test
	public void checkTransaction_multipleWarnings() {
		System.out.println("\nEjecutando test checkTransaction_multipleWarnings...");
		try {
			String transactionName = "SetActTransaccionalProfileLim";
			String errorCode1 = "150";			
			String errorCode2 = "160";
			String errorCode3 = "170";
			String errorCode4 = "180";
		
			loadWarning(transactionName, errorCode1);
			loadWarning(transactionName, errorCode2);
			loadWarning(transactionName, errorCode3);
			loadWarning(transactionName, errorCode4);
					
			//Armado del TransactionStatus
			ErrorCode e1 = new ErrorCode(errorCode1);
			ErrorCode e2 = new ErrorCode(errorCode2);
			ErrorCode e3 = new ErrorCode(errorCode3);
			ErrorCode e4 = new ErrorCode(errorCode4);
			List<ErrorCode> errors = new ArrayList<ErrorCode>();
			errors.add(e1);
			errors.add(e2);
			errors.add(e3);
			errors.add(e4);
			TransactionStatus status = new TransactionStatusImpl(false, errors);
			CmError.checkTransaction(transactionName, status);
		} catch (ApplicationErrorException e1) {
			e1.printStackTrace(System.out);
			System.out.println("Errores: " + e1.getErrors());
			assertTrue(false);
		} catch (ApplicationWarningException e2) {
			e2.printStackTrace(System.out);
			System.out.println("Warnings: " + e2.getWarnings());
			assertTrue(e2.getWarnings().size() == 4);
			System.out.println("Detailed Msg: " + e2.getDetailedMessage());			
		}		
	}
	
	/** 
	 *  Verifica el funcionamiento del checkTransaction del CmError para una transaccion dada 
	 *  cuando la transaccion tiene un error y un warning. El error prevalece a pesar del warning 	
	 */
	
	@Test
	public void checkTransaction_ErrorYWarning() {
		System.out.println("\nEjecutando test checkTransaction_ErrorYWarning...");
		try {
			String transactionName = "SetActTransaccionalProfileLim";
			String errorCode1 = "007";
			String errorCode2 = "150";			
		
			loadError(transactionName, errorCode1);
			loadWarning(transactionName, errorCode2);
	
			//Armado del TransactionStatus
			ErrorCode e1 = new ErrorCode(errorCode1);
			ErrorCode e2 = new ErrorCode(errorCode2);
			List<ErrorCode> errors = new ArrayList<ErrorCode>();
			errors.add(e1);
			errors.add(e2);			
			TransactionStatus status = new TransactionStatusImpl(false, errors);
			CmError.checkTransaction(transactionName, status);
		} catch (ApplicationErrorException e1) {
			e1.printStackTrace(System.out);
			System.out.println("Errores: " + e1.getErrors());
			assertTrue(e1.getErrors().size() == 1);
		} catch (ApplicationWarningException e2) {
			e2.printStackTrace(System.out);
			System.out.println("Warnings: " + e2.getWarnings());
			assertTrue(false);
		}		
	}
	
	/** 
	 *  Verifica el funcionamiento del checkTransaction del CmError para una transaccion dada 
	 *  cuando la transaccion tiene un error y varios warnings. El error prevalece a pesar de los warnings 	
	 */
	
	@Test
	public void checkTransaction_ErrorYWarnings() {
		System.out.println("\nEjecutando test checkTransaction_ErrorYWarnings...");
		try {
			String transactionName = "SetActTransaccionalProfileLim";
			String errorCode1 = "007";
			String errorCode2 = "150";
			String errorCode3 = "160";
			String errorCode4 = "170";
		
			loadError(transactionName, errorCode1);
			loadWarning(transactionName, errorCode2);
			loadWarning(transactionName, errorCode3);
			loadWarning(transactionName, errorCode4);
	
			//Armado del TransactionStatus
			ErrorCode e1 = new ErrorCode(errorCode1);
			ErrorCode e2 = new ErrorCode(errorCode2);
			ErrorCode e3 = new ErrorCode(errorCode3);
			ErrorCode e4 = new ErrorCode(errorCode4);
			List<ErrorCode> errors = new ArrayList<ErrorCode>();
			errors.add(e1);
			errors.add(e2);			
			errors.add(e3);
			errors.add(e4);
			TransactionStatus status = new TransactionStatusImpl(false, errors);
			CmError.checkTransaction(transactionName, status);
		} catch (ApplicationErrorException e1) {
			e1.printStackTrace(System.out);
			System.out.println("Errores: " + e1.getErrors());
			assertTrue(e1.getErrors().size() == 1);
		} catch (ApplicationWarningException e2) {
			e2.printStackTrace(System.out);
			System.out.println("Warnings: " + e2.getWarnings());
			assertTrue(false);
		}		
	}
	
	/** 
	 *  Verifica el funcionamiento del checkTransaction del CmError para una transaccion dada 
	 *  cuando la transaccion tiene varios errores y varios warnings. Los errores prevalecen a pesar de los warnings 	
	 */
	
	@Test
	public void checkTransaction_ErrorsYWarnings() {
		System.out.println("\nEjecutando test checkTransaction_ErrorsYWarnings...");
		try {
			String transactionName = "SetActTransaccionalProfileLim";
			//Errors
			String errorCode1 = "007";
			String errorCode2 = "008";
			String errorCode3 = "009";
			//Warnings
			String errorCode4 = "150";
			String errorCode5 = "160";
			String errorCode6 = "170";
		
			loadError(transactionName, errorCode1);			
			loadError(transactionName, errorCode2);
			loadError(transactionName, errorCode3);
			loadWarning(transactionName, errorCode4);
			loadWarning(transactionName, errorCode5);
			loadWarning(transactionName, errorCode6);						
	
			//Armado del TransactionStatus
			ErrorCode e1 = new ErrorCode(errorCode4);
			ErrorCode e2 = new ErrorCode(errorCode6);
			ErrorCode e3 = new ErrorCode(errorCode1);
			ErrorCode e4 = new ErrorCode(errorCode3);
			ErrorCode e5 = new ErrorCode(errorCode5);
			ErrorCode e6 = new ErrorCode(errorCode2);
			List<ErrorCode> errors = new ArrayList<ErrorCode>();
			errors.add(e1);
			errors.add(e2);			
			errors.add(e3);
			errors.add(e4);
			errors.add(e5);
			errors.add(e6);
			TransactionStatus status = new TransactionStatusImpl(false, errors);
			CmError.checkTransaction(transactionName, status);
		} catch (ApplicationErrorException e1) {
			e1.printStackTrace(System.out);
			System.out.println("Errores: " + e1.getErrors());
			assertTrue(e1.getErrors().size() == 3);
		} catch (ApplicationWarningException e2) {
			e2.printStackTrace(System.out);
			System.out.println("Warnings: " + e2.getWarnings());
			assertTrue(false);
		}		
	}

	@Test
	public void getShortStackTrace() {
		
		Throwable t1 = new ApplicationErrorException("Exception1");
		
		Throwable t2 = new IOException("Exception2", t1);
		
		Throwable t3 = new Exception("Exception3", t2);
		
		Throwable t4 = new RuntimeException("Exception4", t3);
		
		System.out.println(ExceptionUtil.getShortStackTrace(t4));
		
		
	}
	
}
