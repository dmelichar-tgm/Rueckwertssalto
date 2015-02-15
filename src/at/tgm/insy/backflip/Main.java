package at.tgm.insy.backflip;


import at.tgm.insy.backflip.cli.CommandLineController;
import at.tgm.insy.backflip.config.ConfigUtil;
import at.tgm.insy.backflip.output.OutputController;

/**
 * @author Daniel Melichar
 * @version 11.02.2015
 */
public class Main {

    public static void main(String[] args) {
        if (!ConfigUtil.hasBeenCreated) {
            ConfigUtil.store();
        }
            new OutputController().init(new CommandLineController(args));
    }
}
