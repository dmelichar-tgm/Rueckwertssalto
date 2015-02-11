package at.tgm.insy.backflip.metadata;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Melichar
 * @version 10.02.2015
 */
public class DBConnection extends DBStaticFields {
    
    /* ATTRIBUTES */
    
    private Connection connection;
    private DatabaseMetaData metaData;
    
        
    /* CONSTRUCTORS */
    
    public DBConnection() {
        connection = null;
        metaData = null;
    }
    
    public DBConnection(Connection connection, DatabaseMetaData metaData) {
        this.connection = connection;
        this.metaData = metaData;
    }

    
    /* METHODS */
    
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
        connection = null;
        metaData = null;
    }
    
    /* GETTER & SETTER - custom */
    public PreparedStatement getPreparedSql(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
    
    public String[] getCatalogs() throws SQLException {
        List<String> catalog = new ArrayList<String>();
        ResultSet res = metaData.getCatalogs();
        while (res.next()) {
            String name = res.getString("SCHEM_NAME");
            if (!"".equals(name) && !catalog.contains(name)) {
                catalog.add(name);
            }
        }
        res.close();

        return catalog.toArray(new String[]{});
    }

    public String[] getSchemata() throws SQLException {
        List<String> schemata = new ArrayList<String>();
        ResultSet res = metaData.getSchemas();
        while (res.next()) {
            String name = res.getString("SCHEM_NAME");
            if (!"".equals(name) && !schemata.contains(name)) {
                schemata.add(name);
            }
        }
        res.close();

        return schemata.toArray(new String[]{});
    }

    public List<String> getTables(String catalog, String schema) throws SQLException {
        List<String> tables = new ArrayList<String>();
        String[] names = {"TABLE", "SYSTEM TABLE"};         // ToDo needs testing
        ResultSet res = metaData.getTables(catalog, schema, "%", names);
        while (res.next()) {
            tables.add(res.getString("TABLE_NAME"));
        }
        res.close();

        return tables;
    }

    public ResultSet getAttributes(String catalog, String schema,String table) throws SQLException {
        return metaData.getColumns(catalog, schema, table, "%");
    }

    
    /* GETTER & SETTER - defined */
    
    public DatabaseMetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(DatabaseMetaData metaData) {
        this.metaData = metaData;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
