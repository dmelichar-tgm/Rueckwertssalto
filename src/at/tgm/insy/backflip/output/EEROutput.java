package at.tgm.insy.backflip.output;

import at.tgm.insy.backflip.connection.AbstractConnection;
import at.tgm.insy.backflip.metadata.DBReader;
import at.tgm.insy.backflip.model.AttributeInfo;
import at.tgm.insy.backflip.model.ERRelationshipInfo;
import at.tgm.insy.backflip.model.TableInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Generates a dot-File that describes the database. The dot-File is then being converted into
 * a PDF file displays a diagram (following the IDEFIX-Notation's standard). 
 *  
 * @author Daniel Melichar
 * @version 11.02.2015
 */
public class EEROutput implements Output {

    /* ATTRIBUTES */
    private OutputController controller;
    private DBReader dbReader;
    private List<TableInfo> tables;
    
    /* CONSTRUCTOR */
    public EEROutput(AbstractConnection connection) {
        dbReader = new DBReader();
        dbReader.setConnection(connection, connection.getMetaData());

        try {
            tables = dbReader.getTables(null, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Reading metadata..");
        }
    }

    @Override
    /**
     * Creates the dot and then the pdf
     */
    public void createOutput() {
        PrintWriter os = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            os = new PrintWriter(new File(controller.getOutputDirectory() + ".dot "));
            
            // START
            stringBuilder.append("digraph G {\n");
            stringBuilder.append(createIndent(1)).append("overlap=false\n");
            
            // TABLES
            tables = dbReader.getTables(null, null);
            for (TableInfo table : tables) {
                stringBuilder.append(createIndent(1)).append("\"")
                             .append(table.getName())
                             .append("\" ");
                
                stringBuilder.append("[shape=box, label=<\n").append(createIndent(4)).append("<TABLE>\n").append(createIndent(5)).append("<TR>")
                                    .append("<TD><B>").append(table.getName()).append("</B></TD>")
                                .append("</TR>\n");
                
                // primary keys
                Set<String> primaryKeys = dbReader.getPKs(null, null, table.getName());
                for (String primaryKey : primaryKeys) {
                    stringBuilder.append(createIndent(5))
                            .append("<TR>")
                            .append("<TD><I>PK: ").append(primaryKey).append("</I></TD>")
                            .append("</TR>\n");
                }
                
                // foreign keys
                Map<String, String> foreignKeys = dbReader.getFKs(null, null, table.getName());
                for (String key : foreignKeys.keySet()){
                    stringBuilder.append(createIndent(5))
                            .append("<TR>")
                            .append("<TD><I>FK: ").append(key).append("</I></TD>")
                            .append("</TR>\n");
                }
                
                // Attributes
                for (AttributeInfo attribute : table.getAttributes()) {
                    if (!attribute.isPK() && !attribute.isFK()) {
                        stringBuilder.append(createIndent(5)).append("<TR>")
                                        .append("<TD>").append(attribute.getName()).append(" ").append(attribute.getDataType().getName()).append("</TD>")
                                     .append("</TR>\n");

                    }
                }
                
                stringBuilder.append(createIndent(4)).append("</TABLE>\n").append(createIndent(3)).append(">];\n");
            }

            /* Relationships */
            List<ERRelationshipInfo> relationships = dbReader.getRelationships(null, null);
            for (ERRelationshipInfo relationship : relationships) {
                int foreignKeysAmount = relationship.getKeys().keySet().toString().length();
                if(relationship.isIdentifying()) {
                    if (relationship.isMultiToMulti()) {
                        stringBuilder.append(createIndent(1))
                                .append("\"")
                                .append(relationship.getChildTable())
                                .append("\" -> \"")
                                .append(relationship.getParentTable())
                                .append("\" [arrowhead=\"dot\" arrowtail=\"dot\" label=\"")
                                .append(relationship.getDefinition())
                                .append("\"];\n");
                    } else if (foreignKeysAmount <= 1&& foreignKeysAmount >= 0 ){
                        stringBuilder.append(createIndent(1))
                                .append("\"")
                                .append(relationship.getChildTable())
                                .append("\" -> \"")
                                .append(relationship.getParentTable())
                                .append("\" [arrowhead=\"dot\" dir=\"forward\" taillabel=\"Z\" label=\"")
                                .append(relationship.getDefinition())
                                .append("\"];\n");
                    } else if (foreignKeysAmount > 1) {
                        stringBuilder.append(createIndent(1))
                                .append("\"")
                                .append(relationship.getChildTable())
                                .append("\" -> \"")
                                .append(relationship.getParentTable())
                                .append("\" [arrowhead=\"dot\" dir=\"forward\" taillabel=\"P\" label=\"")
                                .append(relationship.getDefinition())
                                .append("\"];\n");
                    }
                } else if(relationship.isNonIdentifying()) {
                    if (foreignKeysAmount <= 1&& foreignKeysAmount >= 0 ){
                        stringBuilder.append(createIndent(1))
                                .append("\"")
                                .append(relationship.getChildTable())
                                .append("\" -> \"")
                                .append(relationship.getParentTable())
                                .append("\" [arrowhead=\"dot\" dir=\"forward\" style=\"dotted\" taillabel=\"Z\" label=\"")
                                .append(relationship.getDefinition())
                                .append("\"];\n");
                    } else if (foreignKeysAmount > 1) {
                        stringBuilder.append(createIndent(1))
                                .append("\"")
                                .append(relationship.getChildTable())
                                .append("\" -> \"")
                                .append(relationship.getParentTable())
                                .append("\" [arrowhead=\"dot\" dir=\"forward\" style=\"dotted\" taillabel=\"P\" label=\"")
                                .append(relationship.getDefinition())
                                .append("\"];\n");
                    }
                }
            }
            
            stringBuilder.append("}");
            os.print(stringBuilder);
            dbReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            
            System.out.println("Dot generated..");
            System.out.println("Converting to pdf..");
            generateGraphFromDot();
        }
    }

    private void generateGraphFromDot() {
        try {
            String executable = controller.getGraphvizBinPath() + "\\neato.exe";
            String output = "\"" + controller.getOutputDirectory() + ".pdf" + "\"";
            String input = controller.getOutputDirectory() + ".dot";
            
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(executable + " -Tpdf -o " + output + " " + input);
            int exitVal = proc.waitFor();

            if (exitVal == 1) {
                System.out.println("Conversion finished..");
                System.out.println("Stored files in.. " + controller.getOutputDirectory());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private String createIndent(int amount) {
        String indent = "";
        for (int x = 0; x < amount; x++) {
            indent += "\t";
        }
        return indent;
    }
    
    public void setController(OutputController controller) {this.controller = controller;}
}
