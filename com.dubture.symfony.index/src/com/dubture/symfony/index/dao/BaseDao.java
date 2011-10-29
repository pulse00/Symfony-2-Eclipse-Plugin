package com.dubture.symfony.index.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

abstract public class BaseDao implements IDao {
	
	/** Cache for insert element reference queries */
	protected static final Map<String, String> D_INSERT_QUERY_CACHE = new HashMap<String, String>();
	protected final Map<String, PreparedStatement> batchStatements;
	
	public BaseDao() {
		
		this.batchStatements = new HashMap<String, PreparedStatement>();
		
	}
	
	
	public void commitInsertions() throws SQLException {


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
