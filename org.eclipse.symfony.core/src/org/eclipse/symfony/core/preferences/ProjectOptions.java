package org.eclipse.symfony.core.preferences;


import org.eclipse.core.resources.IProject;
import org.eclipse.symfony.core.preferences.CorePreferenceConstants.Keys;



public class ProjectOptions {
	
	private ProjectOptions() {

	}
	
	@SuppressWarnings("restriction")
	public static final String getSyntheticServices(IProject project) {
		
		return CorePreferencesSupport.getInstance()
				.getPreferencesValue(Keys.SYNTHETIC_SERVICES, null, project);
	}	
	
	
	@SuppressWarnings("restriction")
	public static final String getDefaultSyntheticServices() {
	
		
		return CorePreferencesSupport.getInstance().getWorkspacePreferencesValue(Keys.SYNTHETIC_SERVICES);
		
	}

}
