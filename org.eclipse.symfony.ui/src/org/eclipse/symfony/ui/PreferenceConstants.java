package org.eclipse.symfony.ui;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.symfony.core.SymfonyCoreConstants;
import org.eclipse.symfony.core.util.JsonUtils;

public class PreferenceConstants {
	
	public interface Keys {
		public static final String SYNTHETIC_SERVICES = SymfonyCoreConstants.SYNTHETIC_SERVICES;
	}
	
	

	public static IPreferenceStore getPreferenceStore() {
		return SymfonyUiPlugin.getDefault().getPreferenceStore();
	}
	
	public static void initializeDefaultValues() {

		IPreferenceStore store = getPreferenceStore();
		String defaultServices = JsonUtils.createDefaultSyntheticServices();
		store.setDefault(SymfonyCoreConstants.SYNTHETIC_SERVICES, defaultServices);
		
	}
}