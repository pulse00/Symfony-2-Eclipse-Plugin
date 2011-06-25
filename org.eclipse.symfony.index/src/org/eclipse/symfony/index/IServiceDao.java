package org.eclipse.symfony.index;

import java.sql.Connection;
import java.sql.SQLException;

public interface IServiceDao {
	
	void insert(Connection connection, String path, String name, String phpclass, int timestamp) throws SQLException;

}
