package at.tgm.insy.backflip.output;

/**
 * @author Daniel Melichar
 * @version 11.02.2015
 */
public class OutputFactory {

    public AbstractOutput createConnection(String type) {
        AbstractOutput output = null;

        // Other database types can be added in this statement
        if (type.equalsIgnoreCase("eer")) {
            output = new EEROutput();
        } else if (type.equalsIgnoreCase("rm")) {
            output = new RMOutput();
        } else {
            System.out.println("Unkown output type");
        }

        return output;
    }

}
