/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
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
