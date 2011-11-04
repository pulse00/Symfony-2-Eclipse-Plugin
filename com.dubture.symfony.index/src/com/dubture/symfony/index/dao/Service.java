package com.dubture.symfony.index.dao;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * Simple Service POJO
 * 
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
public class Service {
	
	public static final String NAME = "name";
	public static final String CLASS = "class";
	public static final Object SYNTHETIC = "synthetic";
	
	public String id;
	public String phpClass;
	public String path;
	private String _public = "true";
		
	private List<String> aliases = new ArrayList<String>();
	private List<String> tags = new ArrayList<String>();
	
	public Service(String id, String phpClass, String path) {
		
		this.id = id;
		this.phpClass = phpClass;
		this.path = path;
		
	}

	public void setPublic(String _public)
	{
		this._public = _public;
		
	}
	
	public boolean isPublic() {
		
		return _public == null || _public.equals("true");
		
	}
	
	public void addAlias(String alias) {
		
		aliases.add(alias);
		
	}
	
	public List<String> getAliases() {
		
		return aliases;
	}
	
	public void addTag(String tag) {
			
		tags.add(tag);
		
	}
	
	public List<String> getTags() {
		
		return tags;
		
	}

}
