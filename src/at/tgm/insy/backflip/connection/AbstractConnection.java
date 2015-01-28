package at.tgm.insy.backflip.connection;

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
     * @param host     Server host (IP or Domain)
     * @param user     Designated user with given rights
     * @param password Password to the user's account
     * @return DataSource Object
     */
    public abstract DataSource createConnection(String host, String user, String password, String database);

    /**
     * Creates a connection to a Database with the given information.
     *
     * @param host     Server host
     * @param user     Designated user with given rights
     * @param password Password to the user's account
     * @param database The Database you want to connect to
     */
    public void start(String host, String user, String password, String database) {
        try {
            // Connect to Database and store it in the Attribute for later use.
            DataSource ds = createConnection(host, user, password, database);
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
}
