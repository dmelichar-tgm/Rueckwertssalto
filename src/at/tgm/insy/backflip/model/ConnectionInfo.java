package at.tgm.insy.backflip.model;

/**
 * @author Daniel Melichar
 * @version 03.02.2015
 */
public enum ConnectionInfo {
    MYSQL,
    POSTGRESQL,
    ORACLE;
    
    private String host;
    private String user;
    private String password;
    private String database;

    ConnectionInfo() {
        host = "";
        user = "";
        password = "";
        database = "";
    }

    ConnectionInfo(String host, String user, String password, String database) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
