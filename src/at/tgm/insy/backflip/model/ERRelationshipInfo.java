package at.tgm.insy.backflip.model;

/**
 * All about them er-relationships.
 * @author Daniel Melichar
 * @version 03.02.2015
 */
public class ERRelationshipInfo extends RelationshipInfo{

    private String verbPhraseParent;
    private String verbPhraseChild;
    private boolean isIdentifying;
    private boolean isNonIdentifying;
    private boolean isMultiToMulti;
    private boolean isParentRequired;
    private String erIndex;
    
    public ERRelationshipInfo() {
        init();        
    }

    @Override
    void init() {
        super.init();
        verbPhraseParent = "";
        verbPhraseChild = "";
        isIdentifying = false;
        isNonIdentifying = false;
        isMultiToMulti = false;
        isParentRequired = false;
    }

    public boolean isIdentifying() {
        return isIdentifying;
    }

    public void setIdentifying() {
        this.isIdentifying = true;
    }

    public boolean isNonIdentifying() {
        return isNonIdentifying;
    }

    public void setNonIdentifying() {
        this.isNonIdentifying = true;
    }

    public boolean isMultiToMulti() {
        return isMultiToMulti;
    }

    public void setParentRequired() {
        this.isParentRequired = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ERRelationshipInfo)) return false;
        if (!super.equals(o)) return false;

        ERRelationshipInfo that = (ERRelationshipInfo) o;

        if (isIdentifying != that.isIdentifying) return false;
        if (isMultiToMulti != that.isMultiToMulti) return false;
        if (isNonIdentifying != that.isNonIdentifying) return false;
        if (isParentRequired != that.isParentRequired) return false;
        if (erIndex != null ? !erIndex.equals(that.erIndex) : that.erIndex != null) return false;
        return !(verbPhraseChild != null ? !verbPhraseChild.equals(that.verbPhraseChild) : that.verbPhraseChild != null) && !(verbPhraseParent != null ? !verbPhraseParent.equals(that.verbPhraseParent) : that.verbPhraseParent != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (verbPhraseParent != null ? verbPhraseParent.hashCode() : 0);
        result = 31 * result + (verbPhraseChild != null ? verbPhraseChild.hashCode() : 0);
        result = 31 * result + (isIdentifying ? 1 : 0);
        result = 31 * result + (isNonIdentifying ? 1 : 0);
        result = 31 * result + (isMultiToMulti ? 1 : 0);
        result = 31 * result + (isParentRequired ? 1 : 0);
        result = 31 * result + (erIndex != null ? erIndex.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ERRelationshipInfo{" +
                "verbPhraseParent='" + verbPhraseParent + '\'' +
                ", verbPhraseChild='" + verbPhraseChild + '\'' +
                ", isIdentifying=" + isIdentifying +
                ", isNonIdentifying=" + isNonIdentifying +
                ", isMultiToMulti=" + isMultiToMulti +
                ", isParentRequired=" + isParentRequired +
                ", erIndex='" + erIndex + '\'' +
                '}';
    }
}
