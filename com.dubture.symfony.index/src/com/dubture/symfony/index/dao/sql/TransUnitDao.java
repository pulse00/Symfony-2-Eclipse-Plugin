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

import org.eclipse.osgi.util.NLS;

import com.dubture.symfony.index.Schema;
import com.dubture.symfony.index.SymfonyDbFactory;
import com.dubture.symfony.index.dao.ITransUnitDao;
import com.dubture.symfony.index.handler.ITranslationHandler;
import com.dubture.symfony.index.log.Logger;

public class TransUnitDao extends BaseDao implements ITransUnitDao {

	private static final String TABLENAME = "TRANSUNIT";

	private static final String Q_INSERT_DECL = Schema.readSqlFile("Resources/index/translations/insert_decl.sql"); //$NON-NLS-1$

	private static final String QUERY_FIND_BY_PATH = "SELECT NAME, VALUE, LANGUAGE, PATH FROM TRANSUNIT WHERE PATH LIKE ?"; //$NON-NLS-1$
	private static final String QUERY_FIND_BY_NAME_PATH = "SELECT NAME, VALUE, LANGUAGE, PATH FROM TRANSUNIT WHERE NAME = ? AND PATH LIKE ?"; //$NON-NLS-1$+

	private static final String QUERY_DELETE_BY_NAME_PATH_LANGUAGE = "DELETE FROM TRANSUNIT WHERE NAME = ? AND PATH = ? AND LANGUAGE = ?";

	public TransUnitDao() {
		super();
	}

	@Override
	public void insert(Connection connection, String path, String name, String value, String language, int timestamp) throws Exception {
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

	private void insertBatch(PreparedStatement statement, String path, String name, String value, String language, int timestamp) throws SQLException {

		int param = 0;

		statement.setString(++param, path);
		statement.setString(++param, name);
		statement.setString(++param, value);
		statement.setString(++param, language);
		statement.setInt(++param, timestamp);
		statement.addBatch();
	}

	@Override
	public void findTranslations(String path, ITranslationHandler iTranslationHandler) {
		Connection connection = null;
		try {
			connection = SymfonyDbFactory.getInstance().createConnection();
			PreparedStatement statement = connection.prepareStatement(QUERY_FIND_BY_PATH);
			statement.setString(1, escapeLikePattern(path) + LIKE_WILDCARD);

			ResultSet result = statement.executeQuery();
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
		} finally {
			closeIfExists(connection);
		}
	}

	public void findTranslations(String name, String path, ITranslationHandler handler) {
		Connection connection = null;
		try {
			connection = SymfonyDbFactory.getInstance().createConnection();

			PreparedStatement statement = connection.prepareStatement(QUERY_FIND_BY_NAME_PATH);
			statement.setString(1, name);
			statement.setString(2, escapeLikePattern(path) + LIKE_WILDCARD);
			ResultSet result = statement.executeQuery();

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
		} finally {
			closeIfExists(connection);
		}
	}

	@Override
	public void deleteRoutesByPath(String name, String language, String path) {
		Connection connection = null;
		try {
			connection = SymfonyDbFactory.getInstance().createConnection();
			PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_BY_NAME_PATH_LANGUAGE);
			statement.setString(1, name);
			statement.setString(2, language);
			statement.setString(3, path);
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			Logger.logException(e);
		} finally {
			closeIfExists(connection);
		}
	}
}
