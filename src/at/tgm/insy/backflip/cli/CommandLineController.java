package at.tgm.insy.backflip.cli;

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
    private String databaseType = "";
    private String outputFile = "";
    private String table = "";

    /* CONSTRUCTORS */
    public CommandLineController(String[] args) {
        optionBuilder = new DefaultOptionBuilder();
        argumentBuilder = new ArgumentBuilder();
        GroupBuilder groupBuilder = new GroupBuilder();

        Group options = groupBuilder
                .withName("options")
                .withOption(this.buildHelp())
                .withOption(this.buildDatabase())
                .withOption(this.buildTable())
                .withOption(this.buildHost())
                .withOption(this.buildUser())
                .withOption(this.buildPassword())
                .withOption(this.buildPasswordPrompt())
                .withOption(this.buildDatabaseType())
                .withOption(this.buildOutputFile())
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
     * Output File Argument
     * Here Apache's validator is being used to check the argument
     * Short:   -of
     * Long:    --outputfile
     *
     * @return Option for the output file
     */
    private Option buildOutputFile() {
        return optionBuilder
                .withLongName("outputfile")
                .withShortName("of")
                .withRequired(false)
                .withDescription("Set a name for the file in which the output will be created\nExample: output.txt"
                )
                .withArgument(
                        argumentBuilder
                                .withName("file path")
                                .withMinimum(1)
                                .withMaximum(1)
                                        //.withValidator(fileValidator)
                                .create()
                )
                .create();
    }

    /**
     * Option databaseType
     * Short:   -dt
     * Long:    --databaseType
     *
     * @return Option for the database type
     */
    private Option buildDatabaseType() {
        return optionBuilder
                .withLongName("databaseType")
                .withShortName("dt")
                .withRequired(false)
                .withDescription("Please specify which database you have, i.e. MySQL, PostgreSQL, etc.\n")
                .withArgument(
                        argumentBuilder
                                .withName("databaseType")
                                .withMinimum(1)
                                .withMaximum(1)
                                .create()
                )
                .create();
    }

    /**
     * Required Table Argument
     * Short:   -T
     * Long:    --Table
     *
     * @return Option for the table
     */
    private Option buildTable() {
        return optionBuilder
                .withLongName("Table")
                .withShortName("T")
                .withRequired(false)
                .withDescription("REQUIRED\nNeeded for the FROM-Clause for the Statement\n")
                .withArgument(
                        argumentBuilder
                                .withName("table")
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
            if (cl.hasOption(this.buildDatabase()) && cl.hasOption(this.buildTable()) ) {
                this.database = cl.getValue(buildDatabase()).toString();
                this.table = cl.getValue(buildTable()).toString();

                // Default (if nothing is set): localhost
                if (cl.hasOption(this.buildHost())) {
                    this.host = cl.getValue(buildHost()).toString();
                } else {
                    this.host = "localhost";
                }

                // Default: System's user name
                if (cl.hasOption(this.buildUser())) {
                    this.user = cl.getValue(buildUser()).toString();
                } else {
                    this.user = System.getProperty("user.name");
                }

                // Default password: none
                if (cl.hasOption(this.buildPassword())) {
                    this.password = cl.getValue(buildPassword()).toString();
                } else {
                    this.password = "";
                }
                
                // Password prompt in console
                if (cl.hasOption(this.buildPasswordPrompt())) {
                    this.password = new String(System.console().readPassword("Password: "));
                }

                // Database type, for future expansion. Default: mysql
                if (cl.hasOption(this.buildDatabaseType())) {
                    this.databaseType = cl.getValue(buildDatabaseType()).toString();
                } else {
                    this.databaseType = "mysql";
                }

                // File 
                if (cl.hasOption(this.buildOutputFile())) {
                    this.outputFile = cl.getValue(buildOutputFile()).toString();
                }
            } else {
                // If the required arguments haven't been added
                System.out.println("Required Arguments (Database, Expression and Table) not found.\n" +
                                    "Please look at the help page using -help");
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

    public String getDatabaseType() {
        return databaseType;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public String getTable() {
        return table;
    }
}
