package com.dubture.symfony.ui.editor.template;

import java.util.HashMap;
import java.util.Map;

public class CodeTemplateVariableHolder {
	
	
	private Map<String, Object> variables = new HashMap<String, Object>();	
	
	public void set(String key, Object value) {
		
		variables.put(key, value);
		
	}
	
	public Object get(String key) {
		
		if (!variables.containsKey(key))
			return "";
		
		return variables.get(key);
	}

}
