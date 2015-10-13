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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.osgi.util.NLS;

import com.dubture.symfony.index.Schema;
import com.dubture.symfony.index.SymfonyDbFactory;
import com.dubture.symfony.index.dao.IRouteDao;
import com.dubture.symfony.index.log.Logger;
import com.dubture.symfony.index.model.Route;

/**
 * DAO for Routes.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public class RouteDao extends BaseDao implements IRouteDao {

	private static final String TABLENAME = "ROUTES";
	private static final String Q_INSERT_DECL = Schema.readSqlFile("Resources/index/routes/insert_decl.sql"); //$NON-NLS-1$

	private static final String QUERY_FIND_BY_PATH = "SELECT NAME, PATTERN, CONTROLLER, BUNDLE, ACTION FROM ROUTES WHERE PATH LIKE ?"; //$NON-NLS-1$
	private static final String QUERY_FIND_BY_BUNDLE_PATH = "SELECT NAME, PATTERN, CONTROLLER, BUNDLE, ACTION FROM ROUTES WHERE BUNDLE = ? AND PATH LIKE ?"; //$NON-NLS-1$
	private static final String QUERY_FIND_BY_BUNDLE_CONTROLLER_PATH = "SELECT NAME, PATTERN, CONTROLLER, BUNDLE, ACTION FROM ROUTES WHERE BUNDLE = ? AND CONTROLLER = ? AND PATH LIKE ?"; //$NON-NLS-1$
	private static final String QUERY_FIND_BY_NAME_PATH = "SELECT NAME, PATTERN, CONTROLLER, BUNDLE, ACTION FROM ROUTES WHERE NAME LIKE ? AND PATH LIKE ?"; //$NON-NLS-1$
	private static final String QUERY_FIND_ONE_BY_NAME_PATH = "SELECT NAME, PATTERN, CONTROLLER, BUNDLE, ACTION FROM ROUTES WHERE NAME = ? AND PATH LIKE ? LIMIT 1"; //$NON-NLS-1$

	private static final String QUERY_DELETE_BY_ROUTE_PATH = "DELETE FROM ROUTES WHERE NAME = ? AND PATH = ?"; //$NON-NLS-1$

	public RouteDao() {
		super();
	}

	public void insert(Connection connection, String name, String pattern, String controller, String bundle, String action, IPath path) throws Exception {
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
			insertBatch(statement, name, pattern, controller, bundle, action, path);
		}
	}

	private void insertBatch(PreparedStatement statement, String name, String pattern, String controller, String bundle, String action, IPath path)
			throws Exception {
		int param = 0;
		statement.setString(++param, name.replaceAll("['\"]", ""));
		statement.setString(++param, pattern);
		statement.setString(++param, controller);
		statement.setString(++param, bundle);
		statement.setString(++param, action);
		statement.setString(++param, path.toString());
		statement.addBatch();
	}

	@Override
	public void deleteRoutesByPath(String name, IPath path) {
		Connection connection = null;
		try {
			connection = SymfonyDbFactory.getInstance().createConnection();
			PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_BY_ROUTE_PATH);
			statement.setString(1, name);
			statement.setString(2, path.toString());
			statement.execute();
			connection.commit();

		} catch (SQLException e) {
			Logger.logException(e);
		} finally {
			closeIfExists(connection);
		}
	}

	@Override
	public List<Route> findRoutes(IPath path) {
		return searchRoutes(QUERY_FIND_BY_PATH, new String[] { escapeLikePattern(path.toString()) + LIKE_WILDCARD });
	}

	public List<Route> findRoutesByBundle(String bundle, IPath path) {
		return searchRoutes(QUERY_FIND_BY_BUNDLE_PATH, new String[] { bundle, escapeLikePattern(path.toString()) + LIKE_WILDCARD });
	}

	@Override
	public List<Route> findRoutesByController(String bundleAlias, String controller, IPath path) {
		return searchRoutes(QUERY_FIND_BY_BUNDLE_CONTROLLER_PATH, new String[] { bundleAlias, controller, escapeLikePattern(path.toString()) + LIKE_WILDCARD });
	}

	@Override
	public List<Route> findRoutes(String prefix, IPath path) {
		return searchRoutes(QUERY_FIND_BY_NAME_PATH, new String[] { escapeLikePattern(prefix) + LIKE_WILDCARD,
				escapeLikePattern(path.toString()) + LIKE_WILDCARD });
	}

	private List<Route> searchRoutes(String sql, String[] params) {

		final List<Route> routes = new ArrayList<Route>();
		Connection connection = null;
		try {
			connection = SymfonyDbFactory.getInstance().createConnection();

			PreparedStatement statement = connection.prepareStatement(sql);
			int pos = 1;
			for (String param : params) {
				statement.setString(pos++, param);
			}
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				int columnIndex = 0;
				String name = result.getString(++columnIndex);
				String pattern = result.getString(++columnIndex);
				String controller = result.getString(++columnIndex);
				String bundle = result.getString(++columnIndex);
				String action = result.getString(++columnIndex);
				routes.add(new Route(bundle, controller, action, name, pattern));
			}
		} catch (Exception e) {
			Logger.logException(e);
		} finally {
			closeIfExists(connection);
		}

		return routes;
	}

	@Override
	public Route findRoute(String route, IPath path) {
		final List<Route> routes = searchRoutes(QUERY_FIND_ONE_BY_NAME_PATH, new String[] { route, escapeLikePattern(path.toString()) + LIKE_WILDCARD });

		if (routes.size() <= 0) {
			return null;
		}

		return routes.get(0);
	}

	@Override
	public void insertResource(String path, String type, String prefix, IPath fullPath) {

	}

}
