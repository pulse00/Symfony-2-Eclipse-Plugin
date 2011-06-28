package org.eclipse.symfony.core.model;

import java.util.StringTokenizer;

public class ViewPath {

	
	private String bundle;
	private String controller;
	private String template;
	
	public ViewPath(String path) {
		
		StringTokenizer tokenizer = new StringTokenizer(path, ".");
		
		bundle = tokenizer.nextToken();
		controller = tokenizer.nextToken();
		template = tokenizer.nextToken();
		
	
	}
	
	
}
