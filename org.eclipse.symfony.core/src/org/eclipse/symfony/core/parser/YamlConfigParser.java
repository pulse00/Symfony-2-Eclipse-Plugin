package org.eclipse.symfony.core.parser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yaml.snakeyaml.Yaml;

/**
 * 
 * The {@link YamlConfigParser} is responsible for parsing
 * symfony .yml config files.
 * 
 * Currently it only extracts service descriptions.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class YamlConfigParser implements IConfigParser {
	
	private InputStream input;
	
	private HashMap<String, String> services = new HashMap<String, String>();
	private HashMap<String, String> parameters = new HashMap<String, String>();
	
	
	public YamlConfigParser(InputStream input) {
		
		this.input = input;		
		
	}
	
	public HashMap<String, String> getParameters() {
		return parameters;
	}

	
	@Override
	@SuppressWarnings("rawtypes")	
	public void parse() throws Exception {

		// we need to replace the % signs, otherwise
		// the yaml parser will throw a syntax error exception
		Pattern replaceCharPattern = Pattern.compile("%", Pattern.COMMENTS);
		
		StringBuilder text = new StringBuilder();
	    String NL = System.getProperty("line.separator");
	    Scanner scanner = new Scanner(input);

	    try {
	      while (scanner.hasNextLine()){
	        text.append(scanner.nextLine() + NL);
	      }
	    }
	    finally{
	      scanner.close();
	    }		
		
        Matcher m = replaceCharPattern.matcher(text);
        String tokenString = m.replaceAll("_");
		
		Yaml yaml = new Yaml();
		Map map = (Map) yaml.load(tokenString);

		Object parameters = map.get("parameters");
		
		if (parameters instanceof Map) {			
			parseParameters((Map) parameters);
		}

		
		Object services = map.get("services");
		
		if (services instanceof Map) {
			parseServices((Map) services);
		}
		
	}


	/**
	 * Parse services parameters.
	 * 
	 * 
	 * @param parameters
	 */
	@SuppressWarnings("rawtypes")
	private void parseParameters(Map parameters) {

		Iterator it = parameters.entrySet().iterator();
		
		while(it.hasNext()) {

			Map.Entry pairs = (Entry) it.next();
			
			String paramName = (String) pairs.getKey();
			
			if (paramName == null)
				continue;
			
			if (paramName.endsWith(".class")) {
				
				String className = (String) pairs.getValue();				
				assert className != null;
				
				String id = paramName.replace(".class",  "");
				this.parameters.put(id, className);
				
			}
		}
		
	}


	@SuppressWarnings("rawtypes")
	private void parseServices(Map services) {

		Iterator it = services.entrySet().iterator();
		
		while(it.hasNext()) {
			
			Map.Entry pairs = (Entry) it.next();
			String id = (String) pairs.getKey();
			Map description = (Map) pairs.getValue();
			
			if (id == null || description == null)
				continue;
			
			String clazz = (String) description.get("class");
			
			if (clazz == null)
				continue;
			
			if (clazz.startsWith("_") && clazz.endsWith("_")) {
				
				clazz = parameters.get(id);
				
				if (clazz == null)
					continue;
				
				
			}
			
			this.services.put(id, clazz);
			
		}
	}

	public HashMap<String, String> getServices() {
		return services;
	}
}