package org.eclipse.symfony.index;


/**
 * 
 * Handler for retrieving services from the SQL index.
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
public interface IServiceHandler {
	
	void handle(Service service);
}
