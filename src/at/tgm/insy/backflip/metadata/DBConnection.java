package at.tgm.insy.backflip.metadata;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Sets the basic functionality for the database reader.
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
        setConnection(connection);
        setMetaData(metaData);
    }

    
    /* METHODS */

    /**
     * Close the connection 
     * @throws SQLException From connection
     */
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
        connection = null;
        metaData = null;
    }
    
    /* GETTER & SETTER - custom */

    /**
     * Returns a prepared statement 
     * @param sql Query for database
     * @return prepared statement
     * @throws SQLException Connection errors
     */
    public PreparedStatement getPreparedSql(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    /**
     * Receives the catalogs from the database 
     * @return String array with all catalogs
     * @throws SQLException Connection errors
     */
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

    /**
     * Receives the schemata from the database 
     * @return String array with all schemata
     * @throws SQLException Connection errors
     */
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

    /**
     * Recieves a sorted list with all Tables in the database.
     * Requires either a catalog or a schema 
     * @param catalog Database catalog
     * @param schema Database schema
     * @return Sorted list with all table
     * @throws SQLException Connection errors
     */
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

    /**
     * Receives all Attributes from a table.
     * Requires a catalog or schema and the table which shall be analyzed. 
     * @param catalog Database catalog
     * @param schema Database schema
     * @param table Database table
     * @return ResultSet with all attributes
     * @throws SQLException Connection errors
     */
    public ResultSet getAttributes(String catalog, String schema,String table) throws SQLException {
        return metaData.getColumns(catalog, schema, table, "%");
    }

    
    /* GETTER & SETTER - defined */


    public DatabaseMetaData getMetaData() {
        return metaData;
    }

    /**
     * Set the metadata from a connection
     * @param metaData DatabaseMetaData object
     */
    public void setMetaData(DatabaseMetaData metaData) {
        try {
            this.metaData = metaData;
        } catch(NullPointerException e) {
            System.out.println("Could not read information from connection: " + e.getMessage());
        }
    }
    Connection getConnection() {
        return connection;
    }

    /**
     * Set the connection
     * @param connection Connection object
     */
    public void setConnection(Connection connection) {
        try {
            this.connection = connection;
        } catch(NullPointerException e) {
            System.out.println("Could not read information from connection: " + e.getMessage());
        }
    }

}
