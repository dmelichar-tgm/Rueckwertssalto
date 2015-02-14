package at.tgm.insy.backflip.model;

/**
 * All abou that datatype
 * @author Daniel Melichar
 * @version 03.02.2015
 */
public class DatatypeInfo {
    
    private String name;
    private final String length;
    private final String description;
    
    public DatatypeInfo() {
        name = "";
        length = "";
        description = "";        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatatypeInfo)) return false;

        DatatypeInfo that = (DatatypeInfo) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return !(length != null ? !length.equals(that.length) : that.length != null) && !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DatatypeInfo{" +
                "name='" + name + '\'' +
                ", length='" + length + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

