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
	
	private boolean basePath;
	
	public ViewPath(String path) {
		
		// initialize the viewpath parts as far as possible		
		if (path.indexOf("::") >=0) {
			
			StringTokenizer tokenizer = new StringTokenizer(path, "::");

			basePath = true;
			try {
				
				bundle = tokenizer.nextToken();
				template = tokenizer.nextToken();
				
			} catch (Exception e) {
				
			}
			
		} else {
			
			StringTokenizer tokenizer = new StringTokenizer(path, ":");
			basePath = false;
			// initialize the viewpath parts as far as possible
			try {
				
				bundle = tokenizer.nextToken();
				controller = tokenizer.nextToken();
				template = tokenizer.nextToken();
				
			} catch (Exception e) {
				
			}
			
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
	
	@Override
	public String toString() {

		return String.format("%s:%s:%s", bundle, controller, template);

	}

	public boolean isBasePath() {

		return basePath;
	}
	
}
