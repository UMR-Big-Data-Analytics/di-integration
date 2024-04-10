package de.di.duplicate_detection;

import de.di.duplicate_detection.structures.AttrSimWeight;
import de.di.similarity_measures.Jaccard;
import de.di.similarity_measures.Levenshtein;
import de.di.similarity_measures.helper.Tokenizer;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RecordComparatorTest {

    private final String[] tuple1 = new String[]{"Data Integration", "English", "6 ECTS", "Philipps University Marburg", "Similarity Measures, Data Profiling, Schema Matching, Duplicate Detection"};
    private final String[] tuple2 = new String[]{"Datenintegration", "German", "9 ECTS", "University of Potsdam", "similartiy_measurse, dataa_prufiling, schema_matsching, duplicate_erkennung"};

    @Test
    public void testCorrectnessLevenshtein() {
        List<AttrSimWeight> attrSimWeights = new ArrayList<>(5);
        attrSimWeights.add(new AttrSimWeight(0, new Levenshtein(true), 1.0/5));
        attrSimWeights.add(new AttrSimWeight(1, new Levenshtein(true), 1.0/5));
        attrSimWeights.add(new AttrSimWeight(2, new Levenshtein(false), 1.0/5));
        attrSimWeights.add(new AttrSimWeight(3, new Levenshtein(true), 1.0/5));
        attrSimWeights.add(new AttrSimWeight(4, new Levenshtein(true), 1.0/5));

        RecordComparator recordComparator = new RecordComparator(attrSimWeights, 1.0);
        double similarity = recordComparator.compare(this.tuple1, this.tuple2);
        assertEquals(0.5244259259259259, similarity, 0.0000000001);
    }

    @Test
    public void testCorrectnessJaccard() {
        List<AttrSimWeight> attrSimWeights = new ArrayList<>(5);
        attrSimWeights.add(new AttrSimWeight(0, new Jaccard(new Tokenizer(2, false), false), 1.0/5));
        attrSimWeights.add(new AttrSimWeight(1, new Jaccard(new Tokenizer(2, false), false), 1.0/5));
        attrSimWeights.add(new AttrSimWeight(2, new Jaccard(new Tokenizer(2, false), false), 1.0/5));
        attrSimWeights.add(new AttrSimWeight(3, new Jaccard(new Tokenizer(3, true), false), 1.0/5));
        attrSimWeights.add(new AttrSimWeight(4, new Jaccard(new Tokenizer(2, false), false), 1.0/5));

        RecordComparator recordComparator = new RecordComparator(attrSimWeights, 1.0);
        double similarity = recordComparator.compare(this.tuple1, this.tuple2);
        assertEquals(0.3572086733650958, similarity, 0.0000000001);
    }

    @Test
    public void testCorrectnessMixed() {
        List<AttrSimWeight> attrSimWeights = new ArrayList<>(5);
        attrSimWeights.add(new AttrSimWeight(0, new Levenshtein(true), 1.0/5));
        attrSimWeights.add(new AttrSimWeight(1, new Jaccard(new Tokenizer(2, false), false), 1.0/5));
        attrSimWeights.add(new AttrSimWeight(2, new Levenshtein(false), 1.0/5));
        attrSimWeights.add(new AttrSimWeight(3, new Jaccard(new Tokenizer(3, true), false), 1.0/5));
        attrSimWeights.add(new AttrSimWeight(4, new Levenshtein(true), 1.0/5));

        RecordComparator recordComparator = new RecordComparator(attrSimWeights, 1.0);
        double similarity = recordComparator.compare(this.tuple1, this.tuple2);
        assertEquals(0.5070271317829458, similarity, 0.0000000001);
    }

    @Test
    public void testCorrectnessMixedWeighted() {
        List<AttrSimWeight> attrSimWeights = new ArrayList<>(5);
        attrSimWeights.add(new AttrSimWeight(0, new Levenshtein(true), 0.4));
        attrSimWeights.add(new AttrSimWeight(1, new Jaccard(new Tokenizer(2, false), false), 0.1));
        attrSimWeights.add(new AttrSimWeight(2, new Levenshtein(false), 0.2));
        attrSimWeights.add(new AttrSimWeight(3, new Jaccard(new Tokenizer(3, true), false), 0.1));
        attrSimWeights.add(new AttrSimWeight(4, new Levenshtein(true), 0.2));

        RecordComparator recordComparator = new RecordComparator(attrSimWeights, 1.0);
        double similarity = recordComparator.compare(this.tuple1, this.tuple2);
        assertEquals(0.6485968992248062, similarity, 0.0000000001);
    }

    @Test
    public void testCorrectnessMixedOverweighted() {
        List<AttrSimWeight> attrSimWeights = new ArrayList<>(5);
        attrSimWeights.add(new AttrSimWeight(0, new Levenshtein(true), 400));
        attrSimWeights.add(new AttrSimWeight(1, new Jaccard(new Tokenizer(2, false), false), 100));
        attrSimWeights.add(new AttrSimWeight(2, new Levenshtein(false), 200));
        attrSimWeights.add(new AttrSimWeight(3, new Jaccard(new Tokenizer(3, true), false), 100));
        attrSimWeights.add(new AttrSimWeight(4, new Levenshtein(true), 200));

        RecordComparator recordComparator = new RecordComparator(attrSimWeights, 1.0);
        double similarity = recordComparator.compare(this.tuple1, this.tuple2);
        assertEquals(0.6485968992248062, similarity, 0.0000000001);
    }
}
