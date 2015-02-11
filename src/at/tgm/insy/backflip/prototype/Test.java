package at.tgm.insy.backflip.prototype;

/**
 * Prints all databases and all their tables to console.
 * This prototype is used to try managing the metadata from 
 * a given database-server. 
 * @author Daniel Melichar
 * @version 12.01.2015
 */
public class Test {
    
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[shape=box, label=<")
                .append("<table>")
                .append("<tr>")
                .append("<th>").append("TestTable!").append("</th>")
                .append("</tr>");
        
        System.out.println(stringBuilder.toString());
    }
    
}
