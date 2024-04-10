package de.di.similarity_measures;

public interface SimilarityMeasure {

    double calculate(final String string1, final String string2);

    double calculate(final String[] strings1, final String[] strings2);
}
