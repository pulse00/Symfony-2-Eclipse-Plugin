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
import java.sql.SQLException;
import java.util.List;

import org.eclipse.core.runtime.IPath;

import com.dubture.symfony.index.IServiceHandler;


/**
 * 
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
public interface IServiceDao extends IDao {
	
	void insert(Connection connection, String id, String phpClass, String _public, List<String> tags, String path, int timestamp) throws SQLException;
	
	void delete(Connection connection, String id, String path);

	void truncate(Connection connection);
	
	void findAll(Connection connection, IServiceHandler handler);

	Service find(Connection connection, String string);

	void deleteServices(Connection connection, String path);

	void findServicesByPath(Connection connection, String path, IServiceHandler handler);

	void findService(Connection connection, String id, String path,
			IServiceHandler iServiceHandler);

	
	List<String> findTags(Connection connection, IPath path);


}
