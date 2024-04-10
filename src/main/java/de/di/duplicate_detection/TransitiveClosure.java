package de.di.duplicate_detection;

import de.di.Relation;
import de.di.duplicate_detection.structures.Duplicate;

import java.util.HashSet;
import java.util.Set;

public class TransitiveClosure {

    /**
     * Calculates the transitive close over the provided set of duplicates. The result of the transitive closure
     * calculation are all input duplicates together with all additional duplicates that follow from the input
     * duplicates via transitive inference. For example, if (1,2) and (2,3) are two input duplicates, the algorithm
     * adds the transitive duplicate (1,3). Note that the duplicate relationship is commutative, i.e., (1,2) and (2,1)
     * both describe the same duplicate. The algorithm does not add identity duplicates, such as (1,1).
     * @param duplicates The duplicates over which the transitive closure is to be calculated.
     * @return The input set of duplicates with all transitively inferrable additional duplicates.
     */
    public Set<Duplicate> calculate(Set<Duplicate> duplicates) {
        Set<Duplicate> closedDuplicates = new HashSet<>(2 * duplicates.size());

        if (duplicates.size() <= 1)
            return duplicates;

        Relation relation = duplicates.iterator().next().getRelation();
        int numRecords = relation.getRecords().length;

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //                                      DATA INTEGRATION ASSIGNMENT                                           //
        // Calculate the transitive closure over the provided attributes using Warshall's (or Warren's) algorithm.    //



        //                                                                                                            //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        return closedDuplicates;
    }
}
