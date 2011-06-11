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

	
	/**
	 * Get the severity level for annotation problems.
	 * 
	 * 
	 * @return {@link ProblemSeverity}
	 */
	public static ProblemSeverity getAnnotationSeverity() {
				
		String severity = Platform.getPreferencesService().getString(
				SymfonyCorePlugin.ID,SymfonyCoreConstants.ANNOTATION_PROBLEMS, "warning", null);
		
		if (severity.equals("error")) 
		{	
			return ProblemSeverity.ERROR;
			
		} else if(severity.equals("warning"))
		{			
			return ProblemSeverity.WARNING;
		}
				
		return ProblemSeverity.IGNORE;
		
	}
}
