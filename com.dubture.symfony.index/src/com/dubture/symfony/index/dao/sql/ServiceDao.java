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
import com.dubture.symfony.index.dao.IServiceDao;
import com.dubture.symfony.index.handler.IServiceHandler;
import com.dubture.symfony.index.log.Logger;
import com.dubture.symfony.index.model.Service;

/**
 * 
 * {@link ServiceDao} Data Access Object for Symfony services.
 * 
 * TODO: implement Cache layer
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
public class ServiceDao extends BaseDao implements IServiceDao {

	private static final String TABLENAME = "SERVICES"; //$NON-NLS-1$
	private static final String QUERY_TRUNCATE = "TRUNCATE TABLE SERVICES"; //$NON-NLS-1$
	private static final String QUERY_FIND_ALL = "SELECT PATH, NAME, PHPCLASS, _PUBLIC, TAGS  FROM SERVICES"; //$NON-NLS-1$
	private static final String QUERY_FIND_ONE_BY_NAME = "SELECT PATH, NAME, PHPCLASS FROM SERVICES WHERE NAME = ? LIMIT 1"; //$NON-NLS-1$
	private static final String QUERY_FIND_BY_PATH = "SELECT NAME, PHPCLASS, PATH, _PUBLIC, TAGS FROM SERVICES WHERE PATH LIKE ?"; //$NON-NLS-1$
	private static final String QUERY_FIND_BY_PATH_CLASS_NAME = "SELECT NAME, PHPCLASS, PATH, _PUBLIC, TAGS FROM SERVICES WHERE PATH LIKE ? AND ? != ''"; //$NON-NLS-1$
	private static final String QUERY_FIND_ONE_BY_ID_PATH = "SELECT NAME, PHPCLASS, PATH, _PUBLIC, TAGS FROM SERVICES WHERE PATH LIKE ? AND NAME = ? LIMIT 1"; //$NON-NLS-1$
	private static final String QUERY_FIND_TAGS_BY_PATH = "SELECT TAGS FROM SERVICES WHERE PATH LIKE ?";

	private static final String QUERY_DELETE_BY_PATH = "DELETE FROM SERVICES WHERE PATH = ?"; //$NON-NLS-1$
	private static final String QUERY_DELETE_BY_ID_PATH = "DELETE FROM SERVICES WHERE NAME = ? AND PATH = ?"; //$NON-NLS-1$

	private static final String Q_INSERT_DECL = Schema.readSqlFile("Resources/index/insert_decl.sql"); //$NON-NLS-1$

	public ServiceDao() {
		super();
	}

	public void insert(Connection connection, String name, String phpclass, String _public, List<String> tags, String path, int timestamp) throws Exception {
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
			insertBatch(statement, path, name, phpclass, _public, tags, timestamp);
		}
	}

	private void insertBatch(PreparedStatement statement, String path, String name, String phpclass, String _public, List<String> tags, int timestamp)
			throws SQLException {

		int param = 0;
		String tagString = "";

		if (tags != null) {
			for (int i = 0; i < tags.size(); i++) {
				tagString += tags.get(i);
				if (i++ < tags.size()) {
					tagString += ",";
				}
			}
		}
		statement.setString(++param, path);
		statement.setString(++param, name);
		statement.setString(++param, phpclass);
		statement.setString(++param, _public);
		statement.setString(++param, tagString);
		statement.setInt(++param, timestamp);
		statement.addBatch();

		// Logger.debugMSG(statement.toString());
	}

	@Override
	public void truncate() {
		Connection connection = null;
		try {
			connection = SymfonyDbFactory.getInstance().createConnection();
			Statement statement = connection.createStatement();
			statement.execute(QUERY_TRUNCATE);
			connection.commit();
		} catch (SQLException e) {
			Logger.logException(e);
		} finally {
			closeIfExists(connection);
		}
	}

	public void findAll(IServiceHandler handler) {
		Connection connection = null;
		try {
			connection = SymfonyDbFactory.getInstance().createConnection();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(QUERY_FIND_ALL);

			while (result.next()) {
				int columnIndex = 0;
				String path = result.getString(++columnIndex);
				String name = result.getString(++columnIndex);
				String phpClass = result.getString(++columnIndex);
				String _public = result.getString(++columnIndex);
				String tags = result.getString(++columnIndex);
				handler.handle(name, path, phpClass, _public, tags);
			}
		} catch (Exception e) {
			Logger.logException(e);
		} finally {
			closeIfExists(connection);
		}
	}

	@Override
	public Service find(String name) {
		Connection connection = null;
		try {
			connection = SymfonyDbFactory.getInstance().createConnection();
			PreparedStatement prepareStatement = connection.prepareStatement(QUERY_FIND_ONE_BY_NAME);
			prepareStatement.setString(1, name);
			ResultSet result = prepareStatement.executeQuery();
			result.first();

			String path = result.getString(1);
			String id = result.getString(2);
			String phpClass = result.getString(3);

			Service service = new Service(id, phpClass, path);
			return service;
		} catch (Exception e) {
			Logger.logException(e);
		} finally {
			closeIfExists(connection);
		}
		return null;
	}

	@Override
	public void deleteServices(String path) {
		Connection connection = null;
		try {
			connection = SymfonyDbFactory.getInstance().createConnection();
			PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_BY_PATH);
			statement.setString(1, path);
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			Logger.logException(e);
		} finally {
			closeIfExists(connection);
		}
	}

	@Override
	public void findServicesByPath(String path, IServiceHandler handler) {
		Connection connection = null;
		try {
			connection = SymfonyDbFactory.getInstance().createConnection();
			PreparedStatement statement = connection.prepareStatement(QUERY_FIND_BY_PATH);
			statement.setString(1, escapeLikePattern(path) + LIKE_WILDCARD);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				int columnIndex = 0;
				String id = result.getString(++columnIndex);
				String phpClass = result.getString(++columnIndex);
				String _path = result.getString(++columnIndex);
				String _public = result.getString(++columnIndex);
				String tags = result.getString(++columnIndex);
				handler.handle(id, phpClass, _path, _public, tags);
			}
		} catch (Exception e) {
			Logger.logException(e);
		} finally {
			closeIfExists(connection);
		}
	}

	@Override
	public void findService(String id, String path, IServiceHandler handler) {
		Connection connection = null;
		try {
			connection = SymfonyDbFactory.getInstance().createConnection();
			PreparedStatement statement = connection.prepareStatement(QUERY_FIND_ONE_BY_ID_PATH);
			statement.setString(1, escapeLikePattern(path) + LIKE_WILDCARD);
			statement.setString(2, id.replaceAll("['\"]", "")); //$NON-NLS-1$ //$NON-NLS-2$

			ResultSet result = statement.executeQuery();
			result.first();

			if (!result.isFirst()) {
				return;
			}

			String _id = result.getString(1);
			String _phpClass = result.getString(2);
			String _path = result.getString(3);
			String _public = result.getString(4);
			String _tags = result.getString(5);
			handler.handle(_id, _phpClass, _path, _public, _tags);
		} catch (Exception e) {
			Logger.logException(e);
		} finally {
			closeIfExists(connection);
		}
	}
	
	/**
	 * 
	 */
	public void findServicesByClassName(String className, String path, IServiceHandler handler)
	{
		Connection connection = null;
		try {
			connection = SymfonyDbFactory.getInstance().createConnection();
			PreparedStatement statement = connection.prepareStatement(QUERY_FIND_BY_PATH_CLASS_NAME);
			statement.setString(1, escapeLikePattern(path) + LIKE_WILDCARD);
			statement.setString(2, className.replaceAll("['\"]", "")); //$NON-NLS-1$ //$NON-NLS-2$
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				int columnIndex = 0;
				String name = result.getString(++columnIndex);
				String phpClass = result.getString(++columnIndex);
				String ppath = result.getString(++columnIndex);
				String _public = result.getString(++columnIndex);
				String tags = result.getString(++columnIndex);
				if (phpClass.startsWith("Acme")) {
					System.out.println(phpClass);
				}
				handler.handle(name, phpClass, ppath, _public, tags);
			}
		} catch (Exception e) {
			Logger.logException(e);
		} finally {
			closeIfExists(connection);
		}
	}

	@Override
	public List<String> findTags(IPath path) {
		List<String> tags = new ArrayList<String>();
		Connection connection = null;
		try {
			connection = SymfonyDbFactory.getInstance().createConnection();
			PreparedStatement statement = connection.prepareStatement(QUERY_FIND_TAGS_BY_PATH);
			statement.setString(1, escapeLikePattern(path.toString()) + LIKE_WILDCARD);

			ResultSet result = statement.executeQuery();

			while (result.next()) {
				int columnIndex = 0;
				String _tags = result.getString(++columnIndex);
				String[] t = _tags.split(",");
				for (String tag : t) {
					if (!tags.contains(tag))
						tags.add(tag);
				}
			}
		} catch (Exception e) {
			Logger.logException(e);
		} finally {
			closeIfExists(connection);
		}

		return tags;
	}

	@Override
	public void delete(String id, String path) {
		Connection connection = null;
		try {
			connection = SymfonyDbFactory.getInstance().createConnection();
			PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_BY_ID_PATH);
			statement.setString(1, id);
			statement.setString(2, path);
			connection.commit();
		} catch (SQLException e) {
			Logger.logException(e);
		} finally {
			closeIfExists(connection);
		}
	}
}
