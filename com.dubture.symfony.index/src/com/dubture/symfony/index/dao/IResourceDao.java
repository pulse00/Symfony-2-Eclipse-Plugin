package com.dubture.symfony.index.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.eclipse.core.runtime.IPath;

import com.dubture.symfony.index.IResourceHandler;

public interface IResourceDao extends IDao {

	void insert(Connection connection, String path, String type, String prefix,
			IPath fullPath) throws SQLException;

	void findResource(Connection connection, IPath path,
			IResourceHandler iResourceHandler);

	
	
}
