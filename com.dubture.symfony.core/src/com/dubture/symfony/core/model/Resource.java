package com.dubture.symfony.core.model;

public class Resource {

	public static int ROUTE_RESOURCE = 0;
	
	public String path;
	public int type;
	public String prefix;
	
	@Override
	public String toString() {
	
		return String.format("%s => %s", path, type);
	
	}
	
}
