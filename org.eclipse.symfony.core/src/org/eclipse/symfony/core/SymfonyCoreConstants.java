package org.eclipse.symfony.core;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;


/**
 * 
 * Preference constants for the Symfony plugin.
 * 
 * @see PreferenceInitializer, SymfonyCorePreferences 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class SymfonyCoreConstants {
	
	/**
	 * Option for severity level or annotation problems
	 */
	public static final String ANNOTATION_PROBLEM_SEVERITY = "annotation_problem_severity";
	
	
	/**
	 * Annotation problem severity levels.
	 */
	public static final String ANNOTATION_ERROR 	= "Error";
	public static final String ANNOTATION_WARNING 	= "Warning";	
	public static final String ANNOTATION_IGNORE 	= "Ignore";	
	
	
	// Symfony Class and Method names	
	public static final String CONTROLLER_CLASS 	= "Controller";
	public static final String ACTION_SUFFIX 		= "Action";

	
	
	// FrameworkExtraBundle Annotations
	
	public static final String TEMPLATE_ANNOTATION	= "@Template";
	

	public static void initializeDefaultValues() {

		IEclipsePreferences node = DefaultScope.INSTANCE.getNode(SymfonyCorePlugin.ID);
		node.put(ANNOTATION_PROBLEM_SEVERITY, ANNOTATION_WARNING);
		
	}	
}
