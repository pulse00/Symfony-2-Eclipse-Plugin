package org.eclipse.symfony.index.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.osgi.util.NLS;
import org.eclipse.symfony.index.Debug;
import org.eclipse.symfony.index.Schema;


/**
 * 
 * DAO for Routes.
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public class RouteDao implements IRouteDao {
	
	private static final String TABLENAME = "ROUTES";

	private static final String Q_INSERT_DECL = Schema
			.readSqlFile("Resources/index/routes/insert_decl.sql"); //$NON-NLS-1$

	/** Cache for insert element reference queries */
	private static final Map<String, String> D_INSERT_QUERY_CACHE = new HashMap<String, String>();

	private final Map<String, PreparedStatement> batchStatements;


	public RouteDao() {

		this.batchStatements = new HashMap<String, PreparedStatement>();		
	}
	
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

		statement.setString(++param, name);
		statement.setString(++param, pattern);
		statement.setString(++param, controller);		
		statement.setString(++param, bundle);
		statement.setString(++param, action);
		statement.setString(++param, path.toString());
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
	
	@Override
	public void commitInsertions() throws SQLException {

		System.err.println("commit routes");
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
	public void deleteRoutesByPath(Connection connection, String name, IPath path) {

		try {
			Statement statement = connection.createStatement();
			statement.execute("DELETE FROM ROUTES WHERE NAME = '"
					+ name + "' AND PATH = '"  + path.toString()+ "'");
			connection.commit();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Override
	public List<Route> findRoutes(Connection connection, IPath path) {

		final List<Route> routes = new ArrayList<Route>();
		
		try {
			
			Statement statement = connection.createStatement();
			String query = "SELECT NAME, PATTERN, CONTROLLER FROM ROUTES WHERE PATH = '" + path + "'";

			ResultSet result = statement.executeQuery(query.toString());
			
			while (result.next()) {
				
				int columnIndex = 0;
				String name = result.getString(++columnIndex);
				String pattern = result.getString(++columnIndex);
				String controller= result.getString(++columnIndex);				
				routes.add(new Route(name, pattern, controller));

			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return routes;

	}	
}