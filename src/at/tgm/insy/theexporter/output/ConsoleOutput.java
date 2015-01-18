package at.tgm.insy.theexporter.output;

import at.tgm.insy.theexporter.connection.Connection;

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Prints the ResultSet of the query, generated with the user's arguments, to the Console.
 * @author Daniel Melichar
 * @version 30.12.2014
 */
public class ConsoleOutput {

    /**
     * With a OutputController-Class, we can now display the ResultSet of the query.
     * For this, we'll be using a DefaultTableModel, since it resembles a SQL-Table the most
     * and can later be easily added to a GUI if need be. 
     * @param outputController OutputController Object
     */
    public ConsoleOutput(OutputController outputController) {
        Connection connection = outputController.createConnection();

        try {
            // Getting the ResultSet as well as its MetaData
            DefaultTableModel table = connection.sendQuery(outputController.createQuery());
            ResultSetMetaData metaData = connection.getMetaData();

            // Printing of the MetaData as header
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                System.out.format("%-50s", metaData.getColumnName(i + 1));
            }
            System.out.println("");

            // Printing of the Values of each table cell
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    System.out.format("%-50s", table.getValueAt(i, j));
                }
                System.out.println();
            }
            
            connection.close();
        } catch (SQLException e) {
            System.out.println("Warning: SQL Exception found: " +e.getMessage());
        }
    }

}
