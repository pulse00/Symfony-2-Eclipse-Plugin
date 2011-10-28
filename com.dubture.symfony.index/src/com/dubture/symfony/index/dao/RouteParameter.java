package com.dubture.symfony.index.dao;

public class RouteParameter {
	
	public static final String LEFT_DELIM = "{";
	public static final String RIGHT_DELIM = "}";

	private String name;
	private String value;
	private String raw;
	
	public RouteParameter(String raw) {

		this.raw = raw;
		
		name = raw.replace(LEFT_DELIM, "").replace(RIGHT_DELIM, "");
		
	}
	
	public String getName() {
		
		return name;
		
	}

	public void setValue(String string) {

		value = string;
		
	}
	
	public String getValue() {
				
		return value;
	}

	public String getRaw() {

		return raw;
	}
	
}
