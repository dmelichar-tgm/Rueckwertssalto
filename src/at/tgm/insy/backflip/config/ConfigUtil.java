package at.tgm.insy.backflip.config;

import java.io.*;
import java.util.Properties;

/**
 * Used to create and read the config.properties file.
 *  
 * @author Daniel Melichar
 * @version 11.02.2015
 */
public class ConfigUtil {
    
    /* ATTRIBUTES WITH DEFAULTS */
    public static String databaseType = "mysql";
    public static String outputDirectory = System.getProperty("user.dir");
    public static String outputType = "eer";
    public static String graphvizNeatoPath = System.getProperty("user.dir") + "\\Graphviz\\bin\\neato.exe";
    public static boolean hasBeenCreated = false;

    /* METHODS */

    /**
     * Load information from the properties file 
     */
    public static void load() {
        Properties prop = new Properties();
        InputStream input = null;
        
        try {
            input = new FileInputStream(System.getProperty("user.dir") + "\\config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            databaseType = prop.getProperty("databaseType");
            outputDirectory = prop.getProperty("outputDirectory");
            outputType = prop.getProperty("outputType");
            graphvizNeatoPath = prop.getProperty("graphvizNeatoPath");

        } catch (IOException ex) {
            System.out.println("Error while loading the properties file:" + ex.getMessage());
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.out.println("Error while loading the properties file:" + e.getMessage());
                }
            }
        }
    }

    /**
     * Store the defaults into the properties file 
     */
    public static void store() {
        Properties prop = new Properties();
        OutputStream output = null;

        try {
            output = new FileOutputStream(System.getProperty("user.dir") + "\\config.properties");

            // set the properties value
            prop.setProperty("databaseType", databaseType);
            prop.setProperty("outputDirectory", outputDirectory);
            prop.setProperty("outputType", outputType);
            prop.setProperty("graphvizNeatoPath", graphvizNeatoPath);

            // save properties to project root folder
            prop.store(output, null);
        } catch (IOException io) {
            System.out.println("Error while creating the properties file:" + io.getMessage());
        } finally {
            if (output != null) {
                try {
                    output.close();
                    hasBeenCreated = true;
                } catch (IOException e) {
                    System.out.println("Error while loading the properties file:" + e.getMessage());
                }
            }

        }
    }
}
