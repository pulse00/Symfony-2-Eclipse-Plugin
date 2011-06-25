package org.eclipse.symfony.index;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.osgi.util.NLS;


public class ServiceDao implements IServiceDao {

	private static final String TABLENAME = "SERVICES";
	
	
	private static final String Q_INSERT_DECL = Schema
			.readSqlFile("Resources/index/insert_decl.sql"); //$NON-NLS-1$

	/** Cache for insert element reference queries */
	private static final Map<String, String> D_INSERT_QUERY_CACHE = new HashMap<String, String>();

	private final Map<String, PreparedStatement> batchStatements;


	public ServiceDao() {

		this.batchStatements = new HashMap<String, PreparedStatement>();		
	}

	public void insert(Connection connection, String path, String name, String phpclass, int timestamp)
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
			insertBatch(connection, statement, path, name, phpclass, timestamp);
		}
		
		System.err.println("service inserted");
	}

	
	private void insertBatch(Connection connection, PreparedStatement statement, String path, String name, String phpclass, int timestamp)
			throws SQLException {

		int param = 0;

		statement.setString(++param, path);
		statement.setString(++param, name);
		statement.setString(++param, phpclass);

		statement.addBatch();
	
//
//		if (!isReference) {
//			H2Cache.addElement(new Element(type, flags, offset, length,
//					nameOffset, nameLength, name, camelCaseName, metadata, doc,
//					qualifier, parent, fileId, isReference));
//		}
	}	
}