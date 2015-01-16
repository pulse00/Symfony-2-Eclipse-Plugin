/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.index.dao;

import java.sql.Connection;
import java.util.List;

import org.eclipse.core.runtime.IPath;

import com.dubture.symfony.index.model.Route;

/**
 * RouteDao Interface.
 * 
 * @author Robert Gruendler <r.gruendler@gmail.com>
 */
public interface IRouteDao extends IDao {
	
	void insert(Connection connection, String name, String pattern, String controller, String bundle, String action, IPath path) throws Exception;

	void deleteRoutesByPath(String name, IPath path);

	List<Route> findRoutes(IPath path);
	
	List<Route> findRoutes(String prefix, IPath path);
	
	List<Route> findRoutesByBundle(String bundle, IPath path);

	Route findRoute(String route, IPath path);
	
	List<Route> findRoutesByController(String bundleAlias, String controller, IPath path);

	void insertResource(String path, String type, String prefix, IPath fullPath);
}
