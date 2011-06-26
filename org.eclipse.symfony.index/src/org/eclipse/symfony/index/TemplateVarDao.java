package org.eclipse.symfony.index;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.osgi.util.NLS;

public class TemplateVarDao {

	private static final String TABLENAME = "TEMPLATEVARS";


	private static final String Q_INSERT_DECL = Schema
			.readSqlFile("Resources/index/insert_tempvar_decl.sql"); //$NON-NLS-1$

	/** Cache for insert element reference queries */
	private static final Map<String, String> D_INSERT_QUERY_CACHE = new HashMap<String, String>();

	private final Map<String, PreparedStatement> batchStatements;


	public TemplateVarDao() {

		this.batchStatements = new HashMap<String, PreparedStatement>();		
	}

	public void insert(Connection connection, String varName, String phpClass, String namespace,
			String path, int timestamp)
			throws SQLException {


		String tableName = TABLENAME;
		String query;

		query = D_INSERT_QUERY_CACHE.get(tableName);
		if (query == null) {
			query = NLS.bind(Q_INSERT_DECL, tableName);
			D_INSERT_QUERY_CACHE.put(tableName, query);
		}


		synchronized (batchStatements) {
			PreparedStatement statement = batchStatements.get(query);
			if (statement == null) {
				statement = connection.prepareStatement(query);
				batchStatements.put(query, statement);
			}
			insertBatch(connection, statement, varName, path, namespace, phpClass, timestamp);
		}
	}


	private void insertBatch(Connection connection, PreparedStatement statement, String varName, String path, String namespace, String phpclass, int timestamp)
			throws SQLException {

		int param = 0;

		statement.setString(++param, varName);
		statement.setString(++param, path);
		statement.setString(++param, namespace);
		statement.setString(++param, phpclass);
		statement.setInt(++param, timestamp);
		statement.addBatch();
		
		if (Debug.debugSql) {			
			System.err.println(statement.toString());
		}		
		
		//
		//		if (!isReference) {
		//			H2Cache.addElement(new Element(type, flags, offset, length,
		//					nameOffset, nameLength, name, camelCaseName, metadata, doc,
		//					qualifier, parent, fileId, isReference));
		//		}
	}
	
	public void commitInsertions() throws SQLException {

		synchronized (batchStatements) {
			try {
				for (PreparedStatement statement : batchStatements.values()) {
					try {
						statement.executeBatch();
					} finally {
						statement.close();
					}
				}
			} finally {
				batchStatements.clear();
			}
		}
	}
}