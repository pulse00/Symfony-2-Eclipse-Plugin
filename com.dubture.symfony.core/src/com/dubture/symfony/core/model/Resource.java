/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
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
