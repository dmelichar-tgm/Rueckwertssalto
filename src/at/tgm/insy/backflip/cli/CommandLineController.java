package at.tgm.insy.backflip.cli;

import at.tgm.insy.backflip.config.ConfigUtil;
import at.tgm.insy.backflip.model.ConnectionInfo;
import at.tgm.insy.backflip.model.DatabaseTypes;
import org.apache.commons.cli2.*;
import org.apache.commons.cli2.builder.ArgumentBuilder;
import org.apache.commons.cli2.builder.DefaultOptionBuilder;
import org.apache.commons.cli2.builder.GroupBuilder;
import org.apache.commons.cli2.commandline.Parser;
import org.apache.commons.cli2.util.HelpFormatter;


/**
 * Controls the CLI-Input of the User using Commons-CLI2
 * @author Daniel Melichar
 * @version 30.12.2014
 */
public class CommandLineController {

    /* ATTRIBUTES */
    private final DefaultOptionBuilder optionBuilder;
    private final ArgumentBuilder argumentBuilder;

    private String host = "";
    private String user = "";
    private String password = "";
    private String database = "";
    
    /* PROPERTIES FILE */
    private String graphvizNeatoPath = "";
    private String outputDirectory = "";
    private String outputType = "";
    private DatabaseTypes databaseType = null;
    
    private ConnectionInfo connectionInfo;

    /* CONSTRUCTOR */

    public CommandLineController(String[] args) {
        optionBuilder = new DefaultOptionBuilder();
        argumentBuilder = new ArgumentBuilder();
        GroupBuilder groupBuilder = new GroupBuilder();
        connectionInfo = new ConnectionInfo();

        Group options = groupBuilder
                .withName("options")
                .withOption(this.buildHelp())
                .withOption(this.buildDatabase())
                .withOption(this.buildHost())
                .withOption(this.buildUser())
                .withOption(this.buildPassword())
                .withOption(this.buildPasswordPrompt())
                .create();

        // HelpFormatter
        HelpFormatter hf = new HelpFormatter();
        hf.setShellCommand("Backflip.jar");
        hf.setGroup(options);

        // HelpFormatter Settings
        hf.getFullUsageSettings().remove(DisplaySetting.DISPLAY_GROUP_EXPANDED);
        hf.getDisplaySettings().remove(DisplaySetting.DISPLAY_GROUP_ARGUMENT);


        // Parsing the Arguments and setting the attribute values
        Parser parser = new Parser();
        parser.setGroup(options);

        try {
            CommandLine cl = parser.parse(args);
            this.setValues(cl, hf);
        } catch (OptionException e) {
            System.out.println("Error while parsing arguments: " + e.getMessage());
            System.out.println("To display the help page use: -help");
            System.exit(1);
        }
    }

    /* METHODS */

    /**
     * Help Option (for display)
     * Short:   -help
     *
     * @return Option for help dispaly
     */
    private Option buildHelp() {
        return optionBuilder
                .withShortName("help")
                .withDescription("Display this help page\n")
                .create();
    }

    /**
     * Host Argument
     * Short:   -h
     * Long:    host
     * Standard: localhost
     *
     * @return Option for the host
     */
    private Option buildHost() {
        return optionBuilder
                .withLongName("host")
                .withShortName("h")
                .withDescription("Used to set the Server to which a connection shall be established\n")
                .withRequired(false)
                .withArgument(
                        argumentBuilder
                                .withName("domain or ip")
                                .withMinimum(1)
                                .withMaximum(1)
                                .create()
                )
                .create();
    }

    /**
     * Username Argument
     * Short:   -u
     * Long:    user
     * Standard: System username
     *
     * @return Option for the username
     */
    private Option buildUser() {
        return optionBuilder
                .withLongName("user")
                .withShortName("u")
                .withDescription("Please specify the user with whom you want to connect to the database\n")
                .withRequired(false)
                .withArgument(
                        argumentBuilder
                                .withName("username")
                                .withMinimum(1)
                                .withMaximum(1)
                                .create()
                )
                .create();
    }

    /**
     * Password Argument
     * Short:   -p
     * Long:    password
     * Standard: No password
     *
     * @return Option for the password
     */
    private Option buildPassword() {
        return optionBuilder
                .withLongName("password")
                .withShortName("p")
                .withRequired(false)
                .withDescription("The user's password. \nWarning: Your password will be visible\nTo get a password prompt, use -P / -Password\n")
                .withArgument(
                        argumentBuilder
                                .withName("password")
                                .withMinimum(1)
                                .withMaximum(1)
                                .create()
                )
                .create();
    }

    /**
     * Password Argument with prompt
     * Short:   -P
     * Long:    -Password
     * Standard: No password
     *
     * @return Option for the password
     */
    private Option buildPasswordPrompt() {
        return optionBuilder
                .withLongName("Password")
                .withShortName("P")
                .withRequired(false)
                .withDescription("The user's password.\nNo arguments required\n")
                .create();
    }

    /**
     * Required Database Argument
     * Short:   -D
     * Long:    --Database
     *
     * @return Option for the database
     */
    private Option buildDatabase() {
        return optionBuilder
                .withLongName("Database")
                .withShortName("D")
                .withRequired(false)
                .withDescription("REQUIRED\nFrom which database a result-set shall be gotten\n")
                .withArgument(
                        argumentBuilder
                                .withName("database")
                                .withMinimum(1)
                                .withMaximum(1)
                                .create()
                )
                .create();
    }

    
    /**
     * Sets the values of the Attributes and validates if the arguments
     * meet the given criteria.  
     * @param cl The CommandLine to get the values
     * @param hf HelpFormatter in case we get errors
     */
    private void setValues(CommandLine cl, HelpFormatter hf) {
        if (cl.hasOption("-help")) {
            hf.print(); // Print out help
            System.exit(1);
        } else {
            // Required Arguments: Database and Table
            if (cl.hasOption(this.buildDatabase()) ) {
                String database = cl.getValue(buildDatabase()).toString();
               
                this.database = database;
                connectionInfo.setDatabase(database);
                
                // Default (if nothing is set): localhost
                if (cl.hasOption(this.buildHost())) {
                    String host = cl.getValue(buildHost()).toString();
                    this.host = host;
                    connectionInfo.setHost(host);
                } else {
                    this.host = "localhost";
                    connectionInfo.setHost(host);
                }

                // Default: System's user name
                if (cl.hasOption(this.buildUser())) {
                    String user = cl.getValue(buildUser()).toString();
                    this.user = user;
                    connectionInfo.setUser(user);
                } else {
                    this.user = System.getProperty("user.name");
                    connectionInfo.setUser(System.getProperty("user.name"));
                }

                // Default password: none
                if (cl.hasOption(this.buildPassword())) {
                    String password = cl.getValue(buildPassword()).toString();
                    this.password = password;
                    connectionInfo.setPassword(password);
                } else {
                    this.password = "";
                    connectionInfo.setPassword("");
                }
                
                // Password prompt in console
                if (cl.hasOption(this.buildPasswordPrompt())) {
                    String password = new String(System.console().readPassword("Password: "));
                    this.password = password;
                    connectionInfo.setPassword(password);
                }

                
                
                // From config.properties file
                ConfigUtil.load();
                
                // Database type
                String databaseType = ConfigUtil.databaseType;
                if (databaseType.length() > 0) {
                    if (databaseType.equalsIgnoreCase("mysql")) {
                        this.databaseType = DatabaseTypes.MYSQL;
                        connectionInfo.setDatabaseType(DatabaseTypes.MYSQL);
                    } else if (databaseType.equalsIgnoreCase("oracle")) {
                        this.databaseType = DatabaseTypes.ORACLE;
                        connectionInfo.setDatabaseType(DatabaseTypes.ORACLE);
                    } else if (databaseType.equalsIgnoreCase("postgres")) {
                        this.databaseType = DatabaseTypes.POSTGRES;
                        connectionInfo.setDatabaseType(DatabaseTypes.POSTGRES);
                    }
                } else {
                    this.databaseType = DatabaseTypes.MYSQL;
                    connectionInfo.setDatabaseType(DatabaseTypes.MYSQL);
                }
                
                
                // output directory
                String outputDirectory = ConfigUtil.outputDirectory;
                if (outputDirectory.length() > 0) {
                    this.outputDirectory = outputDirectory;
                } else {
                    this.outputDirectory = System.getProperty("user.dir");
                }
                
                // output type
                String outputType = ConfigUtil.outputType;
                if (outputType.length() > 0) {
                    this.outputType = outputType;
                } else {
                    this.outputType = "rm";
                }

                // graphvizNeatoPath
                String graphvizNeatoPath = ConfigUtil.graphvizNeatoPath;
                if (graphvizNeatoPath.length() > 0) {
                    this.graphvizNeatoPath = graphvizNeatoPath;
                } else {
                    if (outputType.equalsIgnoreCase("rm")) {
                        this.graphvizNeatoPath = "";
                    } else if (outputType.equalsIgnoreCase("eer")) {
                        throw new IllegalArgumentException("Please add the path to your neato file to the properties.");
                    }
                }
                
            } else {
                throw new IllegalArgumentException("Required Argument (database) not parsed in console.");
            }
        }
    }

    /* GETTER & SETTER */

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabase() {
        return database;
    }

    public DatabaseTypes getDatabaseType() {
        return databaseType;
    }

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    public String getGraphvizNeatoPath() {
        return graphvizNeatoPath;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public String getOutputType() {
        return outputType;
    }
}
 