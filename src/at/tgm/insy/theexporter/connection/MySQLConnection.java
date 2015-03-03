package at.tgm.insy.theexporter.connection;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;

/**
 * MySQL Connection Frame
 * @author Daniel Melichar
 * @version 30.12.2014
 */
public class MySQLConnection extends Connection {

    /**
     * Implementation for MySQL
     *
     * @param host     Server host (IP or Domain)
     * @param user     Designated user with given rights
     * @param password Password to the user's account
     * @return DataSource Object
     */
    @Override
    public DataSource createConnection(String host, String user, String password) {
        MysqlDataSource ds = new MysqlDataSource();

        // Set host, user and password
        ds.setServerName(host);
        ds.setUser(user);
        ds.setPassword(password);

        return ds;
    }
}
