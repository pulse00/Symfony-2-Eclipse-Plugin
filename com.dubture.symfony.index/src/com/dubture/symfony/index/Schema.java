package com.dubture.symfony.index;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.service.prefs.BackingStoreException;

import com.dubture.symfony.index.log.Logger;
import com.dubture.symfony.index.preferences.SymfonyIndexPreferences;


/**
 * 
 * 
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
public class Schema {


	public static final String VERSION = "0.6"; //$NON-NLS-1$

	/**
	 * Creates the database schema using given connection.
	 * 
	 * @param connection
	 *            Database connection
	 * @throws SQLException
	 */
	public void initialize(Connection connection) throws SQLException {
		try {
			Statement statement = connection.createStatement();
			try {
				statement.executeUpdate(readSqlFile("Resources/index/basic.sql")); //$NON-NLS-1$
				statement.executeUpdate(readSqlFile("Resources/index/routes/basic.sql")); //$NON-NLS-1$
				statement.executeUpdate(readSqlFile("Resources/index/translations/basic.sql")); //$NON-NLS-1$
				statement.executeUpdate(readSqlFile("Resources/index/resources/basic.sql")); //$NON-NLS-1$
			} finally {
				statement.close();
			}

			// Store new schema version:
			storeSchemaVersion(VERSION);

		} catch (SQLException e) {
			
			Logger.logException(e);
			throw e;
		}
	}


	/**
	 * Checks whether the schema version is compatible with the stored one.
	 */
	public boolean isCompatible() {
		String storedVersion = getStoredSchemaVersion();
		if (storedVersion != null && VERSION.equals(storedVersion)) {
			return true;
		}
		return false;
	}

	private String getStoredSchemaVersion() {
		return Platform.getPreferencesService().getString(SymfonyIndex.PLUGIN_ID,
				SymfonyIndexPreferences.SCHEMA_VERSION, null, null);
	}

	private void storeSchemaVersion(String newVersion) {
		IEclipsePreferences node = InstanceScope.INSTANCE
		.getNode(SymfonyIndex.PLUGIN_ID);
		node.put(SymfonyIndexPreferences.SCHEMA_VERSION, newVersion);
		try {
			node.flush();
		} catch (BackingStoreException e) {
		}
	}

	public static String readSqlFile(String sqlFile) {
		try {
			URL url = FileLocator.find(SymfonyIndex.getDefault().getBundle(),
					new Path(sqlFile), null);
			URL resolved = FileLocator.resolve(url);

			StringBuilder buf = new StringBuilder();
			BufferedReader r = new BufferedReader(new InputStreamReader(
					resolved.openStream()));
			try {
				String line;
				while ((line = r.readLine()) != null) {
					buf.append(line).append('\n');
				}
			} finally {
				r.close();
			}
			return buf.toString();
		} catch (IOException e) {
			Logger.logException(e);
		}
		return null;
	}
	

	
}