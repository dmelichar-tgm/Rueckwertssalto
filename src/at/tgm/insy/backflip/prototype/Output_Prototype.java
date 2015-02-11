package at.tgm.insy.backflip.prototype;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * @author Daniel Melichar
 * @version 25.01.2015
 */
public class Output_Prototype {
    
    private static final String SERVER =   "localhost";           // vmwaredebian
    private static final String DATABASE = "tvprog_3chit";      // jdbcTest
    private static final String USERNAME = "root";              // jdbc
    private static final String PASSWORD = "root";              // jdbc


    public static void main(String[] args) throws SQLException, FileNotFoundException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(SERVER);
        dataSource.setDatabaseName(DATABASE);
        dataSource.setUser(USERNAME);
        dataSource.setPassword(PASSWORD);

        PrintWriter os = new PrintWriter(new File(System.getProperty("user.dir") + "\\output.txt"));


        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();

        ResultSet rsTables = metaData.getTables(null, null, null, null);

        while (rsTables.next()) {
            while (rsTables.next()) {
                os.print("\t" + rsTables.getString("TABLE_NAME") + " (");

                //* GET PRIMARY KEYS  *//
                ResultSet rsPrimaryKeys = metaData.getPrimaryKeys(null, null, rsTables.getString("Table_NAME"));
                ArrayList<String> primaryKeys = new ArrayList<String>();

                while (rsPrimaryKeys.next()) {
                    if (rsPrimaryKeys.getString("COLUMN_NAME").length() > 0) {
                        primaryKeys.add(rsPrimaryKeys.getString("COLUMN_NAME"));
                    }
                }

                //* PRINT PRIMARY KEYS *//
                os.print("PK <");
                for (int x = 0; x < primaryKeys.size(); x++) {
                    if (x < primaryKeys.size()-1) {
                        os.print(primaryKeys.get(x) + ", ");
                    } else {
                        os.print(primaryKeys.get(x));
                    }
                }
                os.print(">, ");

                //* GET FOREIGN KEYS *//
                ResultSet rsForeignKeys = metaData.getImportedKeys(null, null, rsTables.getString("TABLE_NAME"));
                ArrayList<String> foreignKeys = new ArrayList<String>();

                while (rsForeignKeys.next()) {
                    if (rsForeignKeys.getString("FKCOLUMN_NAME").length() > 0) {
                        foreignKeys.add(rsForeignKeys.getString("FKCOLUMN_NAME") + ":" + rsForeignKeys.getString("PKTABLE_NAME") + "." + rsForeignKeys.getString("PKCOLUMN_NAME"));
                    }
                }

                //* PRINT FOREIGN KEYS *//
                for (int x = 0; x < foreignKeys.size(); x++) {
                    if (x < foreignKeys.size()-1) {
                        os.print(foreignKeys.get(x) + ", ");
                    } else {
                        os.print(foreignKeys.get(x));
                    }
                }

                ResultSet rsColumns = metaData.getColumns(null, null, rsTables.getString("TABLE_NAME"), "%");
                ArrayList<String> columns = new ArrayList<String>();


                while (rsColumns.next()) {
                    columns.add(rsColumns.getString("COLUMN_NAME"));
                }


                for (int x = 0; x < columns.size(); x++) {
                    if (!(foreignKeys.contains(columns.get(x))) && !primaryKeys.contains(columns.get(x))) {
                        if (x < columns.size()-1) {
                            os.print(columns.get(x) + ", ");
                        } else {
                            os.print(columns.get(x) + ", ");
                        }
                    } else {
                        // do something
                    }
                }



                os.println(")\n");
            }
        }
        
        os.close();
        connection.close();
        System.out.println("Finished..");
    }

}
