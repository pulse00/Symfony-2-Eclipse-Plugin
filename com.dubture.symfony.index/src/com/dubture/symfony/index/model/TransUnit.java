/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.index.model;

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
