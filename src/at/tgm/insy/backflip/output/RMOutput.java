package at.tgm.insy.backflip.output;

import at.tgm.insy.backflip.connection.AbstractConnection;
import at.tgm.insy.backflip.metadata.DBReader;
import at.tgm.insy.backflip.model.AttributeInfo;
import at.tgm.insy.backflip.model.TableInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Creates a text file with a relational-mapping of the database. 
 * @author Daniel Melichar
 * @version 11.02.2015
 */
public class RMOutput implements Output {
    
    /* ATTRIBUTES */
    private OutputController controller;
    private DBReader dbReader;
    private List<TableInfo> tables;
    
    /* CONSTRUCTOR */
    public RMOutput(AbstractConnection connection) {
        dbReader = new DBReader();
        dbReader.setConnection(connection, connection.getMetaData());

        try {
            tables = dbReader.getTables(null, null);
        } catch (SQLException e) {
            System.out.println("Error while reading tables from database: " + e.getMessage());
        } finally {
            System.out.println("Reading metadata..");
        }
    }

    @Override
    /**
     * Creates the rm text file
     */
    public void createOutput() {
        PrintWriter os = null;
        StringBuilder stringBuilder = new StringBuilder();
        
        try {
            os = new PrintWriter(new File(controller.getOutputDirectory()));
            
            for (TableInfo table : tables) {
                String tableName = table.getName();
                stringBuilder.append(tableName).append(" (");
                
                // primary keys
                Set<String> primaryKeys = dbReader.getPKs(null, null, tableName);
                stringBuilder.append("PKs: <");
                for (Iterator<String> it = primaryKeys.iterator(); it.hasNext();) {
                    stringBuilder.append(it.next());
                    if (it.hasNext())  {
                        stringBuilder.append(", ");
                    }
                }
                
                //foreign keys
                stringBuilder.append(">, FKs: <");
                Map<String, String> foreignKeys = dbReader.getFKs(null, null, tableName);
                for (String key : foreignKeys.keySet()){
                    stringBuilder.append(key)
                                 .append(":")
                                 .append(foreignKeys.get(key))
                                 .append(", ");                
                }
                stringBuilder.replace(stringBuilder.length()-2, stringBuilder.length(), "");  // because maps are stupid

                // attributes
                stringBuilder.append(">, ");
                for (AttributeInfo attribute : table.getAttributes()) {
                    stringBuilder.append("(")
                                 .append(attribute.getDataType().getName())
                                 .append(")")
                                 .append(attribute.getName())
                                 .append(", ");
                }
                stringBuilder.replace(stringBuilder.length()-2, stringBuilder.length(), "");  // because im lazy
                stringBuilder.append(");\n");
            }
            
            os.print(stringBuilder);
            os.close();
            dbReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error while creating output file:" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL-Error while creating output file: " + e.getMessage());
        }
    }

    public void setController(OutputController controller) {this.controller = controller;}
}
