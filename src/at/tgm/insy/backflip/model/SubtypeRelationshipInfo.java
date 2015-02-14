package at.tgm.insy.backflip.model;

/**
 * All about that subtype relationship
 * @author Daniel Melichar
 * @version 03.02.2015
 */
public class SubtypeRelationshipInfo extends RelationshipInfo{

    private String discriminatorAttribute;
    private boolean isConclusive;
    
    public SubtypeRelationshipInfo() {
        init();        
    }
    
    @Override
    void init() {
        super.init();
        discriminatorAttribute = "";
        isConclusive = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubtypeRelationshipInfo)) return false;
        if (!super.equals(o)) return false;

        SubtypeRelationshipInfo that = (SubtypeRelationshipInfo) o;

        return isConclusive == that.isConclusive && !(discriminatorAttribute != null ? !discriminatorAttribute.equals(that.discriminatorAttribute) : that.discriminatorAttribute != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (discriminatorAttribute != null ? discriminatorAttribute.hashCode() : 0);
        result = 31 * result + (isConclusive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SubtypeRelationshipInfo{" +
                "discriminatorAttribute='" + discriminatorAttribute + '\'' +
                ", isConclusive=" + isConclusive +
                '}';
    }
}
