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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.osgi.util.NLS;

import com.dubture.symfony.index.Schema;
import com.dubture.symfony.index.SymfonyDbFactory;
import com.dubture.symfony.index.dao.IParameterDao;
import com.dubture.symfony.index.handler.IParameterHandler;
import com.dubture.symfony.index.log.Logger;
import com.dubture.symfony.index.model.Parameter;

public class ParameterDao extends BaseDao implements IParameterDao {

	private static final String TABLENAME = "PARAMETERS";
	private static final String Q_INSERT_DECL = Schema.readSqlFile("Resources/index/parameters/insert_decl.sql"); //$NON-NLS-1$
	
	private static final String QUERY_FIND_ALL = "SELECT KEY, VALUE, PATH FROM PARAMETERS"; //$NON-NLS-1$
	private static final String QUERY_FIND_BY_PATH = "SELECT KEY, VALUE, PATH FROM PARAMETERS WHERE PATH LIKE ?"; //$NON-NLS-1$
	
	private static final String QUERY_DELETE_BY_PATH = "DELETE FROM PARAMETERS WHERE PATH = ?"; //$NON-NLS-1$

	public ParameterDao() {
		super();
	}

	@Override
	public void insert(Connection connection, String key, String value, IPath path) throws Exception {
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
			insertBatch(statement, key, value, path);
		}
	}

	private void insertBatch(PreparedStatement statement, String key, String value, IPath path) throws Exception {
		int param = 0;
		statement.setString(++param, key);
		statement.setString(++param, value);
		statement.setString(++param, path.toString());
		statement.addBatch();
		
	}

	@Override
	public void delete(String id, String path) {

	}

	@Override
	public void findAll(IParameterHandler handler) {
		Connection connection = null;
		try {
			connection = SymfonyDbFactory.getInstance().createConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(QUERY_FIND_ALL);
			while (result.next()) {
				int columnIndex = 0;
				String key = result.getString(++columnIndex);
				String value = result.getString(++columnIndex);
				String path = result.getString(++columnIndex);
				handler.handle(key, value, path);
			}
		} catch (Exception e) {
			Logger.logException(e);
		} finally {
			closeIfExists(connection);
		}
	}

	public List<Parameter> findParameters(IPath path) throws Exception {
		final List<Parameter> params = new ArrayList<Parameter>();
		Connection connection = null;
		try {
			connection = SymfonyDbFactory.getInstance().createConnection();
			PreparedStatement statement = connection.prepareStatement(QUERY_FIND_BY_PATH);
			statement.setString(1, escapeLikePattern(path.toString()) + LIKE_WILDCARD);
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				int columnIndex = 0;
				String key = result.getString(++columnIndex);
				String value = result.getString(++columnIndex);
				params.add(new Parameter(key, value));
			}
		} finally {
			closeIfExists(connection);
		}

		return params;
	}


	@Override
	public void deleteParameters(String path) {
		Connection connection = null;
		try {
			connection = SymfonyDbFactory.getInstance().createConnection();
			PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_BY_PATH);
			statement.setString(1, path);
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			Logger.logException(e);
		}
	}
}
