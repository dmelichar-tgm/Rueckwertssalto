package at.tgm.insy.theexporter;

import at.tgm.insy.theexporter.cli.CommandLineController;
import at.tgm.insy.theexporter.output.OutputController;

/**
 * Main class of the application
 */
public class Main {

    /**
     * Main Method of the Application.
     * Calls upon the Output Controller with a CommandLineController*
     * @param args Arguments given in the commandline
     */
    public static void main(String[] args) {
        new OutputController(new CommandLineController(args));
    }
}
