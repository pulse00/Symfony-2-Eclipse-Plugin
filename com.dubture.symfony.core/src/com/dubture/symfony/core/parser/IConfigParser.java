/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.parser;

import java.util.HashMap;

import com.dubture.symfony.index.dao.Service;


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
	HashMap<String, Service> getServices();

}
