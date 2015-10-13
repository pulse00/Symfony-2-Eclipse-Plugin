/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.preferences;

/**
 * Utility class to access the plugins preferences.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class SymfonyCorePreferences {

	
	public static final String SCHEMA_VERSION = "schema_version";
	
	
	
//	public static JSONObject getSyntheticServices() {
//		
//		try {
//
//			if (synthetic != null)
//				return synthetic;
//			
//			//TODO: store in preferences and build a preference page for synthetic services
//			// where users can change the default implementations
//			
////			String stored = Platform.getPreferencesService().getString("org.eclipse.symfony.ui", 
////					SymfonyCoreConstants.SYNTHETIC_SERVICES, "", null);
//			
//			String stored = "{\"request\" : \"Symfony\\Component\\HttpFoundation\\Request\"}";
//			
//			if (stored == null || stored.length() == 0)
//				return new JSONObject();
//			
//			JSONParser parser = new JSONParser();			
//			JSONObject prefs = (JSONObject) parser.parse(stored);
//
//			synthetic = prefs;
//			return synthetic;
//			
//		} catch (Exception e) {
//
//			Logger.logException(e);
//		}			
//		
//		return new JSONObject();
//		
//	}
}
