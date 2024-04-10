package de.di.duplicate_detection.structures;

import de.di.similarity_measures.SimilarityMeasure;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AttrSimWeight {

    // The attribute index that is the target of the similarity calculation.
    private final int attribute;

    // The similarity measure that should be applied for this attribute.
    private final SimilarityMeasure similarityMeasure;

    // The weight that shall be assigned to the calculated similarity.
    private final double weight;
}
