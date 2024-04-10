package de.di.schema_matching.helper;

public abstract class Metric {

    public abstract double run(int[][] groundTruthMatrix, double[][] simMatrix);

    public abstract double run(int[] groundTruthVector, double[] simVector);

    public abstract double run(int[][] groundTruthMatrix, int[][] corrMatrix);

    public abstract double run(int[] groundTruthVector, int[] corrVector);

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
