package at.tgm.insy.backflip.connection;


import at.tgm.insy.backflip.model.DatabaseTypes;

/**
 * @author Daniel Melichar
 * @version 26.01.2015
 */
public class ConnectionFactory {

    /**
     * Sets the database type and gives back a Connection-Object
     * with which additional functionality is given.
     *
     * @param types The database type (e.g. MySQL)
     * @return A Connection-Object with additional functionality
     */
    public AbstractConnection createConnection(DatabaseTypes types) {
        AbstractConnection con = null;

        // Other database types can be added in this statement
        if (types.getType().equalsIgnoreCase("mysql")) {
            con = new MySQLConnection();
        } else {
            System.out.println("Unsupported Database");
        }

        return con;
    }
    
}
