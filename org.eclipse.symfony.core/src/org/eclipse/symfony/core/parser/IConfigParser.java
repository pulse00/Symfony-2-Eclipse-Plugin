package org.eclipse.symfony.core.parser;

import org.eclipse.core.resources.IFile;

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
	 * @param file
	 * @throws Exception 
	 */
	public void parse(IFile file) throws Exception;

}
