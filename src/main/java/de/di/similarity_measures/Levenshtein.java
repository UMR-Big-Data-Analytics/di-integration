package de.di.similarity_measures;

public class Levenshtein implements SimilarityMeasure {

    private boolean withDamerau;

    public Levenshtein(boolean withDamerau) {
        this.withDamerau = withDamerau;
    }

    @Override
    public float calculate(String string1, String string2) {
        return 0;
    }

    @Override
    public float calculate(String[] strings1, String[] strings2) {
        return 0;
    }
}
