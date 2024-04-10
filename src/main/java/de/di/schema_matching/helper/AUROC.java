package de.di.schema_matching.helper;

import it.unimi.dsi.fastutil.ints.IntList;

import java.util.*;

public class AUROC extends AUCMetric {

    @Override
    public double run(int[] groundTruthVector, double[] simVector) {
        IntList sortedSimIndices = MetricUtils.getSortedSimIndices(simVector, groundTruthVector);
        IntList groundTruthIndices = MetricUtils.getGroundTruthIndices(groundTruthVector);

        int n = groundTruthVector.length;
        int numPositives = groundTruthIndices.size();
        int numNegatives = n - numPositives;

        int numTP = 0;
        int numFP = 0;

        int numThresholds = 1 + (int) Arrays.stream(simVector).distinct().count(); // first threshold is inf

        double[] tpr = new double[numThresholds];
        double[] fpr = new double[numThresholds];
        // start ROC at (0,0) for first threshold (inf)
        tpr[0] = 0;
        fpr[0] = 0;

        double currThreshold = simVector[sortedSimIndices.getInt(0)];
        int currThresholdIdx = 1;
        // iterate over sim values in descending order and group them by equality (=same threshold)
        for (int i = 0; i < n; i++) {
            double currSimValue = simVector[sortedSimIndices.getInt(i)];
            if (currSimValue != currThreshold) {
                tpr[currThresholdIdx] = (double) numTP / numPositives;
                fpr[currThresholdIdx] = (double) numFP / numNegatives;
                currThresholdIdx += 1;
                currThreshold = currSimValue;
            }
            if (groundTruthIndices.contains(sortedSimIndices.getInt(i))) {
                numTP += 1;
            } else {
                numFP += 1;
            }
        }
        tpr[currThresholdIdx] = (double) numTP / numPositives;
        fpr[currThresholdIdx] = (double) numFP / numNegatives;

        return (float) calcAreaUnderCurve(fpr, tpr);
    }
}
