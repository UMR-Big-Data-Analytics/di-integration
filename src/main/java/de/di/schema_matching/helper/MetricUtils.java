package de.di.schema_matching.helper;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import java.util.Arrays;

public class MetricUtils {

    /**
     * @param simVector Similarity vector to sort descendingly
     * @param groundTruthVector Ground truth information used as tie-breaker for equal sim scores
     * @return List of indices sorted in descending order of similarity values in {@code simVector}.
     *      When two values are equal, ground truth works as tie-breaker: negatives are listed before positives.
     */
    public static IntList getSortedSimIndices(double[] simVector, int[] groundTruthVector) {
        IntList res = new IntArrayList();
        IntList gtIndices = getGroundTruthIndices(groundTruthVector);

        // insert all sim indices not in ground truth
        for (int i = 0; i < simVector.length; i++) {
            if (!gtIndices.contains(i)) {
                res.add(i);
            }
        }

        res.sort((a,b) -> Double.compare(simVector[b], simVector[a]));

        // add all sim indices which are in ground truth
        for (int id : gtIndices) {
            double currSimScore = simVector[id];
            int currResSize = res.size();
            boolean inserted = false;
            for (int i = 0; i < currResSize; i++) {
                if (simVector[res.get(i)] < currSimScore) {
                    res.add(i, id);
                    inserted = true;
                    break;
                }
            }
            if (!inserted) { // add to end
                res.add(id);
            }
        }

        return res;
    }

    /**
     * @param groundTruthVector Ground truth vector to extract indices of positives
     * @return List of indices for which ground truth is true (i.e., ground truth vector has a 1)
     */
    public static IntList getGroundTruthIndices(int[] groundTruthVector) {
        IntList res = new IntArrayList(Arrays.stream(groundTruthVector).sum());

        for (int i = 0; i < groundTruthVector.length; i++) {
            if (groundTruthVector[i] == 1) {
                res.add(i);
            }
        }

        return res;
    }
}