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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.core.runtime.IPath;
import org.eclipse.osgi.util.NLS;

import com.dubture.symfony.index.Schema;
import com.dubture.symfony.index.dao.IResourceDao;
import com.dubture.symfony.index.handler.IResourceHandler;
import com.dubture.symfony.index.log.Logger;
import com.dubture.symfony.index.model.RoutingResource;

public class ResourceDao extends BaseDao implements IResourceDao {

    public ResourceDao(Connection connection) {
		super(connection);
	}

	private static final String TABLENAME = "RESOURCE";
    private static final String Q_INSERT_DECL = Schema
            .readSqlFile("Resources/index/resources/insert_decl.sql"); //$NON-NLS-1$


    @Override
    public void insert(String path, String type, String prefix, IPath fullPath) throws Exception {

        if (type == null)
            type = "";

        if (prefix == null)
            prefix = "";

        String tableName = TABLENAME;
        String query;

        query = D_INSERT_QUERY_CACHE.get(tableName);
        if (query == null) {
            query = NLS.bind(Q_INSERT_DECL, tableName);
            D_INSERT_QUERY_CACHE.put(tableName, query);
        }

        synchronized (batchStatements) {
            PreparedStatement statement = batchStatements.get(query);
            if (statement == null) {
                statement = connection.prepareStatement(query);
                batchStatements.put(query, statement);
            }
            insertBatch(statement, path, type, prefix, fullPath);
        }
    }

    private void insertBatch(PreparedStatement statement,
            String resourcePath, String type, String prefix, IPath path)
                    throws SQLException {

        int param = 0;

        statement.setString(++param, resourcePath);
        statement.setString(++param, type);
        statement.setString(++param, prefix);
        statement.setString(++param, path.toString());
        statement.addBatch();

        //
        //        if (!isReference) {
        //            H2Cache.addElement(new Element(type, flags, offset, length,
        //                    nameOffset, nameLength, name, camelCaseName, metadata, doc,
        //                    qualifier, parent, fileId, isReference));
        //        }
    }

    @Override
    public void findResource(IPath path, IResourceHandler iResourceHandler) {
        try {
            String sql = "SELECT RESOURCEPATH, TYPE, PREFIX FROM RESOURCE WHERE PATH = '" + path.toString() + "'";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                int columnIndex = 0;
                String resourcePath = result.getString(++columnIndex);
                String type = result.getString(++columnIndex);
                String prefix = result.getString(++columnIndex);
                RoutingResource resource = new RoutingResource(type, resourcePath, prefix);
                iResourceHandler.handle(resource);
            }
        } catch(Exception e) {
            Logger.logException(e);
        }
    }
}