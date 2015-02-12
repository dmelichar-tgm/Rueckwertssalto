package at.tgm.insy.backflip.prototype;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.*;
import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author Daniel Melichar
 * @version 05.02.2015
 */
public class Diagram_Prototype {

    private static final String SERVER   = "localhost";          // vmwaredebian
    private static final String DATABASE = "tvprog";              // jdbcTest
    private static final String USERNAME = "root";                  // jdbc
    private static final String PASSWORD = "root";                  // jdbc

    private Hashtable<String, Table> m_tables;

    public static void main(String[] args) throws IOException, SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(SERVER);
        dataSource.setDatabaseName(DATABASE);
        dataSource.setUser(USERNAME);
        dataSource.setPassword(PASSWORD);

        new Diagram_Prototype(dataSource.getConnection());
    }

    public Diagram_Prototype(Connection connection) {
        m_tables = new Hashtable<String, Table>();
        
        getMetaData(connection);
        createERD(System.getProperty("user.dir") + "\\out\\");

        // My system: D:\Graphviz2.38\bin
        try {
            Runtime.getRuntime().exec("D:\\Graphviz2.38\\bin\\neato.exe -Tpdf -o\""+System.getProperty("user.dir")+"\\"+DATABASE+".pdf\" "+"D:\\Graphviz2.38\\bin\\ERD_"+DATABASE+".dot");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void getMetaData(Connection con) {
        try {
            // Getting meta data from connection
            Statement st = con.createStatement();
            DatabaseMetaData dbm = con.getMetaData();

            // Get the tables
            ResultSet tables = dbm.getTables(null, null, null, null);
            
            while (tables.next()) {
                String table_name = tables.getString("TABLE_NAME");
                Table t = new Table(table_name);

                // Get all attributes
                ResultSet rs_attributes = dbm.getColumns(null, null, table_name, null);
                while (rs_attributes.next()) {
                    t.addAttribute(rs_attributes.getString("COLUMN_NAME"));
                }
                rs_attributes.close();

                // Get primary keys
                Hashtable<String, Attribute> temp_attributes = t.getAttributes();
                ResultSet rs_primaryKeys = dbm.getPrimaryKeys(null, null, table_name);
                while (rs_primaryKeys.next()) {
                    temp_attributes.get(rs_primaryKeys.getString("COLUMN_NAME")).setPrimary(true);
                }
                rs_primaryKeys.close();

                // All in table
                m_tables.put(table_name, t);
            }

            // Getting foreign key
            tables = dbm.getTables(null, null, null, null);
            
            while (tables.next()) {
                String table_name = tables.getString("TABLE_NAME");
                ResultSet rs_ForeignKeys = dbm.getExportedKeys(null, null, table_name);
                while (rs_ForeignKeys.next()) {
                    // Putting the foreign key information into the table
                    m_tables.get(rs_ForeignKeys.getString("FKTABLE_NAME")).setForeignAttribute(
                                        rs_ForeignKeys.getString("FKCOLUMN_NAME"),
                                        rs_ForeignKeys.getString("PKTABLE_NAME"),
                                        rs_ForeignKeys.getString("PKCOLUMN_NAME")
                    );
                }
            }

            st.close();
            tables.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createERD(String filename) {
        try {
            // Opening writer
            StringBuilder s = new StringBuilder();

            // Generating the DOT-File
            s.append("digraph G { overlap=false");
            Vector<String> stringvector = new Vector<String>();

            for (Enumeration<Table> e = m_tables.elements(); e.hasMoreElements();) {
                Table t_tamp = (e.nextElement());
                s.append(t_tamp.getName());
                s.append("\"[label=<<TABLE BORDER=\"0\" CELLBORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"4\"><TR><TD><B>");
                s.append("</B></TD></TR>" + t_tamp.getName());

                Hashtable<String, Attribute> x2 = t_tamp.getAttributes();

                //adding the primary keys first
                for (Enumeration<Attribute> e2 = x2.elements(); e2.hasMoreElements();) {
                    Attribute m = e2.nextElement();
                    if (m.isPrimary()) {
                        s.append("<TR><TD><U>" + m.getAttributeName());
                        s.append("</U></TD></TR>");
                    }
                }

                //adding the foreign keys
                for (Enumeration<Attribute> e2 = x2.elements(); e2.hasMoreElements();) {
                    Attribute m = e2.nextElement();
                    if (m.getFatt() != null) {
                        s.append("<TR><TD><I>" + m.getAttributeName());
                        s.append("</I></TD></TR>");
                    }
                }

                //adding the normal keys
                for (Enumeration<Attribute> e2 = x2.elements(); e2.hasMoreElements();) {
                    Attribute m = e2.nextElement();
                    if (m.getFatt() == null && !m.isPrimary()) {
                        s.append("<TR><TD>" + m.getAttributeName() + "</TD></TR>");
                    }
                }
                s.append("</TABLE>> ,shape=box];");

                //adding the relations
                for (Enumeration<Attribute> e2 = x2.elements(); e2.hasMoreElements();) {
                    Attribute m = e2.nextElement();
                    if (m.getFtab() != null) {
                        //stringvector.addElement(("\"" + t_tamp.getName()+ "\" -> \"" + m.getFtab() + "\" [arrowhead =\"none\" taillabel=\"(0,1)\" , headlabel=\"(0,*)\"];"));
                        stringvector.addElement(t_tamp.getName()+"-"+ m.getFtab());
                    }
                }
            }

            //searching for multiple relations -  deleting multiple relations
            for (int i = 0; i < stringvector.size(); ++i) {
                for (int j = 0; j < stringvector.size(); ++j) {
                    if (stringvector.elementAt(i).equals(stringvector.elementAt(j))&& (i != j))
                        stringvector.remove(j);
                }
            }

            // Adding the relations to the DOT file
            for(int i=0; i<stringvector.size();++i){
                String temp[] = stringvector.elementAt(i).split("-");
                s.append("\""+"rel"+i+"\" [label=\""+"rel"+i+"\" shape=diamond];");
                s.append("\""+temp[0]+"\" -> \""+"rel"+i+"\" [arrowhead =\"none\" taillabel=\"(0,1)\"];");
                s.append("\""+temp[1]+"\" -> \""+"rel"+i+"\" [arrowhead =\"none\" taillabel=\"(0,*)\"];");
            }

            s.append("}");

            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename + "\\ERD_" + DATABASE  + ".dot"), "utf-8"));
            writer.write(s.toString());
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
