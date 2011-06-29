package org.eclipse.symfony.core.model;

import java.util.StringTokenizer;

/**
 * 
 * Represents a ViewPath in the form
 * 
 * 
 * <pre>
 * 
 * 	BundleName:ControllerName:TemplateName
 * 
 * </pre>
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class ViewPath {

	private String bundle;
	private String controller;
	private String template;
	
	public ViewPath(String path) {
		
		StringTokenizer tokenizer = new StringTokenizer(path, ":");

		// initialize the viewpath parts as far as possible
		try {
			
			bundle = tokenizer.nextToken();
			controller = tokenizer.nextToken();
			template = tokenizer.nextToken();
			
		} catch (Exception e) {
		}
	}
	
	public String getBundle() {

		return bundle;
		
	}
	
	
	public String getController() {
		
		
		return controller;
		
		
	}
	
	public String getTemplate() {
		
		return template;
		
	}
	
}
