package com.dubture.symfony.core.parser;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.yaml.snakeyaml.Yaml;

public class YamlTranslationParser {

	private InputStream input;	
	private Map<String, String> transUnits = new HashMap<String, String>();
	
	public YamlTranslationParser(InputStream input) {

		this.input = input;
	}
	
	
	@SuppressWarnings("rawtypes")
	public void parse() {
		
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
			String key = (String) it.next();
			Object value = map.get(key);
			parse(key,value);
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	private void parse(String key, Object value) {
		
		if (value instanceof String) {
			
			String transUnit = (String) value;						
			transUnits.put(key, transUnit);
			
			
		} else if (value instanceof LinkedHashMap) {
			
			LinkedHashMap map = (LinkedHashMap) value;			
			Iterator it = map.keySet().iterator();
			
			while (it.hasNext()) {
				String k = (String) it.next();
				Object v = map.get(k);				
				parse(key + "." + k, v);				
			}
		}
	}
	
	public Map<String, String> getTranslations() {
		
		return transUnits;
		
	}
}