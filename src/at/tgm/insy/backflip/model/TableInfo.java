package at.tgm.insy.backflip.model;

import java.util.ArrayList;
import java.util.List;

/**
 * All about that table
 * @author Daniel Melichar
 * @version 03.02.2015
 */
public class TableInfo {

    private String name;
    private final String type;
    private final String definition;
    private final List<AttributeInfo> attributes;
    private List<IndexInfo> indexes;

    public TableInfo() {
        name = "";
        type = "";
        definition = "";
        attributes = new ArrayList  <AttributeInfo>();
        indexes = new ArrayList<IndexInfo>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    void addAttribute(AttributeInfo info) {
        info.setParentTable(name);
        attributes.add(info);
    }

    public void addAttributes(List<AttributeInfo> attributes) {
        for (AttributeInfo attributeInfo : attributes) {
            addAttribute(attributeInfo);
        }
    }

    public List<AttributeInfo> getAttributes() {
        return attributes;
    }

    public void setIndexes(List<IndexInfo> indexes) {
        this.indexes = indexes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TableInfo)) return false;

        TableInfo tableInfo = (TableInfo) o;

        if (!attributes.equals(tableInfo.attributes)) return false;
        if (definition != null ? !definition.equals(tableInfo.definition) : tableInfo.definition != null) return false;
        if (indexes != null ? !indexes.equals(tableInfo.indexes) : tableInfo.indexes != null) return false;
        return !(name != null ? !name.equals(tableInfo.name) : tableInfo.name != null) && !(type != null ? !type.equals(tableInfo.type) : tableInfo.type != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (definition != null ? definition.hashCode() : 0);
        result = 31 * result + (attributes.hashCode());
        result = 31 * result + (indexes != null ? indexes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", definition='" + definition + '\'' +
                ", attributes=" + attributes +
                ", indexes=" + indexes +
                '}';
    }
}
