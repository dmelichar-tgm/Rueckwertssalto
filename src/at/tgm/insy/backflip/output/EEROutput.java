package at.tgm.insy.backflip.output;

import at.tgm.insy.backflip.connection.AbstractConnection;
import at.tgm.insy.backflip.metadata.DBReader;
import at.tgm.insy.backflip.model.AttributeInfo;
import at.tgm.insy.backflip.model.TableInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;


/**
 * @author Daniel Melichar
 * @version 11.02.2015
 */
public class EEROutput extends OutputController {

    private OutputController controller;
    private DBReader dbReader;
    private List<TableInfo> tables;
    
    public EEROutput(AbstractConnection connection) {
        dbReader = new DBReader();
        dbReader.setConnection(connection, connection.getMetaData());

        try {
            tables = dbReader.getTables(null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createOutput() {
        PrintWriter os = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            os = new PrintWriter(new File(controller.getFilePath()));
            
            // START
            stringBuilder.append("digraph G {\n");
            stringBuilder.append(createIndent(1)).append("overlap=false\n");
            
            // TABLES
            tables = dbReader.getTables(null, null);
            for (TableInfo table : tables) {
                stringBuilder.append(createIndent(1)).append("\"")
                             .append(table.getName())
                             .append("\" ");
                
                stringBuilder.append("[shape=box, label=<\n").append(createIndent(4)).append("<table>\n").append(createIndent(5)).append("<tr>")
                                    .append("<th>").append(table.getName()).append("</th>")
                                .append("</tr>\n");
                
                // primary keys
                Set<String> primaryKeys = dbReader.getPKs(null, null, table.getName());
                for (String primaryKey : primaryKeys) {
                    stringBuilder.append(createIndent(5)).append("<tr>")
                                    .append("<td><i>").append(primaryKey).append("</i></td>")
                                 .append("</tr>\n");
                }
                
                // Attributes
                for (AttributeInfo attribute : table.getAttributes()) {
                    if (!attribute.isPK()) {
                        stringBuilder.append(createIndent(5)).append("<tr>")
                                        .append("<td>").append(attribute.getName()).append(" ").append(attribute.getDataType().getName()).append("</td>")
                                     .append("</tr>\n");

                    }
                }
                
                stringBuilder.append(createIndent(4)).append("</table>\n").append(createIndent(3)).append(">];\n");
            }
            

            //System.out.println(stringBuilder.toString());
            
            /* ToDo
            List<ERRelationshipInfo> relationships = dbReader.getRelationships(null, null);
            for (ERRelationshipInfo relationship : relationships) {
                stringBuilder.append(relationship.getParentTable());
                //stringBuilder.append(" -> ")
                
                //relationship.getParentTable() + "->" + relationship.getChildTable() + "[label=\""+relationship.getDefinition()+"\"]";
            }
            */
            
            os.print(stringBuilder);
            os.close();
            dbReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            generateGraphFromDot();
        }
    }

    private void generateGraphFromDot() {
        
        
    }
    
    private String createIndent(int ammount) {
        String indent = "";
        for (int x = 0; x < ammount; x++) {
            indent += "\t";
        }
        return indent;
    }
    
    public void setController(OutputController controller) {this.controller = controller;}
}
