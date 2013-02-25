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
import com.dubture.symfony.index.dao.IParameterDao;
import com.dubture.symfony.index.handler.IParameterHandler;
import com.dubture.symfony.index.log.Logger;
import com.dubture.symfony.index.model.Parameter;

public class ParameterDao extends BaseDao implements IParameterDao {

	private static final String TABLENAME = "PARAMETERS";
	private static final String Q_INSERT_DECL = Schema.readSqlFile("Resources/index/parameters/insert_decl.sql"); //$NON-NLS-1$

	public ParameterDao(Connection connection) {
		super(connection);
	}

	@Override
	public void insert(String key, String value, IPath path) throws Exception {

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
		System.err.println(key + " " + value);
		statement.setString(++param, key);
		statement.setString(++param, value);
		statement.setString(++param, path.toString());
		statement.addBatch();
		
		//
		// if (!isReference) {
		// H2Cache.addElement(new Element(type, flags, offset, length,
		// nameOffset, nameLength, name, camelCaseName, metadata, doc,
		// qualifier, parent, fileId, isReference));
		// }
	}

	@Override
	public void delete(String id, String path) {

	}

	@Override
	public void findAll(IParameterHandler handler) {
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT KEY, VALUE, PATH FROM PARAMETERS";
			ResultSet result = statement.executeQuery(query.toString());
			while (result.next()) {
				int columnIndex = 0;
				String key = result.getString(++columnIndex);
				String value = result.getString(++columnIndex);
				String path = result.getString(++columnIndex);
				handler.handle(key, value, path);
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	public List<Parameter> findParameters(IPath path) throws Exception {
		return searchParameters("SELECT KEY, VALUE, PATH FROM PARAMETERS WHERE PATH LIKE '" + path + "%'");
	}

	private List<Parameter> searchParameters(String sql) throws Exception {

		final List<Parameter> params = new ArrayList<Parameter>();
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(sql);
		while (result.next()) {
			int columnIndex = 0;
			String key = result.getString(++columnIndex);
			String value = result.getString(++columnIndex);
			params.add(new Parameter(key, value));
		}

		return params;
	}

	@Override
	public void deleteParameters(String path) {
		try {
			Statement statement = connection.createStatement();
			statement.execute("DELETE FROM PARAMETERS WHERE PATH = '" + path + "'");
			connection.commit();
		} catch (SQLException e) {
			Logger.logException(e);
		}
	}
}
