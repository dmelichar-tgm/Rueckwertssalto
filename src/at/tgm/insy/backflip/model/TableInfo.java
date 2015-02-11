package at.tgm.insy.backflip.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Melichar
 * @version 03.02.2015
 */
public class TableInfo {

    private String name;
    private String type;
    private String definition;
    private List<AttributeInfo> attributes;
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

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void addAttribute(AttributeInfo info) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIndexes(List<IndexInfo> indexes) {
        this.indexes = indexes;
    }

    public void addIndex(IndexInfo index) {
        indexes.add(index);
    }

    public List<IndexInfo> getIndexes() {
        return indexes;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TableInfo)) return false;

        TableInfo tableInfo = (TableInfo) o;

        if (attributes != null ? !attributes.equals(tableInfo.attributes) : tableInfo.attributes != null) return false;
        if (definition != null ? !definition.equals(tableInfo.definition) : tableInfo.definition != null) return false;
        if (indexes != null ? !indexes.equals(tableInfo.indexes) : tableInfo.indexes != null) return false;
        if (name != null ? !name.equals(tableInfo.name) : tableInfo.name != null) return false;
        if (type != null ? !type.equals(tableInfo.type) : tableInfo.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (definition != null ? definition.hashCode() : 0);
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
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
