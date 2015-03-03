package at.tgm.insy.theexporter.cli;

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
    private String orderBy = "";
    private String orderBySort = "";
    private String filter = "";
    private String separator = "";
    private String expression = "";
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
                .withOption(this.buildExpression())
                .withOption(this.buildTable())
                .withOption(this.buildHost())
                .withOption(this.buildUser())
                .withOption(this.buildPassword())
                .withOption(this.buildPasswordPrompt())
                .withOption(this.buildDatabaseType())
                .withOption(this.buildOrderBy())
                .withOption(this.buildOrderBySort())
                .withOption(this.buildFilter())
                .withOption(this.buildSeparator())
                .withOption(this.buildOutputFile())
                .create();

        // HelpFormatter
        HelpFormatter hf = new HelpFormatter();
        hf.setShellCommand("TheExporter.jar");
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
     * Order By Argument
     * Short:   -ob
     * Long:    --orderby
     *
     * @return Option for the ORDER BY - Statement
     */
    private Option buildOrderBy() {
        return optionBuilder
                .withLongName("orderby")
                .withShortName("ob")
                .withDescription("Used to sort the result-set by one or more columns\n")
                .withRequired(false)
                .withArgument(
                        argumentBuilder
                                .withName("order by")
                                .withMinimum(1)
                                .withMaximum(1)
                                .create()
                ).create();
    }

    /**
     * Order By Sort Argument
     * Short:   -obs
     * Long:    --orderbysort
     *
     * @return Option for the ORDER BY - Statement
     */
    private Option buildOrderBySort() {
        return optionBuilder
                .withLongName("orderbysort")
                .withShortName("obs")
                .withDescription("Sorts the records in ascending order by default.\n" +
                        "Please note that the orderBy-Argument must be fulfilled in order for this to work.\n" +
                        "To sort the records in a descending order, you can use the DESC keyword.\n")
                .withArgument(
                        argumentBuilder
                                .withName("obs")
                                .withMaximum(1)
                                .withMinimum(1)
                                .create()
                ).create();
    }

    /**
     * Filter (WHERE-Clause) Argument
     * Short:   -f
     * Long:    --filter
     *
     * @return Option for the WHERE-Clause
     */
    private Option buildFilter() {
        return optionBuilder
                .withLongName("filter")
                .withShortName("f")
                .withRequired(false)
                .withDescription("The WHERE clause is used to extract only those records that fulfill a specified criterion\n" +
                        "Example: 'title > 4'\n")
                .withArgument(
                        argumentBuilder
                                .withName("filter")
                                .withMinimum(1)
                                .withMaximum(1)
                                .create()
                )
                .create();
    }

    /**
     * Separator Argument
     * Short:   -s
     * Long:    separator
     * Default: Semicolon (;)
     *
     * @return Option for the separator
     */
    private Option buildSeparator() {
        return optionBuilder
                .withLongName("separator")
                .withShortName("s")
                .withRequired(false)
                .withDescription("Used to finish queries\n")
                .withArgument(
                        argumentBuilder
                                .withName("separator")
                                .withMinimum(1)
                                .withMaximum(1)
                                .create()
                )
                .create();
    }

    /**
     * Required Expression Argument
     * Short:   -E
     * Long:    --Expression
     * Separator: Comma (,)
     *
     * @return Option for the expression
     */
    private Option buildExpression() {
        return optionBuilder
                .withLongName("Expression")
                .withShortName("E")
                .withRequired(false)
                .withDescription("REQUIRED\nThe column names for the SELECT-Statement separated by comma\nTo select everything from a table, use 'all'.\n")
                .withArgument(
                        argumentBuilder
                                .withName("expression")
                                .withMinimum(1)
                                .withMaximum(1)
                                .withInitialSeparator(',')
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
            // Required Arguments: Database, Table and Expression
            if (cl.hasOption(this.buildDatabase()) && cl.hasOption(this.buildTable()) && cl.hasOption(this.buildExpression())) {
                this.database = cl.getValue(buildDatabase()).toString();
                this.table = cl.getValue(buildTable()).toString();
                
                // Check if the User wants to display everything from the table
                if (cl.getValue(buildExpression()).toString().equalsIgnoreCase("ALL")) {
                    this.expression = "*";
                } else {
                    this.expression = cl.getValue(buildExpression()).toString();
                }
                
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

                // The ORDER BY - Clause 
                if (cl.hasOption(this.buildOrderBy())) {
                    this.orderBy = cl.getValue(buildOrderBy()).toString();
                    if (cl.hasOption(this.buildOrderBySort()))
                        this.orderBySort = cl.getValue(buildOrderBySort()).toString();
                    else
                        this.orderBySort = " ASC";
                }

                // WHERE-Clause
                if (cl.hasOption(this.buildFilter())) {
                    this.filter = cl.getValue(buildFilter()).toString();
                }

                // Separator. Default: Semicolon
                if (cl.hasOption(this.buildSeparator())) {
                    this.separator = cl.getValue(buildSeparator()).toString();
                } else {
                    this.separator = ";";
                }

                // File 
                if (cl.hasOption(this.buildOutputFile())) {
                    this.outputFile = cl.getValue(buildOutputFile()).toString();
                }
            } else {
                // If the required arguments haven't been added
                throw new IllegalArgumentException("Required Arguments (Database, Expression and Table) not found.\nPlease look at the help page using -help");
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

    public String getOrderBy() {
        return orderBy;
    }

    public String getOrderSort() {
        return orderBySort;
    }

    public String getFilter() {
        return filter;
    }

    public String getSeparator() {
        return separator;
    }

    public String getExpression() {
        return expression;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public String getTable() {
        return table;
    }
}
