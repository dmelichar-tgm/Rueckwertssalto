package at.tgm.insy.backflip.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Relationship base-class
 * @author Daniel Melichar
 * @version 03.02.2015
 */
public class RelationshipInfo {
    
    protected String name;
    protected String parentTable;
    protected String childTable;
    protected String definition;
    protected Map<String, String> keys;
    
    protected void init() {
        name = "";
        parentTable = "";
        childTable = "";
        definition = "";
        keys = new HashMap<String, String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentTable() {
        return parentTable;
    }

    public void setParentTable(String parentTable) {
        this.parentTable = parentTable;
    }

    public String getChildTable() {
        return childTable;
    }

    public void setChildTable(String childTable) {
        this.childTable = childTable;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void addKey(String parentKey, String childKey) {
        keys.put(parentKey, childKey);
    }

    public void removeKey(String parentKey) {
        keys.remove(parentKey);
    }

    public Map<String, String> getKeys() {
        return keys;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelationshipInfo)) return false;

        RelationshipInfo that = (RelationshipInfo) o;

        if (childTable != null ? !childTable.equals(that.childTable) : that.childTable != null) return false;
        if (definition != null ? !definition.equals(that.definition) : that.definition != null) return false;
        if (keys != null ? !keys.equals(that.keys) : that.keys != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (parentTable != null ? !parentTable.equals(that.parentTable) : that.parentTable != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (parentTable != null ? parentTable.hashCode() : 0);
        result = 31 * result + (childTable != null ? childTable.hashCode() : 0);
        result = 31 * result + (definition != null ? definition.hashCode() : 0);
        result = 31 * result + (keys != null ? keys.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RelationshipInfo{" +
                "name='" + name + '\'' +
                ", parentTable='" + parentTable + '\'' +
                ", childTable='" + childTable + '\'' +
                ", definition='" + definition + '\'' +
                ", keys=" + keys +
                '}';
    }
}
