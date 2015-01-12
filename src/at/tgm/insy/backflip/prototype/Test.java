package at.tgm.insy.backflip.prototype;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Prints all databases and all their tables to console.
 * This prototype is used to try managing the metadata from 
 * a given database-server. 
 * @author Daniel Melichar
 * @version 12.01.2015
 */
public class Test {
    
    public static void main(String[] args) throws SQLException {
        // Create DataSource for MySQL 
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("vmwaredebian");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        // Connects using root to get all available databases

        Connection connection = dataSource.getConnection();
        
        /*
        Stores all meta data for later use.
        For instance, you can see what tables are defined in the database, 
        and what columns each table has, whether given features are supported etc. 
         */
        DatabaseMetaData metaData = connection.getMetaData();

        /*
        Receives all information for every database.
        Warning: since the database-server we're connected to is a MySQL-Server,
        the getSchemas()-Method does not work.
          */
        ResultSet rsDatabases = metaData.getCatalogs();
        
        // Look through ResultSet with the database information
        while (rsDatabases.next()) {
            // Print each database catalog name 
            System.out.println("TABLE_CAT = " + rsDatabases.getString("TABLE_CAT") );   
           
            // Get all tables for the database with the catalog name currently used
            ResultSet rsTables = metaData.getTables(rsDatabases.getString("TABLE_CAT"), null, null, null);
            
            // Print each table name
            while (rsTables.next()) {
                System.out.println("\tTABLE_NAME = " + rsTables.getString("TABLE_NAME"));
            }  
        }
        
        connection.close();
    }
    
}
