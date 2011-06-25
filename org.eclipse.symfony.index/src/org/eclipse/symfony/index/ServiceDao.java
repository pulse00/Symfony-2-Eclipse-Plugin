package org.eclipse.symfony.index;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.osgi.util.NLS;


/**
 * 
 * {@link ServiceDao} Data Access Object for 
 * Symfony services.
 * 
 * TODO: implement Cache layer
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
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
	}


	private void insertBatch(Connection connection, PreparedStatement statement, String path, String name, String phpclass, int timestamp)
			throws SQLException {

		int param = 0;

		statement.setString(++param, path);
		statement.setString(++param, name);
		statement.setString(++param, phpclass);
		statement.setInt(++param, timestamp);
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
	public void truncate(Connection connection) {

		try {
			Statement statement = connection.createStatement();
			statement.execute("TRUNCATE TABLE SERVICES");
			connection.commit();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}


	}	


	public void findAll(Connection connection, IServiceHandler handler) {

		try {
			
			Statement statement = connection.createStatement();
			String query = "SELECT PATH, NAME, PHPCLASS FROM SERVICES";

			ResultSet result = statement.executeQuery(query.toString());
			
			while (result.next()) {
				
				int columnIndex = 0;
				String path = result.getString(++columnIndex);
				String name = result.getString(++columnIndex);
				String phpClass = result.getString(++columnIndex);
				
				Service service = new Service(name, phpClass, path);
				handler.handle(service);

			}
		} catch(Exception e) {
			e.printStackTrace();
		}
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
	public Service find(Connection connection, String name) {

		try {
			
			Statement statement = connection.createStatement();
			statement.setMaxRows(1);
			String query = "SELECT PATH, NAME, PHPCLASS FROM SERVICES WHERE NAME = '" + name + "'";

			ResultSet result = statement.executeQuery(query.toString());
			
			result.first();

			String path = result.getString(1);
			String id = result.getString(2);
			String phpClass = result.getString(3);
			
			Service service = new Service(id, phpClass, path);
			return service;
			
			
		} catch(Exception e) {

			e.printStackTrace();

		}		
		
		return null;

	}

	@Override
	public void deleteServices(Connection connection, String path) {

		try {
			Statement statement = connection.createStatement();
			statement.execute("DELETE FROM SERVICES WHERE PATH = '" + path + "'");
			connection.commit();
			
		} catch (SQLException e) {

			e.printStackTrace();
		}

		
	}
}