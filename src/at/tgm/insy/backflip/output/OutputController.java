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
    private String filepath;
    
    //public abstract void createOutput();
    
    public void init(CommandLineController commandLineController) {
        this.commandLineController = commandLineController;
        this.filepath = this.commandLineController.getOutputDirectory();

        if (commandLineController.getOutputType().equalsIgnoreCase("rm")) {
            filepath += "\\rm_" + commandLineController.getDatabase() + ".txt";
            RMOutput rmOutput = new RMOutput(createConnection());
            rmOutput.setController(this);
            rmOutput.createOutput();
        } else if (commandLineController.getOutputType().equalsIgnoreCase("eer")) {
            filepath += "\\eer_" + commandLineController.getDatabase() + ".dot";
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
    
    
    public String getFilePath() {return this.filepath;}
    
}