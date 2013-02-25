/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.index.model;

public class Parameter {
	
	public String key;
	public String value;
	
	public Parameter(String key, String value) {
		this.key = key;
		this.value = value;
	}
}
