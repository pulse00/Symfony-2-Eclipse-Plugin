/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.core.preferences;

import java.util.HashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.preference.IPreferenceStore;
import org.osgi.service.prefs.BackingStoreException;

import com.dubture.symfony.core.log.Logger;


/**
 *
 * The {@link PreferencesSupport} handles project specific
 * preferences and falls back to workspace scoped prefs
 * if there's no value for a specific project.
 * 
 * 
 * This is basically copy pasted from the PDT PreferencesSupport
 * but i wanted to get rid of the deprecated use of Preferences
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
@SuppressWarnings("rawtypes")
public class PreferencesSupport {
	
	
	private HashMap projectToScope;
	private String nodeQualifier;
	private IEclipsePreferences preferenceStore;

	/**
	 * Constructs a new PreferencesSupport.
	 * 
	 * @param nodeQualifier
	 *            A string qualifier for the node (for example:
	 *            PHPCorePlugin.ID)
	 * @param preferenceStore
	 *            The relevant preferences store.
	 */
	public PreferencesSupport(String nodeQualifier, IEclipsePreferences preferenceStore) {

		this.nodeQualifier = nodeQualifier;
		this.preferenceStore = preferenceStore;
		projectToScope = new HashMap();
	}

	/**
	 * Returns the project-specific value, or null if there is no node for the
	 * project scope.
	 * 
	 * @param key
	 *            The preferences key
	 * @param def
	 *            The default value to return.
	 * @param project
	 *            The IProject
	 * @return The project-specific value for the given key.
	 */
	@SuppressWarnings("unchecked")
	public String getProjectSpecificPreferencesValue(String key, String def,
			IProject project) {
		assert project != null;
		ProjectScope scope = (ProjectScope) projectToScope.get(project);
		if (scope == null) {
			scope = new ProjectScope(project);
			projectToScope.put(project, scope);
		}
		IEclipsePreferences node = scope.getNode(nodeQualifier);
		if (node != null) {
			return node.get(key, def);
		}
		return null;
	}

	/**
	 * Returns the value for the key by first searching for it as a
	 * project-specific and if it is undefined as such, search it as a workspace
	 * property.
	 * 
	 * @param key
	 *            The preferences key.
	 * @param def
	 *            The default value to return.
	 * @param project
	 *            The IProject (may be null).
	 * @return Returns the value for the key.
	 * @see #getProjectSpecificPreferencesValue(String, String, IProject)
	 * @see #getWorkspacePreferencesValue(String)
	 * @see #getWorkspacePreferencesValue(String, IPreferenceStore)
	 */
	public String getPreferencesValue(String key, String def, IProject project) {
		if (project == null) {
			return getWorkspacePreferencesValue(key);
		}
		String projectSpecificPreferencesValue = getProjectSpecificPreferencesValue(
				key, def, project);
		if (projectSpecificPreferencesValue == null) {
			return getWorkspacePreferencesValue(key);
		}

		return projectSpecificPreferencesValue;
	}

	/**
	 * Returns the value for the key, as found in the preferences store.
	 * 
	 * @param key
	 * @return
	 */
	public String getWorkspacePreferencesValue(String key) {
		return preferenceStore == null ? null : preferenceStore.get(key, null);
	}

	/**
	 * Returns the value for the key, as found in the given preferences store.
	 * 
	 * @param key
	 * @param preferenceStore
	 * @return The String value
	 */
	public static String getWorkspacePreferencesValue(String key,
			IEclipsePreferences preferenceStore) {
		return preferenceStore.get(key, null);
	}

	/**
	 * Returns the project-specific value, or null if there is no node for the
	 * project scope.
	 * 
	 * @param key
	 *            The preferences key
	 * @param value
	 *            The preference value.
	 * @param project
	 *            The IProject
	 * @return boolean When the value was set.
	 */
	@SuppressWarnings("unchecked")
	public boolean setProjectSpecificPreferencesValue(String key, String value,
			IProject project) {
		assert project != null;
		ProjectScope scope = (ProjectScope) projectToScope.get(project);
		if (scope == null) {
			scope = new ProjectScope(project);
			projectToScope.put(project, scope);
		}
		IEclipsePreferences node = scope.getNode(nodeQualifier);
		if (node != null) {
			node.put(key, value);
			try {
				node.flush();
			} catch (BackingStoreException e) {
				Logger.logException(e);
			}
			return true;
		}
		return false;
	}	

}
