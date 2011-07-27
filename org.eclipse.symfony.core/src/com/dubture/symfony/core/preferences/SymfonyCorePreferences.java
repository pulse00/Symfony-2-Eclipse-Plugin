package com.dubture.symfony.core.preferences;

import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.compiler.problem.ProblemSeverity;

/**
 * Utility class to access the plugins preferences.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class SymfonyCorePreferences {

	
	public static final String SCHEMA_VERSION = "schema_version";
	
	/**
	 * Get the severity level for annotation problems.
	 * 
	 * 
	 * @return {@link ProblemSeverity}
	 */
	public static ProblemSeverity getAnnotationSeverity() {
		
		
		//TODO: check if there's a cleaner way to get the preferences from the ui
		// plugin than hardcoding the ID
		String severity = Platform.getPreferencesService().getString("org.eclipse.symfony.ui", 
				SymfonyCoreConstants.ANNOTATION_PROBLEM_SEVERITY, SymfonyCoreConstants.ANNOTATION_WARNING, null);
		
		if (severity == null) {			
			
			severity = SymfonyCoreConstants.ANNOTATION_WARNING;
		}
		
		
		if (severity.equals(SymfonyCoreConstants.ANNOTATION_ERROR)) 
		{	
			return ProblemSeverity.ERROR;
			
		} else if(severity.equals(SymfonyCoreConstants.ANNOTATION_WARNING))
		{				
			return ProblemSeverity.WARNING;
		}
		
		return ProblemSeverity.IGNORE;
		
	}
	
	
//	public static JSONObject getSyntheticServices() {
//		
//		try {
//
//			if (synthetic != null)
//				return synthetic;
//			
//			//TODO: store in preferences and build a preference page for synthetic services
//			// where users can change the default implementations
//			
////			String stored = Platform.getPreferencesService().getString("org.eclipse.symfony.ui", 
////					SymfonyCoreConstants.SYNTHETIC_SERVICES, "", null);
//			
//			String stored = "{\"request\" : \"Symfony\\Component\\HttpFoundation\\Request\"}";
//			
//			if (stored == null || stored.length() == 0)
//				return new JSONObject();
//			
//			JSONParser parser = new JSONParser();			
//			JSONObject prefs = (JSONObject) parser.parse(stored);
//
//			synthetic = prefs;
//			return synthetic;
//			
//		} catch (Exception e) {
//
//			Logger.logException(e);
//		}			
//		
//		return new JSONObject();
//		
//	}
}
