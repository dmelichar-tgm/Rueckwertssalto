package at.tgm.insy.backflip.connection;

import at.tgm.insy.backflip.model.ConnectionInfo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * @author Daniel Melichar
 * @version 26.01.2015
 */
public abstract class AbstractConnection {
    
    /* ATTRIBUTES */
    private Connection connection;
    private DatabaseMetaData metaData;
    
    /* METHODS */
    /**
     * Every time one wants to connect to a specific Database, he/she will have
     * to overwrite this method to suit the Database type. It will return
     * a DataSource Object which can then be used
     *
     * @param connectionInfo A Object of the ConnectionInfo class which has all info
     * @return DataSource Object
     */
    protected abstract DataSource createConnection(ConnectionInfo connectionInfo);

    /**
     * Creates a connection to a Database with the given information.
     *
     * @param connectionInfo A Object of the ConnectionInfo class which has all info
     */
    public void start(ConnectionInfo connectionInfo) {
        try {
            // Connect to Database and store it in the Attribute for later use.
            DataSource ds = createConnection(connectionInfo);
            this.connection = ds.getConnection();
            
            metaData = connection.getMetaData();
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }

    /**
     * Close the connection to the database  and set it back to null
     */
    public void close() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /* GETTER & SETTER */
    public DatabaseMetaData getMetaData() {return metaData;}
    public Connection getConnection() {return connection;}
}
