package de.di.data_profiling.structures;

import de.di.Relation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * An IND is a representation of an inclusion dependency with a left-hand-side (lhs) and a right-hand-side (rhs) that
 * correspond to attribute lists of two (potentially same) relations. The arity of an IND is the number of lhs and rhs
 * attributes. Note that |lhs| == |rhs| needs to be true for INDs.
 */
@Getter
public class IND {

    private final Relation lhsRelation;
    private final AttributeList lhsAttributes;

    private final Relation rhsRelation;
    private final AttributeList rhsAttributes;

    public IND(Relation lhsRelation, int lhsAttribute, Relation rhsRelation, int rhsAttribute) {
        this(lhsRelation, new AttributeList(lhsAttribute), rhsRelation, new AttributeList(rhsAttribute));
    }

    public IND(Relation lhsRelation, AttributeList lhsAttributes, Relation rhsRelation, AttributeList rhsAttributes) {
        assert (lhsAttributes.getAttributes().length == rhsAttributes.getAttributes().length);
        this.lhsRelation = lhsRelation;
        this.lhsAttributes = lhsAttributes;
        this.rhsRelation = rhsRelation;
        this.rhsAttributes = rhsAttributes;
    }

    @Override
    public String toString() {
        return "IND(" + this.lhsRelation.getName() + this.lhsAttributes + ", " + this.rhsRelation.getName() + this.rhsAttributes + ")";
    }

    public String toNaturalString() {
        String[] lhsAttributeLabels = new String[this.lhsAttributes.size()];
        for (int i = 0; i < this.lhsAttributes.size(); i++)
            lhsAttributeLabels[i] = this.lhsRelation.getAttributes()[this.lhsAttributes.getAttributes()[i]];
        String[] rhsAttributeLabels = new String[this.rhsAttributes.size()];
        for (int i = 0; i < this.rhsAttributes.size(); i++)
            rhsAttributeLabels[i] = this.rhsRelation.getAttributes()[this.rhsAttributes.getAttributes()[i]];
        return "IND(" + this.lhsRelation.getName() + Arrays.toString(lhsAttributeLabels) + ", "+ this.rhsRelation.getName() + Arrays.toString(rhsAttributeLabels) + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        IND ind = (IND) o;
        return Objects.equals(this.getLhsRelation(), ind.getLhsRelation()) &&
                Objects.equals(this.getLhsAttributes(), ind.getLhsAttributes()) &&
                Objects.equals(this.getRhsRelation(), ind.getRhsRelation()) &&
                Objects.equals(this.getRhsAttributes(), ind.getRhsAttributes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.lhsRelation.getName(), this.lhsAttributes, this.rhsRelation.getName(), this.rhsAttributes);
    }
}
