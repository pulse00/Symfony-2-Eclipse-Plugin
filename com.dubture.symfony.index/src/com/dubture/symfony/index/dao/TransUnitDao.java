package com.dubture.symfony.index.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.osgi.util.NLS;

import com.dubture.symfony.index.ITranslationHandler;
import com.dubture.symfony.index.Schema;
import com.dubture.symfony.index.log.Logger;

public class TransUnitDao implements ITransUnitDao {

	private static final String TABLENAME = "TRANSUNIT";

	private static final String Q_INSERT_DECL = Schema
			.readSqlFile("Resources/index/translations/insert_decl.sql"); //$NON-NLS-1$

	/** Cache for insert element reference queries */
	private static final Map<String, String> D_INSERT_QUERY_CACHE = new HashMap<String, String>();

	private final Map<String, PreparedStatement> batchStatements;


	public TransUnitDao() {

		this.batchStatements = new HashMap<String, PreparedStatement>();		
	}

	public void insert(Connection connection, String path, String name, String value, String language, int timestamp)
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
			insertBatch(connection, statement, path, name, value, language, timestamp);
		}
	}
	
	private void insertBatch(Connection connection, PreparedStatement statement, String path, String name, String value, String language, int timestamp)
			throws SQLException {

		int param = 0;

		statement.setString(++param, path);
		statement.setString(++param, name);
		statement.setString(++param, value);
		statement.setString(++param, language);
		statement.setInt(++param, timestamp);
		statement.addBatch();
		
		//Logger.debugMSG(statement.toString());
		
		//
		//		if (!isReference) {
		//			H2Cache.addElement(new Element(type, flags, offset, length,
		//					nameOffset, nameLength, name, camelCaseName, metadata, doc,
		//					qualifier, parent, fileId, isReference));
		//		}
	}

	
	@Override
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

	@Override
	public void findTranslations(Connection connection, String path, ITranslationHandler iTranslationHandler) {

		try {
			
			Statement statement = connection.createStatement();
			String query = "SELECT NAME, VALUE, LANGUAGE, PATH FROM TRANSUNIT WHERE PATH LIKE '" + path + "%'";

			ResultSet result = statement.executeQuery(query.toString());
			
			while (result.next()) {
				
				int columnIndex = 0;
				String name = result.getString(++columnIndex);
				String value = result.getString(++columnIndex);
				String language = result.getString(++columnIndex);
				String _path = result.getString(++columnIndex);
				
				iTranslationHandler.handle(name, value, language, _path);

			}
		} catch(Exception e) {
			Logger.logException(e);
		}
	}
}
