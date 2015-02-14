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
    protected void init() {
        super.init();
        verbPhraseParent = "";
        verbPhraseChild = "";
        isIdentifying = false;
        isNonIdentifying = false;
        isMultiToMulti = false;
        isParentRequired = false;
    }

    public String getVerbPhraseParent() {
        return verbPhraseParent;
    }

    public void setVerbPhraseParent(String verbPhraseParent) {
        this.verbPhraseParent = verbPhraseParent;
    }

    public String getVerbPhraseChild() {
        return verbPhraseChild;
    }

    public void setVerbPhraseChild(String verbPhraseChild) {
        this.verbPhraseChild = verbPhraseChild;
    }

    public boolean isIdentifying() {
        return isIdentifying;
    }

    public void setIdentifying(boolean isIdentifying) {
        this.isIdentifying = isIdentifying;
    }

    public boolean isNonIdentifying() {
        return isNonIdentifying;
    }

    public void setNonIdentifying(boolean isNonIdentifying) {
        this.isNonIdentifying = isNonIdentifying;
    }

    public boolean isMultiToMulti() {
        return isMultiToMulti;
    }

    public void setMultiToMulti(boolean isMultiToMulti) {
        this.isMultiToMulti = isMultiToMulti;
    }

    public boolean isParentRequired() {
        return isParentRequired;
    }

    public void setParentRequired(boolean isParentRequired) {
        this.isParentRequired = isParentRequired;
    }

    public String getErIndex() {
        return erIndex;
    }

    public void setErIndex(String erIndex) {
        this.erIndex = erIndex;
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
        if (verbPhraseChild != null ? !verbPhraseChild.equals(that.verbPhraseChild) : that.verbPhraseChild != null)
            return false;
        if (verbPhraseParent != null ? !verbPhraseParent.equals(that.verbPhraseParent) : that.verbPhraseParent != null)
            return false;

        return true;
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
