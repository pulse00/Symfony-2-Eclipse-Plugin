package org.eclipse.symfony.core.util;

import org.eclipse.core.runtime.IPath;


/**
 * 
 * 
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class PathUtils {
	

	/**
	 * 
	 * Return the name of the Controller for a given ViewPath
	 * 
	 * @param path
	 * @return
	 */
	public static String getControllerFromTemplatePath(IPath path) {
	
		int size = path.segmentCount();
		
		for (int i=0; i < size; i++) {
			if (path.segment(i).equals("views") && (i+1 < size)) {
				
				return path.segment(i+1) + "Controller";
			}
			
		}
		
		return null;
	}

	public static String getViewFromTemplatePath(IPath path) {

		String fileName = path.lastSegment();
		return fileName.substring(0, fileName.indexOf('.'));
		
	}

}
