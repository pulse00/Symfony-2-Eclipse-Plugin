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

import com.dubture.symfony.index.handler.ITranslationHandler;

public interface ITransUnitDao extends IDao {

	void insert(Connection connection, String path, String name, String value, String language, int timestamp) throws Exception;
	
	void findTranslations(String path, ITranslationHandler iTranslationHandler);
	
	void findTranslations(String name, String path, ITranslationHandler handler);
	
	void deleteRoutesByPath(String name, String language, String path);
	
}
