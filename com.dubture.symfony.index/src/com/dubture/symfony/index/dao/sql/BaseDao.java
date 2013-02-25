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
import java.util.HashMap;
import java.util.Map;

import com.dubture.symfony.index.dao.IDao;

abstract public class BaseDao implements IDao {
	
	/** Cache for insert element reference queries */
	protected static final Map<String, String> D_INSERT_QUERY_CACHE = new HashMap<String, String>();
	protected final Map<String, PreparedStatement> batchStatements;
	protected Connection connection;
	
	public BaseDao(Connection connection) {
		this.connection = connection;
		this.batchStatements = new HashMap<String, PreparedStatement>();
	}
	
	public void commitInsertions() throws Exception {
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
}
