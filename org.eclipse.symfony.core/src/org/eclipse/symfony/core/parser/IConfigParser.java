package org.eclipse.symfony.core.parser;


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
	public void parse() throws Exception;

}
