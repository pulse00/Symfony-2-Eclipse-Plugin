package com.dubture.symfony.index.dao;

public class RoutingResource {
	
	private String type;
	private String path;
	private String prefix;
	
	public RoutingResource(String type, String path, String prefix) {
		
		this.type = type;
		this.path = path;
		this.prefix = prefix;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	
	@Override
	public String toString() {

		return String.format("%s: type = %s, prefix = %s", path, type, prefix);
		
	}
	

}
