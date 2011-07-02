package org.eclipse.symfony.core.preferences;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.symfony.core.SymfonyCorePlugin;
import org.eclipse.symfony.core.util.JsonUtils;

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
	}

	
	public static void initializeDefaultValues() {

		IEclipsePreferences node = DefaultScope.INSTANCE.getNode(SymfonyCorePlugin.ID);		
		
		node.put(SymfonyCoreConstants.ANNOTATION_PROBLEM_SEVERITY, SymfonyCoreConstants.ANNOTATION_WARNING);
		node.put(Keys.SYNTHETIC_SERVICES, JsonUtils.createDefaultSyntheticServices());		
				
	}	
	
	
	private CorePreferenceConstants() {

	}

}
