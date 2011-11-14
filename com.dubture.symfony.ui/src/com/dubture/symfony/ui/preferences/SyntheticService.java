/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.preferences;

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
