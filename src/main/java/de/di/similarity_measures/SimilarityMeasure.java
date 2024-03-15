package de.di.similarity_measures;

public interface SimilarityMeasure {
    public float calculate(String string1, String string2);
    public float calculate(String[] strings1, String[] strings2);
}
