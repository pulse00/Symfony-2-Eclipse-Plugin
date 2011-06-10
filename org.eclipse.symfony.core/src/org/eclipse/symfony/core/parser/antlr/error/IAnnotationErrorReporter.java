package org.eclipse.symfony.core.parser.antlr.error;


import org.antlr.runtime.RecognitionException;

/**
 * 
 * Interface to Antlr error-reporting
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
public interface IAnnotationErrorReporter {
	
	void reportError(String header, String message, RecognitionException e);	

}
