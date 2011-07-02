package org.eclipse.symfony.index.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.osgi.util.NLS;
import org.eclipse.symfony.index.IServiceHandler;
import org.eclipse.symfony.index.Schema;
import org.eclipse.symfony.index.log.Logger;


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
		
		Logger.debugMSG(statement.toString());
		
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

			Logger.logException(e);
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
				handler.handle(name, path, phpClass);

			}
		} catch(Exception e) {
			Logger.logException(e);
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

			Logger.logException(e);

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

			Logger.logException(e);
		}

		
	}

	@Override
	public void findServicesByPath(Connection connection, String path, IServiceHandler handler) {

		try {
			
			Statement statement = connection.createStatement();
			String query = "SELECT NAME, PHPCLASS, PATH FROM SERVICES WHERE PATH LIKE '" + path + "%'";

			ResultSet result = statement.executeQuery(query.toString());
			
			while (result.next()) {
				
				int columnIndex = 0;
				String id= result.getString(++columnIndex);
				String phpClass = result.getString(++columnIndex);
				String _path = result.getString(++columnIndex);
				handler.handle(id, phpClass, _path);

			}
		} catch(Exception e) {
			Logger.logException(e);
		}
	}

	@Override
	public void findService(Connection connection, String id, String path,
			IServiceHandler handler) {

		try {
			
			Statement statement = connection.createStatement();
			StringBuilder builder = new StringBuilder("SELECT NAME, PHPCLASS, PATH FROM SERVICES WHERE PATH LIKE '");
			builder.append(path);
			builder.append("%' AND NAME = '");
			builder.append(id.replaceAll("['\"]", ""));
			builder.append("'");

			ResultSet result = statement.executeQuery(builder.toString());
			
			result.first();
			
			if (!result.isFirst()) {
				return;
			}

			String _id= result.getString(1);
			String _phpClass = result.getString(2);
			String _path = result.getString(3);
			
			handler.handle(_id, _phpClass, _path);			
			
		} catch(Exception e) {
			Logger.logException(e);
		}
	}


}