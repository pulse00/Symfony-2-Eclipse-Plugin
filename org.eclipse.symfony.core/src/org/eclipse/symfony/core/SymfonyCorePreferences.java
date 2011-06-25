package org.eclipse.symfony.core;

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
}
