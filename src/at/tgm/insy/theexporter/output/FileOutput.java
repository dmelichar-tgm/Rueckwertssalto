package at.tgm.insy.theexporter.output;

import at.tgm.insy.theexporter.cli.CommandLineController;
import at.tgm.insy.theexporter.connection.Connection;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Prints the ResultSet of the query, generated with the user's arguments, to the Console.
 * @author Daniel Melichar
 * @version 30.12.2014
 */
public class FileOutput {

    /**
     * With a OutputController-Class, we can now display the ResultSet of the query.
     * For this, we'll be using a DefaultTableModel, since it resembles a SQL-Table the most
     * and can later be easily added to a GUI if need be. 
     * @param outputController OutputController Object
     * @param commandLineController Needed to get the File-Path                                               
     */
    public FileOutput(CommandLineController commandLineController, OutputController outputController) {
        Connection connection = outputController.createConnection();

        try {
            // File and PrintWriter
            File f = new File(commandLineController.getOutputFile());
            PrintWriter os = new PrintWriter(f);

            // Getting the ResultSet as well as its MetaData
            DefaultTableModel table = connection.sendQuery(outputController.createQuery());
            ResultSetMetaData metaData = connection.getMetaData();

            // Writing the MetaData as Header
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                os.format("%-50s", metaData.getColumnName(i + 1));
            }
            os.format("%n");

            // Writing the Values of each table cell
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    os.format("%-50s", table.getValueAt(i, j));
                }
                os.format("%n");
            }
            
            os.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
