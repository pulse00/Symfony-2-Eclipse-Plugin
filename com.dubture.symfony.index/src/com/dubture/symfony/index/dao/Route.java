/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.index.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.dubture.symfony.index.log.Logger;

/**
 *
 * Represents a Symfony route.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public class Route {

	
	public String name;
	public String pattern;
	public String controller;
	public String action;
	public String bundle;
	
	private Map<String, RouteParameter> parameters = null;
	
	public Route(String bundle, String controller, String action, String name, String pattern) {
		
		this.bundle = bundle;
		this.controller = controller;
		this.action = action;
		this.name = name;
		this.pattern = pattern.replace("\"", "").replace("'", "");
		

		
		
	}
	
	public Route(String name, String pattern, String viewPath) {
		
		this.name = name;
		this.pattern = pattern;
		
		try {
			
			StringTokenizer tokenizer = new StringTokenizer(viewPath, ":");
			
			this.bundle = tokenizer.nextToken();
			this.controller = tokenizer.nextToken();
			this.action = tokenizer.nextToken();
			
		} catch (Exception e) {

			Logger.logException(e);
		}
	}


	public Route(String name2, String pattern2) {

		name = name2;
		pattern = pattern2;
	}
	
	
	@Override
	public String toString() {

		return name + " => " + pattern + " => " + getViewPath();
	}


	public void setAction(String action) {

		this.action = action;
		
	}
	
	
	public String getAction() {
	
		return action;
		
	}

	public String getViewPath() {
		
		return String.format("%s:%s:%s", bundle, controller, action);
	}
	

	public String getName() {
				
		return name;
	}
	
	public String getController() {
		
		return controller;
		
	}
	
	public boolean hasParameters() {
		
		return pattern.contains(RouteParameter.LEFT_DELIM);		
		
	}

	public Map<String, RouteParameter> getParameters() {

		if(parameters != null)
			return parameters;
		
		parameters = new HashMap<String, RouteParameter>();
		
		String route = pattern;
		String[] parts = route.split("\\/");
		
		for (String part : parts) {			
			if (part.startsWith(RouteParameter.LEFT_DELIM)) {
				
				RouteParameter param = new RouteParameter(part);				
				parameters.put(param.getName(), param);
			}
		}		
		
		return parameters;
		
	}

	
	public String getURL(Collection<RouteParameter> collection, String prefix) {

		String url = pattern;
		
		for (RouteParameter param : collection) {
			String regex = String.format("{%s}", param.getName());			
			url = url.replace(regex, param.getValue());
		}
	
		if (prefix != null)
			url = prefix + url;
		
		return url;

	}
	
	public String getURL(String prefix) {

		if (prefix != null)
			return prefix + pattern;
		
		return pattern;
				
	}	
	
	
}
