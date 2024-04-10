package de.di.data_profiling.structures;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * An AttributeList is an ordered list of attribute indexes. An attribute index corresponds to the (0-indexed) position
 * of that attribute in the attribute's schema. Attribute lists from different schemata are not comparable.
 */
@Getter
@AllArgsConstructor
public class AttributeList {

    private int[] attributes;

    public AttributeList(final int singleAttribute) {
        this.attributes = new int[]{singleAttribute};
    }

    /**
     * Returns the attributes of this attribute list as set.
     * @return the attributes of this attribute list as set.
     */
    public IntSet getAttributeSet() {
        return new IntArraySet(this.attributes);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.attributes);
    }

    /**
     * Returns the size of this attribute list.
     * @return The size of this attribute list.
     */
    public int size() {
        return this.attributes.length;
    }

    /**
     * Calculates the union of this attribute list and the provided attribute list.
     * @param other The other attribute list to union this attribute list with.
     * @return The union of both attribute lists with ordered attribute indices.
     */
    public AttributeList union(AttributeList other) {
        int[] attributes1 = this.attributes.clone();
        int[] attributes2 = other.getAttributes().clone();
        Arrays.sort(attributes1);
        Arrays.sort(attributes2);

        IntArrayList attributesUnion = new IntArrayList(attributes1.length + 1);
        int i = 0;
        int j = 0;
        while (i < attributes1.length || j < attributes2.length) {
            if (i >= attributes1.length) {
                attributesUnion.add(attributes2[j]);
                j++;
            } else if (j >= attributes2.length) {
                attributesUnion.add(attributes1[i]);
                i++;
            } else if (attributes1[i] == attributes2[j]) {
                attributesUnion.add(attributes1[i]);
                i++;
                j++;
            } else if (attributes1[i] < attributes2[j]) {
                attributesUnion.add(attributes1[i]);
                i++;
            } else {
                attributesUnion.add(attributes2[j]);
                j++;
            }
        }
        return new AttributeList(attributesUnion.toArray(new int[0]));
    }

    /**
     * Checks weather this attribute list has the same #attributes - 1 long prefix than the provided attribute list.
     * @param other The other attribute list to check the prefix with.
     * @return true if both attribute lists have the same #attributes - 1 long prefix.
     */
    public boolean samePrefixAs(AttributeList other) {
        if (this.attributes.length != other.getAttributes().length)
            return false;
        for (int i = 0; i < this.attributes.length - 1; i++)
            if (this.attributes[i] != other.getAttributes()[i])
                return false;
        return true;
    }

    /**
     * Checks weather this attribute list is a true superlist (i.e., not equal) of the provided attribute list.
     * A sublist is a list that not only contains all attributes of the other list, but these attributes also in the same order.
     * @param other The other attribute list to check the superlist relation with.
     * @return true if this attribute lists is a true superlist of the other attribute list.
     */
    public boolean superlistOf(AttributeList other) {
        if (this.attributes.length <= other.getAttributes().length)
            return false;
        int i = 0;
        int j = 0;
        while (true) {
            if (j == other.getAttributes().length)
                return true;
            if (i == this.attributes.length)
                return false;

            if (this.attributes[i] > other.getAttributes()[j])
                return false;
            if (this.attributes[i] < other.getAttributes()[j])
                i++;
            else if (this.attributes[i] == other.getAttributes()[j]) {
                i++;
                j++;
            }
        }
    }

    /**
     * Checks weather this attribute list is a true sublist (i.e., not equal) of the provided attribute list.
     * A sublist is a list that not only contains all attributes of the other list, but these attributes also in the same order.
     * @param other The other attribute list to check the sublist relation with.
     * @return true if this attribute lists is a true sublist of the other attribute list.
     */
    public boolean sublistOf(AttributeList other) {
        return other.superlistOf(this);
    }

    /**
     * Checks weather this attribute list is a true superset (i.e., not equal) of the provided attribute list.
     * @param other The other attribute list to check the superset relation with.
     * @return true if this attribute lists is a true superset of the other attribute list.
     */
    public boolean supersetOf(AttributeList other) {
        IntSet attributeSet1 = new IntArraySet(this.attributes);
        IntSet attributeSet2 = new IntArraySet(other.getAttributes());
        return attributeSet1.containsAll(attributeSet2);
    }

    /**
     * Checks weather this attribute list is a true subset (i.e., not equal) of the provided attribute list.
     * @param other The other attribute list to check the subset relation with.
     * @return true if this attribute lists is a true subset of the other attribute list.
     */
    public boolean subsetOf(AttributeList other) {
        return other.superlistOf(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        AttributeList that = (AttributeList) o;
        return Arrays.equals(this.attributes, that.getAttributes());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.attributes);
    }
}