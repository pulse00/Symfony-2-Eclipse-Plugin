package com.dubture.symfony.index.dao;

public class TransUnit {
	
	
	public String name;
	public String value;
	public String language;
	public String path;

	public TransUnit(String name, String value, String language) {
		
		this.name = name;
		this.value = value;
		this.language = language;
		
	}
	
	public TransUnit(String name, String value, String language, String path) {
		
		this.name = name;
		this.value = value;
		this.language = language;
		this.path = path;
		
	}
	
	
	@Override
	public String toString() {

		return String.format("%s - %s (%s)", name, value, language);
		
	}
}
