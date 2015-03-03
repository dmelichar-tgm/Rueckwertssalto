package at.tgm.insy.theexporter.connection;

import javax.sql.DataSource;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

/**
 * This abstract class is giving a basis for building a class responsible
 * for the connection to a Database.
 * The DataSource Method can simply be overwritten to serve the needs
 * according to the Database one has, e.g. MySQL or PostgreSQL.
 * Since this Application is based on the Factory Design Pattern, you can
 * simply use the already given Methods with ease to do whatever you desire
 * to do with your Database.
 *
 * @author Daniel Melichar
 * @version 30.12.2014
 */
public abstract class Connection {

    /* ATTRIBUTES */
    private java.sql.Connection connection;      // Connection Object for the Database
    private ResultSetMetaData metaData;

    /* METHODS */

    /**
     * Checks if the syntax of the request sent 
     * * to the Database is valid.
     *
     * @param query The query that shall be checked
     * @return Is the query valid
     */
    public static boolean checkQuery(String query) {
        /*
            TODO
            Idea: stackoverflow.com/questions/10247106/
         */
        return true;
    }

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
    public abstract DataSource createConnection(String host, String user, String password);

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
            DataSource ds = createConnection(host, user, password);
            this.connection = ds.getConnection();

            // Specify which Database shall be used
            PreparedStatement st = connection.prepareStatement("USE " + database + ";");
            st.executeQuery();
        } catch (SQLException e) {
            System.out.println("A SQL-Error has been found:" +e.getMessage());
            System.out.println("Please check if the user and the password are correct and if the host can be reached.");
        }
    }

    /**
     * With this method, you can send your command to the Database after
     * a connection has been established. You will receive the output in
     * an nice to manage and easy to use table and are then able to do
     * whatever you want with the results of the query.
     * Please note that the guidelines of checkQuery() must be followed.
     * @param query The query that shall be executed
     * @return A table with the results of the query
     * @throws java.sql.SQLException
     */
    public DefaultTableModel sendQuery(String query) throws SQLException {
        if (checkQuery(query)) {   // Check the query before doing anything
            // Establish a statement and save the results of the executed query
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            /*
            Save the meta data for later usage.
            For instance, you can see what tables are defined in the database, and what columns each table has,
            whether given features are supported etc.
            */
            this.metaData = rs.getMetaData();
            DefaultTableModel model = new DefaultTableModel();
            int columnCount = metaData.getColumnCount();

            /*
            Saves the column names of the selected database into the
            TableModel as table-header.
             */
            String[] columnNames = new String[columnCount];
            for (int column = 1; column <= columnCount; column++) {
                columnNames[column - 1] = metaData.getColumnName(column);
            }
            model.setColumnIdentifiers(columnNames);

            /*
            Saves each value of each row into the DefaultTableModel
            (basically transfers everything from one table to another)
             */
            while (rs.next()) {
                String[] temp = new String[columnCount];
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    temp[columnIndex - 1] = rs.getString(columnIndex);
                }
                model.addRow(temp);
            }

            // Close the connections
            rs.close();
            st.close();

            return model;
        } else {
            // If the query is incorrect, an exception will be thrown.
            throw new SQLException("Incorrect Syntax! \n");
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
  public ResultSetMetaData getMetaData() {
        return this.metaData;
    }
}