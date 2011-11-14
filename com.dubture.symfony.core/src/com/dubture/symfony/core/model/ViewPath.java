/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.model;

import com.dubture.symfony.core.log.Logger;


/**
 * 
 * Represents a ViewPath in the form
 * 
 * 
 * <pre>
 * 
 * 	BundleName:ControllerName:TemplateName
 * 
 * </pre>
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class ViewPath {

	private String bundle;
	private String controller;
	private String template;
	
	private boolean basePath;
	
	private boolean _isRoot;
	
	public ViewPath(String path) {
		
		_isRoot = false;
		basePath = false;
		
		if (path.contains(":") == false)
			return;
		
		// "::base.html.twig"
		if(path.startsWith("::")) {			
			template = path.replace("::", "");
			_isRoot = true;
			
		// "AcmeDemobundle::"
		} else if (path.endsWith("::")) {
			
			bundle = path.replace("::", "");
			controller = null;
			template = "";
			
		} else {
			
			String[] parts = path.split(":");			
			
			switch (parts.length) {
			
			case 1:
				
				bundle = parts[0];
				if (bundle != null && bundle.length() == 0)
					bundle = null;
				
				break;
				
			case 2:
				
				bundle = parts[0];
				controller = parts[1];
				
				break;
				
			case 3:
				
				bundle = parts[0];
				controller = parts[1];
				
				if (controller != null && controller.length() == 0) {
					controller = null;
					basePath = true;
				}
				
				template =parts[2];				
				break;
				
			default:
				
				Logger.debugMSG("Unable to parse viewpath: " + path);
				break;
			
			}
		}
	}
	
	public String getBundle() {

		return bundle;
		
	}
	
	
	public String getController() {
		
		return controller;
		
	}
	
	public String getTemplate() {
		
		return template;
		
	}
	
	@Override
	public String toString() {

		return String.format("%s:%s:%s", bundle, controller, template);

	}

	public boolean isBundleBasePath() {

		return basePath;
	}
	
	public boolean isRoot() {
		
		return _isRoot;
	}
	
	public boolean isValid() {
		
		
		if (isBundleBasePath() || isRoot())
			return true;

		return bundle != null && controller != null && template != null;
		
	}
	
}
