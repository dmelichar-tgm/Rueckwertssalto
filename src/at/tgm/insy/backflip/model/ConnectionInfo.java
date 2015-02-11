package at.tgm.insy.backflip.model;

/**
 * @author Daniel Melichar
 * @version 03.02.2015
 */
public class ConnectionInfo {
    private String host;
    private String user;
    private String password;
    private String database;

    public ConnectionInfo() {
        host = "";
        user = "";
        password = "";
        database = "";
    }

    public ConnectionInfo(String host, String user, String password, String database) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConnectionInfo)) return false;

        ConnectionInfo that = (ConnectionInfo) o;

        if (database != null ? !database.equals(that.database) : that.database != null) return false;
        if (host != null ? !host.equals(that.host) : that.host != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = host != null ? host.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (database != null ? database.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConnectionInfo{" +
                "host='" + host + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", database='" + database + '\'' +
                '}';
    }
}
