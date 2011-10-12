package com.dubture.symfony.test.reporter;

import org.antlr.runtime.RecognitionException;

import com.dubture.symfony.core.parser.antlr.error.IAnnotationErrorReporter;

/**
 * Antlr Error reporter for unit tests.
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
public class DebugErrorReporter implements IAnnotationErrorReporter {

	private int errorCount = 0;
	
	
	@Override
	public void reportError(String header, String message, RecognitionException e) {

		errorCount++;

	}


	public int getErrorCount() {
		return errorCount;
	}
	
	public boolean hasErrors() {
		
		return errorCount > 0;
		
	}
	
	public void reset() {
		
		errorCount = 0;
	}

}
