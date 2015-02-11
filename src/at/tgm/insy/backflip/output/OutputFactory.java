package at.tgm.insy.backflip.output;

import at.tgm.insy.backflip.connection.AbstractConnection;

/**
 * @author Daniel Melichar
 * @version 11.02.2015
 */
public class OutputFactory {

    public void createConnection(String type, AbstractConnection connection) {

        // Other database types can be added in this statement
        if (type.equalsIgnoreCase("eer")) {
            //output = new EEROutput()
        } else if (type.equalsIgnoreCase("rm")) {
            new RMOutput(connection);
        } else {
            System.out.println("Unkown output type");
        }
    }

}
