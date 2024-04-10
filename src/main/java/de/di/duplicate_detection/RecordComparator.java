package de.di.duplicate_detection;

import de.di.duplicate_detection.structures.AttrSimWeight;
import de.di.similarity_measures.SimilarityMeasure;

import java.util.List;
import java.util.stream.Collectors;

public class RecordComparator {

    // A list of (attribute,similarityMeasure,weight) triples. Each triple assigns a similarity measure to certain
    // attribute. For example, the triple "(0,Levenshtein,0.2)" specifies that the attribute with index 0 should be
    // compared with the Levenshtein algorithm and receives a weight of 20% among the other AttrSimWeight triples.
    private List<AttrSimWeight> attrSimWeights;

    // A similarity threshold that decides whether a record pair with a certain similarity is considered a duplicate
    // or not. The threshold should fit the attrSimWeights-based similarity scoring of this RecordComparator;
    private double threshold;

    public RecordComparator(List<AttrSimWeight> attrSimWeights, double threshold) {
        this.attrSimWeights = this.normalize(attrSimWeights);
        this.threshold = threshold;
    }

    /**
     * Normalizes the weights of the AttrSimWeight objects such that they sum to 1.
     * @param attrSimWeights The AttrSimWeight objects to be normalized.
     * @return The normalized AttrSimWeight objects, whose weights sum to 1.
     */
    private List<AttrSimWeight> normalize(List<AttrSimWeight> attrSimWeights) {
        double correction = 1 / attrSimWeights.stream()
                .map(AttrSimWeight::getWeight)
                .mapToDouble(Double::doubleValue)
                .sum();
        return attrSimWeights.stream()
                .map(a -> new AttrSimWeight(a.getAttribute(), a.getSimilarityMeasure(), a.getWeight() * correction))
                .collect(Collectors.toList());
    }

    /**
     * Compares the two provided tuples with the internal similarity measures. The resulting similarity of tuple1 and
     * tuple2 is the weighted average of all value similarities calculated with the internal similarity measures. The
     * internal AttrSimWeight objects specify, which attributes are compared with which similarity measure and what
     * weights are given to the calculated similarities.
     * @param tuple1 The first tuple for the comparison.
     * @param tuple2 The second tuple for the comparison.
     * @return The similarity of the two tuples w.r.t. the internal similarity measures.
     */
    public double compare(String[] tuple1, String[] tuple2) {
        double recordSimilarity = 0;

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //                                      DATA INTEGRATION ASSIGNMENT                                           //
        // Compare the two tuples with the similarity functions specified by the internal AttrSimWeight objects.      //
        // To calculate the overall tuple similarity, calculate the weighted average similarity of all individual     //
        // attribute similarities; the weights are also stored in the internal AttrSimWeight objects.                 //



        //                                                                                                            //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        return recordSimilarity;
    }

    /**
     * Decides if the provided similarity is higher than the internal similarity threshold and, therefore,
     * characterizes a duplicate.
     * @param similarity The similarity that is to be checked.
     * @return true if the similarity is higher than the internal similarity threshold.
     */
    public boolean isDuplicate(double similarity) {
        return similarity > this.threshold;
    }
}
