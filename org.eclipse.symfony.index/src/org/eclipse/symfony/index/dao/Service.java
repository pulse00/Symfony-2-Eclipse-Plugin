package org.eclipse.symfony.index;


/**
 * 
 * Simple Service POJO
 * 
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
public class Service {
	
	
	public String id;
	public String phpClass;
	public String path;
	
	public Service(String id, String phpClass, String path) {
		
		this.id = id;
		this.phpClass = phpClass;
		this.path = path;
		
	}

}
