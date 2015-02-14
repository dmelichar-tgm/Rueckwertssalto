package at.tgm.insy.backflip.output;

import at.tgm.insy.backflip.cli.CommandLineController;
import at.tgm.insy.backflip.connection.AbstractConnection;
import at.tgm.insy.backflip.connection.ConnectionFactory;

/**
 * @author Daniel Melichar
 * @version 11.02.2015
 */
public class OutputController {
    
    private CommandLineController commandLineController;
    private String outputDirectory;
    private String graphvizBinPath;

    /**
     * Initiation method of the controller.
     * Gets the necessary information from the commandline and 
     * properties file and calls upon the right class to create the output
     * @param commandLineController
     */
    public void init(CommandLineController commandLineController) {
        this.commandLineController = commandLineController;
        this.outputDirectory = this.commandLineController.getOutputDirectory();
        this.graphvizBinPath = this.commandLineController.getGraphvizBinPath();
        AbstractConnection connection = createConnection();

        if (commandLineController.getOutputType().equalsIgnoreCase("rm")) {
            outputDirectory += "\\rm_" + commandLineController.getDatabase() + ".txt";
            RMOutput rmOutput = new RMOutput(connection);
            rmOutput.setController(this);
            rmOutput.createOutput();
        } else if (commandLineController.getOutputType().equalsIgnoreCase("eer")) {
            outputDirectory += "\\eer_" + commandLineController.getDatabase();
            EEROutput eerOutput = new EEROutput(connection);
            eerOutput.setController(this);
            eerOutput.createOutput();
        } else {
            // ToDo Error
        }
        
        connection.close();
    }

    /**
     * Creates and establishes a connection 
     * @return
     */
    AbstractConnection createConnection() {
        AbstractConnection connection;
        ConnectionFactory factory = new ConnectionFactory();
        connection = factory.createConnection(this.commandLineController.getDatabaseType());
        connection.start(this.commandLineController.getConnectionInfo());
        return connection;
    }
    
    /* GETTER & SETTER*/
    
    public String getOutputDirectory() {return this.outputDirectory;}
    
    public String getGraphvizBinPath() {return this.graphvizBinPath;}
    
}