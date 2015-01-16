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

import com.dubture.symfony.index.handler.IParameterHandler;
import com.dubture.symfony.index.model.Parameter;

public interface IParameterDao extends IDao {

	void insert(Connection connection, String key, String value, IPath path) throws Exception;
	
	void delete(String id, String path);

	void findAll(IParameterHandler handler);
	
	List<Parameter> findParameters(IPath path) throws Exception;

	void deleteParameters(String path);

}
