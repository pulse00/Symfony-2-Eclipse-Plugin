package com.dubture.symfony.core.preferences;




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
	public static final String ROUTE_ANNOTATION 	= "@Route";	
	
	// prefix for methods which render views
	public static final String RENDER_PREFIX		= "render";
	
	
	// namespaces
	public static final String CONTROLLER_NS		="Symfony\\Bundle\\FrameworkBundle\\Controller";
	
	// core classes
	public static final String BUNDLE_FQCN 			= "Symfony\\Component\\HttpKernel\\Bundle\\Bundle";



}
