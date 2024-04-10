package de.di.schema_matching.helper;

import java.util.Arrays;

public abstract class AUCMetric extends Metric {

    @Override
    public double run(int[][] groundTruthMatrix, double[][] simMatrix) {
        return this.run(this.flattenMatrix(groundTruthMatrix), this.flattenMatrix(simMatrix));
    }

    @Override
    public double run(int[][] groundTruthMatrix, int[][] corrMatrix) {
        double[][] simMatrix = new double[corrMatrix.length][];
        for (int i = 0; i < corrMatrix.length; i++)
            simMatrix[i] = Arrays.stream(corrMatrix[i]).asDoubleStream().toArray();
        return this.run(groundTruthMatrix, simMatrix);
    }

    @Override
    public double run(int[] groundTruthVector, int[] corrVector) {
        double[] simVector = Arrays.stream(corrVector).asDoubleStream().toArray();
        return this.run(groundTruthVector, simVector);
    }

    protected double[] flattenMatrix(double[][] m) {
        return Arrays.stream(m).flatMapToDouble(Arrays::stream).toArray();
    }

    public int[] flattenMatrix(int[][] m) {
        return Arrays.stream(m).flatMapToInt(Arrays::stream).toArray();
    }

    // inspired by https://github.com/deeplearning4j/deeplearning4j/blob/f9c1faaaf968d6f0e5a5add2627908f7a2565f96/nd4j/nd4j-backends/nd4j-api-parent/nd4j-api/src/main/java/org/nd4j/evaluation/curves/BaseCurve.java#L60
    protected double calcAreaUnderCurve(double[] x, double[] y) {
        double auc = 0;

        for (int i = 0; i < x.length - 1; i++) {
            double xLeft = x[i];
            double xRight = x[i+1];
            double yLeft = y[i];
            double yRight = y[i+1];

            double deltaX = Math.abs(xRight - xLeft);
            double avg = (yLeft + yRight) / 2;

            auc += deltaX * avg;
        }

        return auc;
    }
}
