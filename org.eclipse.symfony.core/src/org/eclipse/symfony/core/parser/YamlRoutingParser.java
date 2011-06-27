package org.eclipse.symfony.core.parser;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.symfony.index.dao.Route;
import org.yaml.snakeyaml.Yaml;


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

	public Stack<Route> getRoutes() {
		return routes;
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
					LinkedHashMap defaults = (LinkedHashMap) params.get("defaults");		
					
					if(defaults == null)
						continue;
					
					String controller = (String) defaults.get("_controller");
					routes.push(new Route(name, pattern, controller));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}