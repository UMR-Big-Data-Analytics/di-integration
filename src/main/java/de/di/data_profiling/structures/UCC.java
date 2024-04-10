package de.di.data_profiling.structures;

import de.di.Relation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

/**
 * A UCC is a representation of a unique column combination, which is based on an AttributeList of a certain Relation.
 * Note that attribute lists are ordered, but UCCs are based on set semantics. Hence, two UCC objects are considered
 * equal if their attribute sets are equal, but the linearized representation still follows the attribute lists as they
 * are defined during object creation.
 */
@Getter
@AllArgsConstructor
public class UCC {

    private final Relation relation;
    private final AttributeList attributeList;

    @Override
    public String toString() {
        return "UCC(" + this.relation.getName() + this.attributeList + ")";
    }

    public String toNaturalString() {
        String[] attributeLabels = new String[this.attributeList.size()];
        for (int i = 0; i < this.attributeList.size(); i++)
            attributeLabels[i] = this.relation.getAttributes()[this.attributeList.getAttributes()[i]];
        return "UCC(" + this.relation.getName() + Arrays.toString(attributeLabels) + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        UCC ucc = (UCC) o;
        return Objects.equals(this.getRelation(), ucc.getRelation()) &&
                Objects.equals(this.getAttributeList().getAttributeSet(), ucc.getAttributeList().getAttributeSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.relation.getName(), this.getAttributeList().getAttributeSet());
    }
}
