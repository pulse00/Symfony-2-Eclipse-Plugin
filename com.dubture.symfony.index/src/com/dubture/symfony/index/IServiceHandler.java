package com.dubture.symfony.index;


/**
 * 
 * Handler for retrieving services from the SQL index.
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
public interface IServiceHandler {
	
	void handle(String id, String phpClass, String path, String _public, String tags);
}
