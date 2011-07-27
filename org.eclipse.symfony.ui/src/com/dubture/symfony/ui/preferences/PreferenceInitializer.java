package com.dubture.symfony.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;

import com.dubture.symfony.ui.PreferenceConstants;

public class PreferenceInitializer extends AbstractPreferenceInitializer {



	@Override
	public void initializeDefaultPreferences() {

		PreferenceConstants.initializeDefaultValues();

	}

}
