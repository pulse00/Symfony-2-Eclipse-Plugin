/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.index.model;

import java.util.ArrayList;

import java.util.List;

/**
 * Simple Service POJO
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 * 
 */
public class Service {

	public static final String NAME = "name";
	public static final String CLASS = "class";
	public static final String SYNTHETIC = "synthetic";

	public String id;
	public String phpClass;
	public String path;
	private String _public = "true";

	private List<String> aliases = new ArrayList<String>();
	private List<String> tags = new ArrayList<String>();
	private Integer line;

	public Service(String id, String phpClass, String path) {
		this.id = id;
		this.phpClass = phpClass;
		this.path = path;
	}

	public String getPHPClass() {
		return phpClass;
	}

	public void setPublic(String _public) {
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

	public void setTags(String tags2) {
		String[] _tags = tags2.split(",");
		for (String tag : _tags) {
			this.tags.add(tag);
		}
	}

	/**
	 * @param userData
	 */
	public void setLine(Integer line) {
		this.line = line;
	}

	public Integer getLine() {
		return line;
	}
}
