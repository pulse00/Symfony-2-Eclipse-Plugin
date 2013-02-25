/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 * 
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 * 
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.index.dao;

import java.util.List;

import org.eclipse.core.runtime.IPath;

import com.dubture.symfony.index.handler.IServiceHandler;
import com.dubture.symfony.index.model.Service;


/**
 * 
 * 
 * 
 * @author "Robert Gruendler <r.gruendler@gmail.com>"
 *
 */
public interface IServiceDao extends IDao {
	
	void insert(String id, String phpClass, String _public, List<String> tags, String path, int timestamp) throws Exception;
	
	void delete(String id, String path);

	void findAll(IServiceHandler handler);

	Service find(String string);

	void deleteServices(String path);

	void findServicesByPath(String path, IServiceHandler handler);

	void findService(String id, String path,
			IServiceHandler iServiceHandler);

	List<String> findTags(IPath path);

	void truncate();

}
