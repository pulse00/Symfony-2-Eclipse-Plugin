package org.eclipse.symfony.core.parser;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.symfony.core.model.ModelManager;
import org.eclipse.symfony.core.model.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class XMLHandler extends DefaultHandler {

	private IFile file;

	private String currentValue;
	private String currentParameter;
	private String currentServiceClass;
	private String currentServiceId;
	private HashMap<String, String> parameters = new HashMap<String, String>();
	private HashMap<String, String> services = new HashMap<String, String>();
	private HashMap<String, String> aliases = new HashMap<String, String>();	


	Boolean isServiceContainer = null;

	public XMLHandler(IFile file) {
		this.file = file;
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		currentValue = new String(ch, start, length);
	}	

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		if (isServiceContainer == null && qName.equals("container") == false) {
			isServiceContainer = false;
			return;
		}

		if (qName.equals("container")) {
			isServiceContainer = true;
			return;
		}

		if (qName.equals("parameter")) {				
			currentParameter = attributes.getValue("key");
		}

		if (qName.equals("service")) {

			if (attributes.getValue("alias") != null) {
				//System.out.println("adding alias " + attributes.getValue("alias") + " for " + attributes.getValue("id"));
				aliases.put(attributes.getValue("id"), attributes.getValue("alias"));
			}

			currentServiceClass = attributes.getValue("class");

			if (!(currentServiceClass instanceof String)) {					
				currentServiceClass = currentServiceId = null;
				return;

			}

			currentServiceClass = currentServiceClass.replace("%", "");
			currentServiceId = attributes.getValue("id");				

		}
	}


	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (isServiceContainer == false) {			
			return;
		}

		if (qName.equals("parameter")) {

			parameters.put(currentParameter, currentValue);

		}			

		if (qName.equals("service")) {

			if (currentServiceClass != null && currentServiceId != null && !services.containsKey(currentServiceClass))
				services.put(currentServiceClass, currentServiceId);


		}

		if (qName.equals("container")) {

			//System.out.println("parsed parameters:");

			Iterator it = parameters.keySet().iterator(); 				

			while(it.hasNext()) { 					
				String key = (String) it.next(); 
				String val = parameters.get(key);

				if (services.containsKey(key)) {
					
					String id = services.get(key);

					if (!services.containsKey(id)) {
						services.remove(key);
						services.put(id, val);
					}
				}
			}

			it = aliases.keySet().iterator();

			while (it.hasNext()) {

				String alias = (String) it.next();
				String id = aliases.get(alias);

				if (services.containsKey(id) && !services.containsKey(alias)) {					
					services.put(alias, services.get(id));
				}
			}
			
			
			it = services.keySet().iterator();
			
			
			while(it.hasNext()) {
				
				String name = (String) it.next();
				String clazz = services.get(name);
				
				ModelManager.getInstance().addService(new Service(file, name, clazz));				
				
			}
			
			
			
		}
	}
}
