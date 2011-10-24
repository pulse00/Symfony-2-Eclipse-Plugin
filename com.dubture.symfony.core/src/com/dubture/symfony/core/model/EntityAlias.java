package com.dubture.symfony.core.model;

public class EntityAlias {
	
	private String bundleAlias;
	private String entity;
	
	public EntityAlias(String bundle, String e) {
		
		setBundleAlias(bundle);
		setEntity(e);
		
	}

	public EntityAlias(String shortcut) {
		
		if (shortcut.contains(":") == false)
			return;
		
		if (shortcut.endsWith(":")) {
			bundleAlias = shortcut.replace(":", "");
			entity = null;					
		} else {
			
			String[] parts = shortcut.split(":");
			
			bundleAlias = parts[0];
			entity = parts[1];
		}
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getBundleAlias() {
		return bundleAlias;
	}

	public void setBundleAlias(String bundleAlias) {
		this.bundleAlias = bundleAlias;
	}
	
	public boolean hasBundle() {
		
		return bundleAlias != null;
		
	}
	
	@Override
	public String toString() {

		return String.format("%s:%s", bundleAlias, entity);
		
	}

}
