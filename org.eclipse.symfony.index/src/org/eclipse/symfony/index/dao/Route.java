package org.eclipse.symfony.index.dao;

public class Route {

	
	public String name;
	public String pattern;
	public String controller;
	

	public Route(String name2, String pattern2, String controller2) {
		
		name = name2;
		pattern = pattern2;
		controller = controller2;
		
	}

}
