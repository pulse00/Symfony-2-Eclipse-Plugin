package com.dubture.symfony.ui;

import org.eclipse.jface.preference.IPreferenceStore;

public class PreferenceConstants {
	
	public static final String CODE_TEMPLATES_KEY = "com.dubture.symfony.ui.text.custom_code_templates";

	public static IPreferenceStore getPreferenceStore() {
		return SymfonyUiPlugin.getDefault().getPreferenceStore();
	}
	
	public static void initializeDefaultValues() {

		
		

		
	}
}