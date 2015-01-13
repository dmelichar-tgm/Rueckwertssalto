package at.tgm.insy.backflip.schemacrawlerTest;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import schemacrawler.schema.*;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaInfoLevel;
import schemacrawler.utility.SchemaCrawlerUtility;

import java.sql.Connection;

public final class SchemacrawlerTest {

    public static void main(final String[] args) throws Exception {
        // Create a database connection
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("vmwaredebian");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        // Connects using root to get all available databases

        Connection connection = dataSource.getConnection();

        // Create the options
        final SchemaCrawlerOptions options = new SchemaCrawlerOptions();
        // Set what details are required in the schema - this affects the
        // time taken to crawl the schema
            options.setSchemaInfoLevel(SchemaInfoLevel.standard());

        // Get the schema definition
        final Catalog catalog = SchemaCrawlerUtility.getCatalog(connection, options);

        for (final Schema schema: catalog.getSchemas()) {
            System.out.println(schema);
            for (final Table table: catalog.getTables(schema)) {
                System.out.print("o--> " + table);
                if (table instanceof View) {
                    System.out.println(" (VIEW)");
                } else {
                    System.out.println();
                }

                for (final Column column: table.getColumns()) {
                    System.out.println("     o--> " + column + " (" + column.getColumnDataType() + ")");
                }
            }
        }
    }
}
