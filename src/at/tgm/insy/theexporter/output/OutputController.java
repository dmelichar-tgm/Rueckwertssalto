package at.tgm.insy.theexporter.output;


import at.tgm.insy.theexporter.cli.CommandLineController;
import at.tgm.insy.theexporter.connection.Connection;
import at.tgm.insy.theexporter.connection.ConnectionFactory;

/**
 * Responsible for preparing everything needed to create
 * an output/display.  
 * @author Daniel Melichar
 * @version 30.12.2014
 */
public class OutputController {

    /*ATTRIBUTES*/
    private CommandLineController commandLineController;

    /*CONSTRUCTORS*/

    /**
     * In this constructor, we check if the output shall be created in
     * a file or displayed in the console. This is done by checking the
     * arguments the user has given us. 
     * @param commandLineController Needed to check the arguments
     */
    public OutputController(CommandLineController commandLineController) {
        this.commandLineController = commandLineController;

        // If the File-Argument has been set, display in File
        if (!(this.commandLineController.getOutputFile().isEmpty())) {
            new FileOutput(commandLineController, this);
        } else {
            new ConsoleOutput(this);
        }
    }

    /*METHODS*/
    
    /**
     * Uses all given parameters to create a SQL-Query 
     * Momentarily, only SELECTS can be build
     * @return The query
     */
    public String createQuery() {
        String query;

        // All possbile user inputs
        String expr = this.commandLineController.getExpression();
        String table = this.commandLineController.getTable();
        String filter = this.commandLineController.getFilter();
        String orderBy = this.commandLineController.getOrderBy();
        String orderSort = this.commandLineController.getOrderSort();
        String separator = this.commandLineController.getSeparator();

        // Building of the query
        query = "SELECT " + expr +
                " FROM " + table;

        if (!filter.isEmpty())
            query += " WHERE " + filter;

        if (!orderBy.isEmpty()) {
            if (!orderSort.isEmpty())
                query += " ORDER BY " + orderBy + " DESC";
        }

        query += separator;
        return query;
    }

    /**
     * Creates a Connection-Object which is connected to 
     * the given database. 
     * @return Connection-Object
     */
    public Connection createConnection() {
        Connection connection;

        ConnectionFactory factory = new ConnectionFactory();
        connection = factory.createConnection(this.commandLineController.getDatabaseType());
        connection.start(this.commandLineController.getHost(), this.commandLineController.getUser(),
                this.commandLineController.getPassword(), this.commandLineController.getDatabase()
        );

        return connection;
    }
}
