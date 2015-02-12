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
    
    
    //public abstract void createOutput();
    
    public void init(CommandLineController commandLineController) {
        this.commandLineController = commandLineController;
        this.outputDirectory = this.commandLineController.getOutputDirectory();
        this.graphvizBinPath = this.commandLineController.getGraphvizBinPath();

        if (commandLineController.getOutputType().equalsIgnoreCase("rm")) {
            outputDirectory += "\\rm_" + commandLineController.getDatabase() + ".txt";
            RMOutput rmOutput = new RMOutput(createConnection());
            rmOutput.setController(this);
            rmOutput.createOutput();
        } else if (commandLineController.getOutputType().equalsIgnoreCase("eer")) {
            outputDirectory += "\\eer_" + commandLineController.getDatabase();
            EEROutput eerOutput = new EEROutput(createConnection());
            eerOutput.setController(this);
            eerOutput.createOutput();
        } else {
            // ToDo Error
        }
        
    }
    
    public AbstractConnection createConnection() {
        AbstractConnection connection;
        ConnectionFactory factory = new ConnectionFactory();
        connection = factory.createConnection(this.commandLineController.getDatabaseType());
        connection.start(this.commandLineController.getConnectionInfo());
        return connection;
    }
    
    
    public String getOutputDirectory() {return this.outputDirectory;}
    
    public String getGraphvizBinPath() {return this.graphvizBinPath;}
    
}