package org.eclipse.symfony.index.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.eclipse.core.runtime.IPath;


/**
 * 
 * RouteDao Interface.
 * 
 * 
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 *
 */
public interface IRouteDao {
	

	void insert(Connection connection, String name, String pattern, 
		String controller, String bundle, String action, IPath path)
			throws SQLException;

	void commitInsertions() throws SQLException;

	void deleteRoutesByPath(Connection connection, String name, IPath path);

	List<Route> findRoutes(Connection connection, IPath path);

}
