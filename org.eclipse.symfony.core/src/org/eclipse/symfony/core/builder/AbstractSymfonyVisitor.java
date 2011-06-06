/**
 * 
 */
package org.eclipse.symfony.core.builder;

import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.parser.XMLConfigParser;
import org.eclipse.symfony.core.parser.YamlConfigParser;

/**
 *
 * Abstract visitor to provide xml- and yml parsers.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public abstract class AbstractSymfonyVisitor {

	private XMLConfigParser xmlParser = null;
	
	private YamlConfigParser ymlParser = null;
	
	private YamlRoutingParser ymlRoutingParser = null;
	
	
	protected YamlRoutingParser getYmlRoutingParser() {
		
		
		if (ymlRoutingParser == null)
			ymlRoutingParser = new YamlRoutingParser();
		
		return ymlRoutingParser;
		
		
	}
	
	protected XMLConfigParser getXmlParser() {
		
		if (xmlParser == null)
			xmlParser = new XMLConfigParser();
		
		
		return xmlParser;
		
	}
	
	protected YamlConfigParser getYamlParser() {
		
		if (ymlParser == null)
			ymlParser = new YamlConfigParser();
		
		return ymlParser;
	}
	
	protected ModelManager getModel() {

		return ModelManager.getInstance();
		
	}
}
