package de.di.schema_matching;

import de.di.Relation;
import de.di.schema_matching.structures.SimilarityMatrix;
import de.di.similarity_measures.Jaccard;
import de.di.similarity_measures.helper.Tokenizer;

public class FirstLineSchemaMatcher {

    /**
     * Matches the attributes of the source and target table and produces a #source_attributes x #target_attributes
     * sized similarity matrix that represents the attribute-to-attribute similarities of the two relations.
     * @param sourceRelation The first relation for the matching that determines the first (= y) dimension of the
     *                       similarity matrix, i.e., double[*][].
     * @param targetRelation The second relation for the matching that determines the second (= x) dimension of the
     *                       similarity matrix, i.e., double[][*].
     * @return The similarity matrix that describes the attribute-to-attribute similarities of the two relations.
     */
    public SimilarityMatrix match(Relation sourceRelation, Relation targetRelation) {
        String[][] sourceColumns = sourceRelation.getColumns();
        String[][] targetColumns = targetRelation.getColumns();

        // Initialize the similarity matrix
        double[][] matrix = new double[sourceColumns.length][];
        for (int i = 0; i < sourceColumns.length; i++)
            matrix[i] = new double[targetColumns.length];

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //                                      DATA INTEGRATION ASSIGNMENT                                           //
        // Calculate all pair-wise attribute similarities of the two relations and store the result in a similarity   //
        // matrix. A naive Jaccard-based implementation will complete the task, but with the already implemented      //
        // further similarity measures, the data profiling algorithms and a clever matching strategy, much better     //
        // matching results are possible!                                                                             //



        //                                                                                                            //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        return new SimilarityMatrix(matrix, sourceRelation, targetRelation);
    }
}
