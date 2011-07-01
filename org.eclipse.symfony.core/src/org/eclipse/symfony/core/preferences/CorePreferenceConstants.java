package org.eclipse.symfony.core.preferences;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.symfony.core.SymfonyCorePlugin;
import org.json.simple.JSONObject;


public class CorePreferenceConstants {
	
	
	public interface Keys {
		public static final String SYNTHETIC_SERVICES = "synthetic_services";
	}

	@SuppressWarnings({ "unchecked" })
	public static void initializeDefaultValues() {

		IEclipsePreferences node = InstanceScope.INSTANCE.getNode(SymfonyCorePlugin.ID);
		
		
		node.put(SymfonyCoreConstants.ANNOTATION_PROBLEM_SEVERITY, SymfonyCoreConstants.ANNOTATION_WARNING);
		
		JSONObject prefs = new JSONObject();
		prefs.put("request", "Symfony\\Component\\HttpFoundation\\Request");
		
		System.err.println("init default " + prefs.toString());		
		node.put(Keys.SYNTHETIC_SERVICES, prefs.toString());
		
		String dathing = node.get(Keys.SYNTHETIC_SERVICES, "none");
		
		System.err.println("dathing " + dathing);
		
		
		String result = ProjectOptions.getDefaultSyntheticServices();
		System.err.println("loaded " + result);
				
	}	
	
	
	private CorePreferenceConstants() {

	}

}
