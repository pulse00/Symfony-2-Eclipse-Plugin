package org.eclipse.symfony.index.dao;

import java.util.StringTokenizer;

/**
 * 
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
public class Route {

	
	public String name;
	public String pattern;
	public String controller;
	public String action;
	public String bundle;

	
	public Route(String bundle, String controller, String action, String name, String pattern) {
		
		this.bundle = bundle;
		this.controller = controller;
		this.action = action;
		this.name = name;
		this.pattern = pattern;
		
		
	}
	
	public Route(String name, String pattern, String viewPath) {
		
		this.name = name;
		this.pattern = pattern;
		
		try {
			
			StringTokenizer tokenizer = new StringTokenizer(viewPath, ":");
			
			this.bundle = tokenizer.nextToken();
			this.controller = tokenizer.nextToken();
			this.action = tokenizer.nextToken();
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}


	public Route(String name2, String pattern2) {

		name = name2;
		pattern = pattern2;
	}
	
	
	@Override
	public String toString() {

		return name + " => " + pattern + " => " + getViewPath();
	}


	public void setAction(String action) {

		this.action = action;
		
	}
	
	
	public String getAction() {
	
		return action;
		
	}

	public String getViewPath() {
		
		return String.format("%s:%s:%s", bundle, controller, action);
	}
}