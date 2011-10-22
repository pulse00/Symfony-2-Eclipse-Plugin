package com.dubture.symfony.index.dao;

public class TransUnit {
	
	
	public String name;
	public String value;
	public String language;

	public TransUnit(String name, String value, String language) {
		
		this.name = name;
		this.value = value;
		this.language = language;
		
	}
	
	
	@Override
	public String toString() {

		return String.format("%s - %s (%s)", name, value, language);
		
	}
}
