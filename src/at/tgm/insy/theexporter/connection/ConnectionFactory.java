package at.tgm.insy.theexporter.connection;

/**
 * The Factory-Class managing all Databases.
 * To add a new Database type, create a new class which
 * implements the Connection-Class and thereby has to override
 * the createConnection() method, and add a new statement for
 * the database type.
 * If a database type hasn't been added/created yet,
 * an Exception will be thrown.
 *
 * @author Daniel Melichar
 * @version 30.12.2014
 */
public class ConnectionFactory {

    /**
     * Sets the database type and gives back a Connection-Object
     * with which additional functionality is given.
     *
     * @param type The database type (e.g. MySQL)
     * @return A Connection-Object with additional functionality
     */
    public Connection createConnection(String type) {
        Connection con = null;

        // Other database types can be added in this statement
        if (type.equalsIgnoreCase("mysql")) {
            con = new MySQLConnection();
        } else {
            System.out.println("Unsupported Database");
        }

        return con;
    }

}
