package com.dubture.symfony.index.dao;

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
import com.dubture.symfony.index.log.Logger;


/**
 * 
 * DAO for Routes.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class RouteDao extends BaseDao implements IRouteDao {
	
	private static final String TABLENAME = "ROUTES";

	private static final String Q_INSERT_DECL = Schema
			.readSqlFile("Resources/index/routes/insert_decl.sql"); //$NON-NLS-1$

	
	public void insert(Connection connection, String name, String pattern, 
			String controller, String bundle, String action, IPath path)
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
			insertBatch(connection, statement, name, pattern, controller, bundle, action, path);
		}
	}
	
	private void insertBatch(Connection connection, PreparedStatement statement, 
			String name, String pattern, String controller, String bundle, String action, IPath path)
					throws SQLException {

		int param = 0;

		statement.setString(++param, name.replaceAll("['\"]", ""));
		statement.setString(++param, pattern);
		statement.setString(++param, controller);		
		statement.setString(++param, bundle);
		statement.setString(++param, action);
		statement.setString(++param, path.toString());
		statement.addBatch();

		
		
		//
		//		if (!isReference) {
		//			H2Cache.addElement(new Element(type, flags, offset, length,
		//					nameOffset, nameLength, name, camelCaseName, metadata, doc,
		//					qualifier, parent, fileId, isReference));
		//		}
	}
	
	@Override
	public void deleteRoutesByPath(Connection connection, String name, IPath path) {

		try {
			Statement statement = connection.createStatement();
			statement.execute("DELETE FROM ROUTES WHERE NAME = '"
					+ name + "' AND PATH = '"  + path.toString()+ "'");
			connection.commit();
			
		} catch (SQLException e) {

			Logger.logException(e);
		}
	}

	@Override
	public List<Route> findRoutes(Connection connection, IPath path) {

		String sql = "SELECT NAME, PATTERN, CONTROLLER, BUNDLE, ACTION FROM ROUTES WHERE PATH LIKE '" + path + "%'";
		return searchRoutes(connection, sql);

	}
	
	public List<Route> findRoutesByBundle(Connection connection, String bundle, IPath path) {
		
		String sql = "SELECT NAME, PATTERN, CONTROLLER, BUNDLE, ACTION FROM ROUTES WHERE BUNDLE = '" + bundle + "' AND PATH LIKE '" + path + "%'";
		return searchRoutes(connection, sql);
		
		
	}
	

	@Override
	public List<Route> findRoutesByController(Connection connection,
			String bundleAlias, String controller, IPath path) {

		String sql = "SELECT NAME, PATTERN, CONTROLLER, BUNDLE, ACTION FROM ROUTES WHERE BUNDLE = '" + bundleAlias + "' AND CONTROLLER = '" + controller + "' AND PATH LIKE '" + path + "%'";
		return searchRoutes(connection, sql);
		
	}	
	
	
	private List<Route> searchRoutes(Connection connection, String sql) {
		
		final List<Route> routes = new ArrayList<Route>();
		
		try {
			
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			while (result.next()) {
				
				
				int columnIndex = 0;
				String name = result.getString(++columnIndex);
				String pattern = result.getString(++columnIndex);
				String controller = result.getString(++columnIndex);				
				String bundle = result.getString(++columnIndex);
				String action = result.getString(++columnIndex);
				
				routes.add(new Route(bundle, controller, action, name, pattern));

			}
		} catch(Exception e) {
			Logger.logException(e);
		}
		
		return routes;		
		
	}

	@Override
	public Route findRoute(Connection connection, String route, IPath path) {

		final List<Route> routes = new ArrayList<Route>();
		
		try {
			
			Statement statement = connection.createStatement();
			String query = "SELECT NAME, PATTERN, CONTROLLER, BUNDLE, ACTION FROM ROUTES WHERE PATH LIKE '" + path + "%' AND NAME = '" + route + "'";

			ResultSet result = statement.executeQuery(query.toString());
			
			while (result.next()) {
				int columnIndex = 0;
				String name = result.getString(++columnIndex);
				String pattern = result.getString(++columnIndex);
				String controller = result.getString(++columnIndex);				
				String bundle = result.getString(++columnIndex);
				String action = result.getString(++columnIndex);
				
				routes.add(new Route(bundle, controller, action, name, pattern));

			}
		} catch(Exception e) {
			Logger.logException(e);
		}
		
		if (routes.size() <= 0)
			
			return null;
		
		return routes.get(0);
			
		
		
	}

	@Override
	public void insertResource(Connection connection, String path, String type,
			String prefix, IPath fullPath) {

		
		
	}
}