package at.tgm.insy.backflip.connection;

import at.tgm.insy.backflip.model.ConnectionInfo;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;

/**
 * @author Daniel Melichar
 * @version 26.01.2015
 */
public class MySQLConnection extends AbstractConnection {
    
    /**
     * Implementation for MySQL
     *
     * @param connectionInfo A Object of the ConnectionInfo class which has all info
     * @return DataSource Object
     */
    @Override
    public DataSource createConnection(ConnectionInfo connectionInfo) {
        MysqlDataSource ds = new MysqlDataSource();

        // Set host, user and password
        ds.setServerName(connectionInfo.getHost());
        ds.setUser(connectionInfo.getUser());
        ds.setPassword(connectionInfo.getPassword());
        ds.setDatabaseName(connectionInfo.getDatabase());

        return ds;
    }
}
