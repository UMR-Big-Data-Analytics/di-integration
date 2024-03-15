package de.di.similarity_measures;

public class Jaccard implements SimilarityMeasure {

    private int tokenSize;
    private boolean usePadding;

    public Jaccard(int tokenSize, boolean usePadding) {
        this.tokenSize = tokenSize;
        this.usePadding = usePadding;
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
