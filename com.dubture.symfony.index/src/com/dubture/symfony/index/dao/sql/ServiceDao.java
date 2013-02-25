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
import com.dubture.symfony.index.dao.IServiceDao;
import com.dubture.symfony.index.handler.IServiceHandler;
import com.dubture.symfony.index.log.Logger;
import com.dubture.symfony.index.model.Service;


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
public class ServiceDao extends BaseDao implements IServiceDao {

	private static final String TABLENAME = "SERVICES";


	private static final String Q_INSERT_DECL = Schema
			.readSqlFile("Resources/index/insert_decl.sql"); //$NON-NLS-1$

	public ServiceDao(Connection connection) {
		super(connection);
	}
	
	public void insert(String name, String phpclass, String _public, List<String> tags, String path, int timestamp)
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
			insertBatch(statement, path, name, phpclass, _public, tags, timestamp);
		}
	}

	private void insertBatch(PreparedStatement statement, String path, String name, String phpclass, String _public, List<String> tags, int timestamp)
			throws SQLException {

		int param = 0;
		String tagString = "";		
		
		if (tags != null) {
			for (int i=0; i < tags.size(); i++) {
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
		
		//Logger.debugMSG(statement.toString());
		
		//
		//		if (!isReference) {
		//			H2Cache.addElement(new Element(type, flags, offset, length,
		//					nameOffset, nameLength, name, camelCaseName, metadata, doc,
		//					qualifier, parent, fileId, isReference));
		//		}
	}

	@Override
	public void truncate() {
		try {
			Statement statement = connection.createStatement();
			statement.execute("TRUNCATE TABLE SERVICES");
			connection.commit();
		} catch (SQLException e) {
			Logger.logException(e);
		}
	}	

	public void findAll(IServiceHandler handler) {
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT PATH, NAME, PHPCLASS, _PUBLIC, TAGS  FROM SERVICES";
			ResultSet result = statement.executeQuery(query.toString());
			
			while (result.next()) {
				int columnIndex = 0;
				String path = result.getString(++columnIndex);
				String name = result.getString(++columnIndex);
				String phpClass = result.getString(++columnIndex);
				String _public = result.getString(++columnIndex);
				String tags = result.getString(++columnIndex);								
				handler.handle(name, path, phpClass, _public, tags);
			}
		} catch(Exception e) {
			Logger.logException(e);
		}
	}

	@Override
	public Service find(String name) {
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
	public void deleteServices(String path) {
		try {
			Statement statement = connection.createStatement();
			statement.execute("DELETE FROM SERVICES WHERE PATH = '" + path + "'");
			connection.commit();
		} catch (SQLException e) {
			Logger.logException(e);
		}
	}

	@Override
	public void findServicesByPath(String path, IServiceHandler handler) {
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT NAME, PHPCLASS, PATH, _PUBLIC, TAGS FROM SERVICES WHERE PATH LIKE '" + path + "%'";
			ResultSet result = statement.executeQuery(query.toString());
			
			while (result.next()) {
				int columnIndex = 0;
				String id= result.getString(++columnIndex);
				String phpClass = result.getString(++columnIndex);
				String _path = result.getString(++columnIndex);
				String _public = result.getString(++columnIndex);
				String tags = result.getString(++columnIndex);
				handler.handle(id, phpClass, _path, _public, tags);
			}
		} catch(Exception e) {
			Logger.logException(e);
		}
	}

	@Override
	public void findService(String id, String path, IServiceHandler handler) {
		try {
			Statement statement = connection.createStatement();
			StringBuilder builder = new StringBuilder("SELECT NAME, PHPCLASS, PATH, _PUBLIC, TAGS FROM SERVICES WHERE PATH LIKE '");
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
			String _public = result.getString(4);
			String _tags = result.getString(5);
			handler.handle(_id, _phpClass, _path, _public, _tags);			
		} catch(Exception e) {
			Logger.logException(e);
		}
	}

	@Override
	public List<String> findTags(IPath path)
	{
		List<String> tags = new ArrayList<String>();
		try {
			Statement statement = connection.createStatement();
			String query = "SELECT TAGS FROM SERVICES WHERE PATH LIKE '" + path + "%'";
			ResultSet result = statement.executeQuery(query.toString());
			
			while (result.next()) {
				int columnIndex = 0;
				String _tags = result.getString(++columnIndex);
				String[] t = _tags.split(",");
				for (String tag : t) {					
					if (!tags.contains(tag))
						tags.add(tag);
				}
			}
		} catch(Exception e) {
			Logger.logException(e);
		}

		return tags;
	}


    @Override
    public void delete(String id, String path)
    {
        try {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM SERVICES WHERE NAME = '" + id +"' AND PATH = '" + path + "'");
            connection.commit();
        } catch (SQLException e) {
            Logger.logException(e);
        }
    }
}
