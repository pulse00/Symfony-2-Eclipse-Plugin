package org.eclipse.symfony.index;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.osgi.util.NLS;
import org.eclipse.symfony.index.preferences.SymfonyIndexPreferences;
import org.osgi.service.prefs.BackingStoreException;

public class Schema {


	public static final String VERSION = "0.1"; //$NON-NLS-1$

	/** Contains already created tables names */
	private static final Set<String> TABLES_CACHE = new HashSet<String>();

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
			} finally {
				statement.close();
			}

			// Store new schema version:
			storeSchemaVersion(VERSION);

		} catch (SQLException e) {
			
			e.printStackTrace();
			throw e;
		}
	}


	/**
	 * Creates elements table
	 * 
	 * @param connection
	 *            Database connection
	 * @param tableName
	 *            Table name
	 * @param isReference
	 *            Whether to create table for element references or element
	 *            declarations
	 * @throws SQLException
	 */
	public void createTable(Connection connection, String tableName) throws SQLException {

		synchronized (TABLES_CACHE) {
			if (TABLES_CACHE.add(tableName)) {

				String query = readSqlFile("Resources/index/element_decl.sql"); //$NON-NLS-1$
				query = NLS.bind(query, tableName);

				try {
					Statement statement = connection.createStatement();
					try {
						statement.executeUpdate(query);
					} finally {
						statement.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
					throw e;
				}
			}
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
		IEclipsePreferences node = new InstanceScope()
		.getNode(SymfonyIndex.PLUGIN_ID);
		node.put(SymfonyIndexPreferences.SCHEMA_VERSION, newVersion);
		try {
			node.flush();
		} catch (BackingStoreException e) {
		}
	}

	static String readSqlFile(String sqlFile) {
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
			e.printStackTrace();
		}
		return null;
	}
	

	
}