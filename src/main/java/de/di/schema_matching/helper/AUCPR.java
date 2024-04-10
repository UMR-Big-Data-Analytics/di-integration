package de.di.schema_matching.helper;

import it.unimi.dsi.fastutil.ints.IntList;

import java.util.Arrays;

public class AUCPR extends AUCMetric {

    @Override
    public double run(int[] groundTruthVector, double[] simVector) {
        IntList sortedSimIndices = MetricUtils.getSortedSimIndices(simVector, groundTruthVector);
        IntList groundTruthIndices = MetricUtils.getGroundTruthIndices(groundTruthVector);

        int n = groundTruthVector.length;
        int numPositives = groundTruthIndices.size();

        int numTP = 0;
        int numFP = 0;
        int numFN = numPositives;

        int numThresholds = 1 + (int) Arrays.stream(simVector).distinct().count(); // first threshold is inf

        double[] precision = new double[numThresholds];
        double[] recall = new double[numThresholds];
        // start ROC at (0,0) for first threshold (inf)
        precision[0] = 1;
        recall[0] = 0;

        double currThreshold = simVector[sortedSimIndices.getInt(0)];
        int currThresholdIdx = 1;
        // iterate over sim values in descending order and group them by equality (=same threshold)
        for (int i = 0; i < n; i++) {
            double currSimValue = simVector[sortedSimIndices.getInt(i)];
            if (currSimValue != currThreshold) {
                precision[currThresholdIdx] = numTP > 0 ? (double) numTP / (numTP + numFP) : 0;
                recall[currThresholdIdx] = numTP > 0 ? (double) numTP / (numTP + numFN) : 0;
                currThresholdIdx += 1;
                currThreshold = currSimValue;
            }
            if (groundTruthIndices.contains(sortedSimIndices.getInt(i))) {
                numTP += 1;
                numFN -= 1;
            } else {
                numFP += 1;
            }
        }
        precision[currThresholdIdx] = numTP > 0 ? (double) numTP / (numTP + numFP) : 0;
        recall[currThresholdIdx] = numTP > 0 ? (double) numTP / (numTP + numFN) : 0;

        return calcAreaUnderCurve(recall, precision);
    }
}
