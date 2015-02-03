package at.tgm.insy.backflip.model;

/**
 * @author Daniel Melichar
 * @version 03.02.2015
 */
public class DatatypeInfo {
    
    private String name;
    private String length;
    private String description;
    
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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatatypeInfo)) return false;

        DatatypeInfo that = (DatatypeInfo) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (length != null ? !length.equals(that.length) : that.length != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
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

