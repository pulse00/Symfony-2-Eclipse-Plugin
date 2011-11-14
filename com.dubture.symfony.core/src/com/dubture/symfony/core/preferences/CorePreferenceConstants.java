/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.preferences;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

import com.dubture.symfony.core.SymfonyCorePlugin;
import com.dubture.symfony.core.util.JsonUtils;

/**
 * 
 * The {@link CorePreferenceConstants} hold basic preference
 * values for the Plugin and is responsible for loading
 * default preferences using {@link DefaultScope}
 * 
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class CorePreferenceConstants {
	
	public interface Keys {
		public static final String SYNTHETIC_SERVICES = "synthetic_services";
		public static final String TWIG_SUPPORT = "twig_support";
		public static final String SYMFONY_VERSION = "symfony_versio";
	}

	
	public static void initializeDefaultValues() {

		IEclipsePreferences node = DefaultScope.INSTANCE.getNode(SymfonyCorePlugin.ID);		
		
		node.put(SymfonyCoreConstants.ANNOTATION_PROBLEM_SEVERITY, SymfonyCoreConstants.ANNOTATION_WARNING);
		node.put(Keys.SYNTHETIC_SERVICES, JsonUtils.createDefaultSyntheticServices());		
				
	}	
	
	
	private CorePreferenceConstants() {

	}

}
