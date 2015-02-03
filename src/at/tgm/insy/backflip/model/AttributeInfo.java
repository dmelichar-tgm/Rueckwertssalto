package at.tgm.insy.backflip.model;

/**
 * @author Daniel Melichar
 * @version 03.02.2015
 */
public class AttributeInfo {

    private String parentTable;
    private String name;
    private DatatypeInfo dataType;
    private String length;
    private String definition;
    private boolean isPK;
    private boolean isFK;
    private boolean isNotNull;

    public AttributeInfo() {
        parentTable = "";
        name = "";
        dataType = new DatatypeInfo();
        length = "";
        definition = "";
        isPK = false;
        isNotNull = false;
    }

    public String getParentTable() {
        return parentTable;
    }

    public void setParentTable(String parentTable) {
        this.parentTable = parentTable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DatatypeInfo getDataType() {
        return dataType;
    }

    public void setDataType(DatatypeInfo dataType) {
        this.dataType = dataType;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public boolean isPK() {
        return isPK;
    }

    public void setPK(boolean isPK) {
        this.isPK = isPK;
    }

    public boolean isFK() {
        return isFK;
    }

    public void setFK(boolean isFK) {
        this.isFK = isFK;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public void setNotNull(boolean isNotNull) {
        this.isNotNull = isNotNull;
    }

    @Override
    public String toString() {
        return "AttributeInfo{" +
                "parentTable='" + parentTable + '\'' +
                ", name='" + name + '\'' +
                ", dataType=" + dataType +
                ", length='" + length + '\'' +
                ", definition='" + definition + '\'' +
                ", isPK=" + isPK +
                ", isFK=" + isFK +
                ", isNotNull=" + isNotNull +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttributeInfo)) return false;

        AttributeInfo that = (AttributeInfo) o;

        if (isFK != that.isFK) return false;
        if (isNotNull != that.isNotNull) return false;
        if (isPK != that.isPK) return false;
        if (dataType != null ? !dataType.equals(that.dataType) : that.dataType != null) return false;
        if (definition != null ? !definition.equals(that.definition) : that.definition != null) return false;
        if (length != null ? !length.equals(that.length) : that.length != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (parentTable != null ? !parentTable.equals(that.parentTable) : that.parentTable != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = parentTable != null ? parentTable.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (definition != null ? definition.hashCode() : 0);
        result = 31 * result + (isPK ? 1 : 0);
        result = 31 * result + (isFK ? 1 : 0);
        result = 31 * result + (isNotNull ? 1 : 0);
        return result;
    }
}
