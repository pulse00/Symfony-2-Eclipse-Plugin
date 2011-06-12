package org.eclipse.symfony.core.parser;

import java.util.HashMap;


/**
 * 
 * Interface for config parsers (XML/Yaml so far).
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public interface IConfigParser {
	
	/**
	 * Parse the configuration from a file and 
	 * contribute it to the Symfony2 model.
	 * 
	 * @throws Exception 
	 */
	void parse() throws Exception;
	
	
	/**
	 * Retrieve the services detected by the parser.
	 * 
	 * @return {@link HashMap}
	 */
	HashMap<String, String> getServices();

}
