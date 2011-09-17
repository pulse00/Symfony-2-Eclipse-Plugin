package com.dubture.symfony.ui.editor.template;

import java.util.HashMap;
import java.util.Map;

public class CodeTemplateVariableHolder {
	
	
	private Map<String, String> variables = new HashMap<String, String>();	
	
	public void set(String key, String value) {
		
		variables.put(key, value);
		
	}
	
	public String get(String key) {
		
		if (!variables.containsKey(key))
			return "";
		
		return variables.get(key);
	}

}
