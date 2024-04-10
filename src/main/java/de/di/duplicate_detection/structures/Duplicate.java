package de.di.duplicate_detection.structures;

import de.di.Relation;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
public class Duplicate implements Comparable<Duplicate> {

    // The record index of the first record of this duplicate in its relation.
    private final int index1;

    // The record index of the second record of this duplicate in its relation.
    private final int index2;

    // The similarity of this duplicate; can be negative, if it is not used.
    private final double similarity;

    // The reference to the relation this duplicate was found in.
    private final Relation relation;

    public Duplicate(int index1, int index2, double similarity, Relation relation) {
        this.index1 = Math.min(index1, index2);
        this.index2 = Math.max(index1, index2);
        this.similarity = similarity;
        this.relation = relation;
    }

    @Override
    public String toString() {
        return "Duplicate(" + String.format("%1.6f", this.similarity) + ": " + this.index1 + ", " + this.index2 + ')';
    }

    public String toNaturalString() {
        return "Duplicate(" + String.format("%1.6f", this.similarity) + ",\n" +
                "     {" + this.index1 + ":" + Arrays.toString(this.relation.getRecords()[this.index1]) + "}\n" +
                "     {" + this.index2 + ":" + Arrays.toString(this.relation.getRecords()[this.index2]) + "})";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        Duplicate duplicate = (Duplicate) o;
        return this.index1 == duplicate.getIndex1() &&
                this.index2 == duplicate.getIndex2() &&
                this.relation.equals(duplicate.getRelation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getIndex1(), this.getIndex2(), this.getSimilarity(), this.getRelation().getName());
    }

    @Override
    public int compareTo(Duplicate o) {
        if (this.index1 < o.getIndex1())
            return -1;
        if (this.index1 > o.getIndex1())
            return 1;
        if (this.index2 < o.getIndex2())
            return -1;
        if (this.index2 > o.getIndex2())
            return 1;
        return 0;
    }
}
