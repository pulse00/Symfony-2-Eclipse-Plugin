/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.test;

import org.antlr.runtime.RecognitionException;

import com.dubture.symfony.annotation.parser.antlr.error.IAnnotationErrorReporter;

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
