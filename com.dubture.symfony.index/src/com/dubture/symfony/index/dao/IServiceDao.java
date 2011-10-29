package com.dubture.symfony.index.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.dubture.symfony.index.IServiceHandler;


/**
 * 
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
public interface IServiceDao extends IDao {
	
	void insert(Connection connection, String path, String name, String phpclass, int timestamp) throws SQLException;

	void truncate(Connection connection);
	
	void findAll(Connection connection, IServiceHandler handler);

	Service find(Connection connection, String string);

	void deleteServices(Connection connection, String path);

	void findServicesByPath(Connection connection, String path, IServiceHandler handler);

	void findService(Connection connection, String id, String path,
			IServiceHandler iServiceHandler);


}
