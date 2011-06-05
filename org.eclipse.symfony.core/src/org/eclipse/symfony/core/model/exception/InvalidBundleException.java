package org.eclipse.symfony.core.model.exception;

@SuppressWarnings("serial")
public class InvalidBundleException extends Exception {

	
	private String message;
	
	public InvalidBundleException(String string) {
		
		message = string;

	}
	
	@Override
	public String getMessage() {

		return message;
	}

}
