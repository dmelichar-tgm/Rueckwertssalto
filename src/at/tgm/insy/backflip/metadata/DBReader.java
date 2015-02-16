package at.tgm.insy.backflip.metadata;

import at.tgm.insy.backflip.connection.AbstractConnection;
import at.tgm.insy.backflip.model.*;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Reads all required and necessary information from the database's metadata 
 * @author Daniel Melichar
 * @version 10.02.2015
 */
public class DBReader extends DBStaticFields {
    
    /* ATTRIBUTES */
    private static DBReader instace = null;

    private DBConnection connection;

    private String dbType;

    private List<String> tableNames;

    /* CONSTRUCTOR(S) */
    
    public DBReader() {
        connection = null;
        dbType = "";
        tableNames = null;
    }
    
    /* METHODS 1 */
    
    public void setConnection(AbstractConnection connection, DatabaseMetaData metaData) {
        this.connection = new DBConnection(connection.getConnection(), metaData);
    }

    public static DBReader getInstance() {
        if (instace == null) {instace = new DBReader();}
        return instace;
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }
    
    /* PUBLIC METHODS */

    /**
     * All schemata as String array 
     * @return String array with all schemata
     * @throws SQLException Connection errors
     */
    public String[] getSchemata() throws SQLException {
        if (DatabaseTypes.ORACLE.selected(dbType)) {
            return connection.getSchemata();
        } else if (DatabaseTypes.MYSQL.selected(dbType)) {
            return connection.getCatalogs();
        } else if (DatabaseTypes.POSTGRES.selected(dbType)) {
            return connection.getCatalogs();
        }

        return connection.getSchemata();
    }

    /**
     * Set with all primary keys as string.
     * Requires a database's catalog or schema and a table* 
     * @param catalog database catalog
     * @param schema database schema
     * @param table database table
     * @return Set with all primary keys as string
     * @throws SQLException Connection error
     */
    public HashSet<String> getPKs(String catalog, String schema, String table) throws SQLException {
        ResultSet res = getPKSet(catalog, schema, table);
        if (res == null) {
            res = getPKSet(schema, catalog, table);
        }
        HashSet<String> pks = new HashSet<String>();
        if (res != null) {
            while (res.next()) {
                pks.add(res.getString(COLUMN_NAME));
            }
            res.close();
        }
        return pks;
    }

    /**
     * Receives all primary keys in a map, where key is equal to the foreign keys name and
     * the value is equal to the foreign table and the references attribute there.
     * Requires a database catalog or schema and a table 
     * @param catalog database catalog
     * @param schema database schema
     * @param table database table
     * @return Map with all primary keys
     * @throws SQLException Connection errors
     */
    public HashMap<String, String> getFKs(String catalog, String schema, String table) throws SQLException {
        HashMap<String, String> fks = new HashMap<String, String>();
        ResultSet res;
        
        try {
            res = connection.getMetaData().getImportedKeys(catalog, schema, table);
        } catch (Exception e) {
            return fks;
        }
        
        while (res.next()) {
            // reference table name
            String tableName = res.getString(TABLE_NAME);
            // reference column name(primary key)
            String pkName = res.getString(COLUMN_NAME);
            // current column name(foreign key)
            String fkName = res.getString(FOREIGN_KEY);

            fks.put(fkName, tableName + "." + pkName);
        }
        res.close();
        return fks;
    }

    /**
     * Gets a sorted list of objects which include more info of all
     * tables in the database 
     * Requires a database catalog or schema. 
     * @param catalog Database catalog
     * @param schema Database schema
     * @return Sorted list with all tables
     * @throws SQLException Connection error
     */
    public List<TableInfo> getTables(String catalog, String schema) throws SQLException {
        if (connection == null) {
            return null;
        }

        tableNames = connection.getTables(catalog, schema);

        List<TableInfo> tableList = new ArrayList<TableInfo>();
        TableInfo tbInfo;
        for (String tableName : tableNames) {
            tbInfo = new TableInfo();
            // name
            tbInfo.setName(tableName);
            // attributes
            tbInfo.addAttributes(getAttributes(catalog, schema, tableName));
            // indexes
            tbInfo.setIndexes(getIndexes(catalog, schema, tableName));

            tableList.add(tbInfo);
        }

        return tableList;
    }

    /**
     * Receives a list of objects which include information about
     * all relationships in the database.
     * Requires a database catalog or schema
     * @param catalog Database catalog
     * @param schema Database schema
     * @return list with all info of the relationships
     * @throws SQLException Connection errors
     */
    public List<ERRelationshipInfo> getRelationships(String catalog, String schema) throws SQLException {
        if (connection == null) {
            return null;
        }
        if (tableNames == null) {
            tableNames = connection.getTables(catalog, schema);
        }
        
        List<ERRelationshipInfo> relationList = new ArrayList<ERRelationshipInfo>();
        PreparedStatement preparedStatement = null;
        
        for (String tableName : tableNames) {
            HashSet<String> pks = getPKs(catalog, schema, tableName);
            Map<String, ERRelationshipInfo> relationMap = new HashMap<String, ERRelationshipInfo>();
            ResultSet res = connection.getMetaData().getImportedKeys(catalog, schema, tableName);

            while (res.next()) {
                //relation name
                String relationName = res.getString(RELATION_NAME);
                
                //current column name(foreign key)
                String fkName = res.getString(FOREIGN_KEY);

                //reference table name
                String referenceTableName = res.getString(TABLE_NAME);

                //reference column name(primary key)
                String pkName = res.getString(COLUMN_NAME);

                if (relationMap.keySet().contains(referenceTableName)) {
                    ERRelationshipInfo info = relationMap.get(referenceTableName);
                    if (!info.getKeys().containsKey(pkName)) {
                        info.addKey(pkName, fkName);
                    }
                    continue;
                }
                
                // set info in Object
                ERRelationshipInfo rInfo = new ERRelationshipInfo();
                rInfo.setChildTable(tableName);
                rInfo.setParentTable(referenceTableName);
                rInfo.setName(relationName);
                rInfo.addKey(pkName, fkName);
                
                if (pks.contains(fkName)) {
                    rInfo.setIdentifying(true);
                    rInfo.setParentRequired(true);
                } else {
                    rInfo.setNonIdentifying(true);
                }

                relationMap.put(referenceTableName, rInfo);
                relationList.add(rInfo);
            }
            res.close();
        }
        
        if (preparedStatement != null) {
            preparedStatement.close();
            preparedStatement = null;
        }
        return relationList;
    }


    /* PRIVATE METHODS */

    private ResultSet getPKSet(String catalog, String schema, String table) {
        try {
            return connection.getMetaData().getPrimaryKeys(catalog, schema, table);
        } catch (SQLException e) {
            System.out.println("Error while getting primary keys" + e.getMessage());
            return null;
        }
    }
    
   
    private List<IndexInfo> getIndexes(String catalog, String schema, String table) throws SQLException {
        List<IndexInfo> indexes = new ArrayList<IndexInfo>();
        indexes.addAll(getIndexInfoes(catalog, schema, table));
        return indexes;
    }

    private List<IndexInfo> getIndexInfoes(String catalog, String schema, String table) throws SQLException {
        List<String> uniques = getUniqueIndexes(catalog, schema, table);
        ResultSet indexSet = getIndexSet(catalog, schema, table, false);
        if (indexSet == null) {
            indexSet = getIndexSet(schema, catalog, table, false);
        }
        
        List<IndexInfo> indexes = new ArrayList<IndexInfo>();
        if (indexSet != null) {
            Map<String, IndexInfo> indexMap = new HashMap<String, IndexInfo>();
            while (indexSet.next()) {
                //parentEntity
                String tableName = indexSet.getString(TABLE_NAME);
                //name
                String name = indexSet.getString(INDEX_NAME);
                if (name == null) {
                    continue;
                }
                IndexInfo indexInfo;
                if (indexMap.get(name) != null) {
                    indexInfo = indexMap.get(name);
                } else {
                    indexInfo = new IndexInfo();
                    //unique
                    indexInfo.setUnique(uniques.contains(name));
                    indexInfo.setName(name);
                    indexInfo.setParentEntity(tableName);
                }
                //attributes
                String[] attributes = indexSet.getString(INDEX_COLUMNS).split(",");
                for (String attribute : attributes) {
                    indexInfo.addAttribute(attribute);
                }
                if (!indexes.contains(indexInfo)) {
                    indexes.add(indexInfo);
                }
                indexMap.put(name, indexInfo);
            }
            indexSet.close();
        }
        return indexes;
    }
    private ResultSet getIndexSet(String catalog, String schema, String table, boolean isUnique) {
        try {
            return connection.getMetaData().getIndexInfo(catalog, schema, table, isUnique, true);
        } catch (SQLException e) {
            return null;
        }
    }

    private List<String> getUniqueIndexes(String catalog, String schema, String table) throws SQLException {
        List<String> uniques = new ArrayList<String>();
        ResultSet indexSet = getIndexSet(catalog, schema, table, true);
        if (indexSet == null) {
            indexSet = getIndexSet(schema, catalog, table, true);
        }
        if (indexSet != null) {
            while (indexSet.next()) {
                //name
                String name = indexSet.getString(INDEX_NAME);
                if (name == null) {
                    continue;
                }
                uniques.add(name);
            }
            indexSet.close();
        }
        return uniques;
    }

    private List<AttributeInfo> getAttributes(String catalog, String schema, String tbName) throws SQLException {
        HashSet<String> pks = getPKs(catalog, schema, tbName);
        HashMap<String, String> fks = getFKs(catalog, schema, tbName);
        ResultSet attrSet = connection.getMetaData().getColumns(catalog, schema, tbName, "%");
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        List<String> attrs = new ArrayList<String>();
        
        while (attrSet.next()) {
            AttributeInfo atInfo = new AttributeInfo();

            //name
            String attrName = getString(attrSet, COLUMN_NAME);
            if (attrs.contains(attrName)) {
                continue;
            }
            attrs.add(attrName);
            atInfo.setName(attrName);

            //data type
            DatatypeInfo dInfo = getDatatypeInfo(attrSet);
            atInfo.setDataType(dInfo);

            //remark
            String definition = getString(attrSet, REMARKS);
            definition = definition == null ? "" : definition;
            atInfo.setDefinition(definition);
            

            //not null
            String notNull = getString(attrSet,IS_NULLABLE);
            if (notNull != null) {
                notNull = notNull.trim();
            }
            atInfo.setNotNull("NO".equals(notNull));

            //Primary Key
            atInfo.setPK(pks.contains(attrName));
            atInfo.setFK(fks.keySet().contains(attrName));

            attributes.add(atInfo);
        }
        attrSet.close();

        return attributes;
    }

    private String getString(ResultSet rSet, int columnIndex) {
        String value;
        try {
            value = rSet.getString(columnIndex);
        } catch (Exception e) {
            return "";
        }
        return value;
    }

    private String getValidDatatypeName(String name) {
        String lowerCaseName = name.toLowerCase();
        //remove "Identity", jude not support it so far
        if (lowerCaseName.contains("identity")) {
            name = name.substring(0, lowerCaseName.indexOf("identity")).trim();
        } else if (lowerCaseName.contains("unsigned")) {
            name = name.substring(0, lowerCaseName.indexOf("unsigned")).trim();
        }
        return name;
    }
    
    private DatatypeInfo getDatatypeInfo(ResultSet attrSet) throws SQLException {
        DatatypeInfo dInfo = new DatatypeInfo();
        String dtName = attrSet.getString(TYPE_NAME);
        dtName = getValidDatatypeName(dtName);
        dInfo.setName(dtName);
        return dInfo;
    }
}
