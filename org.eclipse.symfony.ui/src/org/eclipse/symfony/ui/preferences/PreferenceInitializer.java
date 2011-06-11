package org.eclipse.symfony.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import org.eclipse.symfony.core.SymfonyCoreConstants;
import org.eclipse.symfony.ui.SymfonyUiPlugin;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		
		IPreferenceStore store = SymfonyUiPlugin.getDefault().getPreferenceStore();
		store.setDefault(SymfonyCoreConstants.ANNOTATION_PROBLEMS, "warning");
		
	}

}
