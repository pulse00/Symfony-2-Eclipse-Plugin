package org.eclipse.symfony.ui.preferences;

public class SyntheticService {

	public String name;
	public String className;

	public SyntheticService(String name, String className) {

		this.name = name;
		this.className = className;			
	}				

	public SyntheticService() {
		
		name = "";
		className = "";
	}
}