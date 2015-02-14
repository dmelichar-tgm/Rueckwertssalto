package at.tgm.insy.backflip.model;

import java.util.ArrayList;
import java.util.List;

/**
 * All about that index
 * @author Daniel Melichar
 * @version 03.02.2015
 */
public class IndexInfo {
    
    private String name;
    private String parentEntity;
    private List<String> attributes;
    private boolean isUnique;
    
    public IndexInfo() {
        name = "";
        parentEntity = "";
        attributes = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentEntity() {
        return parentEntity;
    }

    public void setParentEntity(String parentEntity) {
        this.parentEntity = parentEntity;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void addAttribute(String attrName) {
        attributes.add(attrName);
    }
    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean isUnique) {
        this.isUnique = isUnique;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IndexInfo)) return false;

        IndexInfo indexInfo = (IndexInfo) o;

        if (isUnique != indexInfo.isUnique) return false;
        if (attributes != null ? !attributes.equals(indexInfo.attributes) : indexInfo.attributes != null) return false;
        if (name != null ? !name.equals(indexInfo.name) : indexInfo.name != null) return false;
        return !(parentEntity != null ? !parentEntity.equals(indexInfo.parentEntity) : indexInfo.parentEntity != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (parentEntity != null ? parentEntity.hashCode() : 0);
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        result = 31 * result + (isUnique ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IndexInfo{" +
                "name='" + name + '\'' +
                ", parentEntity='" + parentEntity + '\'' +
                ", attributes=" + attributes +
                ", isUnique=" + isUnique +
                '}';
    }
}
