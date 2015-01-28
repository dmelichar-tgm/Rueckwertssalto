package at.tgm.insy.backflip.connection;

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
     * @param host     Server host (IP or Domain)
     * @param user     Designated user with given rights
     * @param password Password to the user's account
     * @return DataSource Object
     */
    @Override
    public DataSource createConnection(String host, String user, String password, String database) {
        MysqlDataSource ds = new MysqlDataSource();

        // Set host, user and password
        ds.setServerName(host);
        ds.setUser(user);
        ds.setPassword(password);
        ds.setDatabaseName(database);

        return ds;
    }
}
