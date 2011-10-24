package com.dubture.symfony.index.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.dubture.symfony.index.ITranslationHandler;

public interface ITransUnitDao {

	void insert(Connection connection, String path, String name, String value, 
			String language, int timestamp) throws SQLException;
	

	void commitInsertions() throws SQLException;


	void findTranslations(Connection connection, String path, ITranslationHandler iTranslationHandler);
	
	void deleteRoutesByPath(Connection connection, String name, String language, String path);
	
}
