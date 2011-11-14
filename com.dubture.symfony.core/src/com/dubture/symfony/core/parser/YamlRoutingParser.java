/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.parser;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yaml.snakeyaml.Yaml;

import com.dubture.symfony.core.log.Logger;
import com.dubture.symfony.index.dao.Route;
import com.dubture.symfony.index.dao.RoutingResource;


/**
 * 
 * {@link YamlRoutingParser}, does what you expect ;)
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class YamlRoutingParser {

	private InputStream input;

	private Stack<Route> routes = new Stack<Route>();
	private Stack<RoutingResource> resources = new Stack<RoutingResource>();
	

	public Stack<Route> getRoutes() {
		return routes;
	}
	
	public Stack<RoutingResource> getResources() {
		
		return resources;
		
	}

	public YamlRoutingParser(InputStream input) {

		this.input = input;
	}

	@SuppressWarnings("rawtypes")
	public void parse() {


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

		Iterator it = map.keySet().iterator();

		while(it.hasNext()) {

			Object key = it.next();
			Object value = map.get(key);

			if (key.getClass() == String.class && value.getClass() == LinkedHashMap.class) {

				try {

					String name = (String) key;				
					LinkedHashMap params = (LinkedHashMap) value;

					String pattern = (String) params.get("pattern");
					
					if (params.containsKey("resource")) {

						String resource = (String) params.get("resource");
						String type = (String) params.get("type");
						String prefix = (String) params.get("prefix");
						resources.add(new RoutingResource(type, resource, prefix));
						
					} else {
						
						LinkedHashMap defaults = (LinkedHashMap) params.get("defaults");		
						
						if(defaults == null)
							continue;
						
						String viewPath = (String) defaults.get("_controller");
						routes.push(new Route(name, pattern, viewPath));
						
					}

				} catch (Exception e) {
					Logger.logException(e);
				}
			}
		}
	}
}
