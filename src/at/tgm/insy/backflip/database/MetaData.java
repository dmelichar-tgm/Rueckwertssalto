package at.tgm.insy.backflip.database;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;

/**
 * @author Daniel Melichar
 * @version 26.01.2015
 */
public class MetaData {
    
    private DatabaseMetaData metaData;
    
    private ArrayList<String> primaryKeys;
    private ArrayList<String> foreignKeys;
    private ArrayList<String> attributes;
    
    public MetaData(DatabaseMetaData metaData) {
        this.metaData = metaData;
    }
    
}
