package at.tgm.insy.backflip.prototype;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Daniel Melichar
 * @version 25.01.2015
 */
public class Output_Prototype {

    public static void main(String[] args) throws SQLException, FileNotFoundException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("root");
        dataSource.setPassword("root");

        PrintWriter os = new PrintWriter(new File(System.getProperty("user.dir") + "\\output.txt"));


        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rsDatabases = metaData.getCatalogs();

        while (rsDatabases.next()) {
            os.println("RM for Database:\t" + rsDatabases.getString("TABLE_CAT"));
            ResultSet rsTables = metaData.getTables(rsDatabases.getString("TABLE_CAT"), null, null, null);

           while (rsTables.next()) {
                os.print("\t" + rsTables.getString("TABLE_NAME") + "(");

                ResultSet rsKeys = metaData.getImportedKeys(rsDatabases.getString("TABLE_CAT"), null, rsTables.getString("TABLE_NAME"));

                while (rsKeys.next()) {
                    if (rsKeys.getString("PKCOLUMN_NAME").length() > 0) {
                        os.print("<PK>" +rsKeys.getString("PKCOLUMN_NAME") + ", ");
                    }

                    if (rsKeys.getString("FKCOLUMN_NAME").length() > 0) {
                        os.print("<FK>" +rsKeys.getString("FKTABLE_NAME") + ":" + rsKeys.getString("FKCOLUMN_NAME") + ", ");
                    }
                }

                ResultSet rsColumns = metaData.getColumns(rsDatabases.getString("TABLE_CAT"), null, rsTables.getString("TABLE_NAME"), "%");

                while (rsColumns.next()) {
                    os.print(rsColumns.getString("COLUMN_NAME") + ":" +rsColumns.getString("TYPE_NAME") + ", ");

                }

                os.println(")");
           }
            os.println("\n");
        }
        
        //os.flush();
        os.close();
        connection.close();
        System.out.println("Finished..");
    }
    
}
