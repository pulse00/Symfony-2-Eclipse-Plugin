/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.index.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.osgi.util.NLS;

import com.dubture.symfony.index.Schema;
import com.dubture.symfony.index.dao.ITransUnitDao;
import com.dubture.symfony.index.handler.ITranslationHandler;
import com.dubture.symfony.index.log.Logger;

public class TransUnitDao extends BaseDao implements ITransUnitDao {

	private static final String TABLENAME = "TRANSUNIT";

	private static final String Q_INSERT_DECL = Schema.readSqlFile("Resources/index/translations/insert_decl.sql"); //$NON-NLS-1$

	public TransUnitDao(Connection connection) {
		super(connection);
	}

	public void insert(String path, String name, String value, String language, int timestamp)
			throws Exception {

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
			insertBatch(statement, path, name, value, language, timestamp);
		}
	}

	private void insertBatch(PreparedStatement statement, String path, String name, String value, String language,
			int timestamp) throws SQLException {

		int param = 0;

		statement.setString(++param, path);
		statement.setString(++param, name);
		statement.setString(++param, value);
		statement.setString(++param, language);
		statement.setInt(++param, timestamp);
		statement.addBatch();

		// Logger.debugMSG(statement.toString());

		//
		// if (!isReference) {
		// H2Cache.addElement(new Element(type, flags, offset, length,
		// nameOffset, nameLength, name, camelCaseName, metadata, doc,
		// qualifier, parent, fileId, isReference));
		// }
	}

	@Override
	public void findTranslations(String path, ITranslationHandler iTranslationHandler) {
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
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	public void findTranslations(String name, String path, ITranslationHandler handler) {
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT NAME, VALUE, LANGUAGE, PATH FROM TRANSUNIT WHERE NAME = '" + name
					+ "' AND PATH LIKE '" + path + "%'";

			ResultSet result = statement.executeQuery(query.toString());

			while (result.next()) {
				int columnIndex = 0;
				String _name = result.getString(++columnIndex);
				String value = result.getString(++columnIndex);
				String language = result.getString(++columnIndex);
				String _path = result.getString(++columnIndex);
				handler.handle(_name, value, language, _path);
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	@Override
	public void deleteRoutesByPath(String name, String language, String path) {
		try {
			Statement statement = connection.createStatement();
			statement.execute("DELETE FROM TRANSUNIT WHERE NAME = '" + name + "' AND PATH = '" + path
					+ "' AND LANGUAGE = '" + language + "'");
			connection.commit();
		} catch (SQLException e) {
			Logger.logException(e);
		}
	}
}
