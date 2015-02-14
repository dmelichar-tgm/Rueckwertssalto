package at.tgm.insy.backflip.model;

/**
 * (Some) possible databases.
 * @author Daniel Melichar
 * @version 10.02.2015
 */
public enum DatabaseTypes {
    ORACLE("Oracle"),
    MYSQL("MySQL"),
    POSTGRES("PostgreSQL");

    private String type;

    private DatabaseTypes(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public boolean selected(String dbType) {
        return getType().equalsIgnoreCase(dbType);
    }
}
