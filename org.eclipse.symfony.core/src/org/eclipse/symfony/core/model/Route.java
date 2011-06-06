package org.eclipse.symfony.core.model;

import org.eclipse.core.resources.IFile;

public class Route {

	
	private String path;
	private String name;
	private IFile file;
	

	public Route(IFile file, String path, String name) {

		this.file = file;
		this.path = path;
		this.name = name;
	}
	
	public static Route fromAnnotation(IFile file, String annotation) {
				
		//TODO: parse name + path from annotation string
		String name = "dummy";
		String path = "dummyPath";
				
				
		Route route = new Route(file, path, name);		
		return route;
		
		
	}
	
	public IFile getFile() {
		
		return file;
	}
	
	public String getName() {
		
		return name;
	}
	
	public String getPath() {
		
		return path;
		
	}

}
