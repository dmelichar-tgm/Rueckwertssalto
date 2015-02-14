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
    protected void init() {
        super.init();
        discriminatorAttribute = "";
        isConclusive = false;
    }

    public String getDiscriminatorAttribute() {
        return discriminatorAttribute;
    }

    public void setDiscriminatorAttribute(String discriminatorAttribute) {
        this.discriminatorAttribute = discriminatorAttribute;
    }

    public boolean isConclusive() {
        return isConclusive;
    }

    public void setConclusive(boolean isConclusive) {
        this.isConclusive = isConclusive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubtypeRelationshipInfo)) return false;
        if (!super.equals(o)) return false;

        SubtypeRelationshipInfo that = (SubtypeRelationshipInfo) o;

        if (isConclusive != that.isConclusive) return false;
        if (discriminatorAttribute != null ? !discriminatorAttribute.equals(that.discriminatorAttribute) : that.discriminatorAttribute != null)
            return false;

        return true;
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
