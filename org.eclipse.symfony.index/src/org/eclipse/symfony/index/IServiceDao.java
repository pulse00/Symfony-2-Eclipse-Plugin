package org.eclipse.symfony.index;

import java.sql.Connection;
import java.sql.SQLException;

public interface IServiceDao {
	
	void insert(Connection connection, String path, String name, String phpclass, int timestamp) throws SQLException;

	void truncate(Connection connection);
	
	void findAll(Connection connection, IServiceHandler handler);
	
	void commitInsertions() throws SQLException;

	Service find(Connection connection, String string);

}
