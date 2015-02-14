package at.tgm.insy.backflip.metadata;

/**
 * Includes some possible queries for the JDBC get-Methods for ease-of-use.
 * @author Daniel Melichar
 * @version 10.02.2015
 */
public class DBStaticFields {

    /**
     * TABLE_NAME String => table name
     */
    static final int TABLE_NAME = 3;

    /**
     * COLUMN_NAME String => column name
     */
    static final int COLUMN_NAME = 4;

    /**
     * TYPE_NAME String => Data source dependent type name, for a UDT the type name is fully qualified
     */
    static final int TYPE_NAME = 6;

    /**
     * INDEX_NAME String => index name
     */
    static final int INDEX_NAME = 6;

    /**
     * FOREIGN_KEY string => foreign key
     */
    static final int FOREIGN_KEY = 8;

    /**
     * INDEX_COLUMNS String => columns for indexes
     */
    static final int INDEX_COLUMNS = 9;

    /**
     * REMARKS String => comment describing column (may be null)
     */
    static final int REMARKS = 12;

    /**
     * RELATION_NAME String => relation name
     */
    static final int RELATION_NAME = 12;

    /**
     * String => "NO" means column definitely does not allow NULL values; "YES" means the column might allow NULL values. An empty string means nobody knows.
     */
    static final int IS_NULLABLE = 18;
}
