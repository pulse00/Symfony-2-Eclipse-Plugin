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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.dubture.symfony.index.dao.IDao;
import com.dubture.symfony.index.log.Logger;

abstract public class BaseDao implements IDao {
	
	/** Cache for insert element reference queries */
	protected static final Map<String, String> D_INSERT_QUERY_CACHE = new HashMap<String, String>();
	protected final Map<String, PreparedStatement> batchStatements;
	protected final String LIKE_WILDCARD = "%"; //$NON-NLS-1$
	
	public BaseDao() {
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
	
	protected String escapeLikePattern(String pattern) {
		return pattern.replaceAll("[\\\\%_]", "\\\\$0"); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	protected void closeIfExists(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				Logger.logException(e);
			}
		}
	}
}
