package de.di.duplicate_detection;

import de.di.Relation;
import de.di.duplicate_detection.structures.AttrSimWeight;
import de.di.duplicate_detection.structures.Duplicate;
import de.di.similarity_measures.Jaccard;
import de.di.similarity_measures.Levenshtein;
import de.di.similarity_measures.helper.Tokenizer;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class SortedNeighborhoodTest {

    @Test
    public void testCorrectness_cd() {
        Relation cd = new Relation("data" + File.separator + "duplicate_detection" + File.separator + "cd.csv", true, ';', StandardCharsets.ISO_8859_1);
        Relation cdGold = new Relation("data" + File.separator + "duplicate_detection" + File.separator + "cd_gold.csv");

        List<Duplicate> duplicatesGold = parseCDGoldstandard(cdGold, cd);

        List<AttrSimWeight> attrSimWeights = new ArrayList<>(5);
        attrSimWeights.add(new AttrSimWeight(1, new Levenshtein(true), 0.1));
        attrSimWeights.add(new AttrSimWeight(2, new Levenshtein(true), 0.1));
        attrSimWeights.add(new AttrSimWeight(3, new Jaccard(new Tokenizer(3, true), false), 0.2));
        attrSimWeights.add(new AttrSimWeight(4, new Levenshtein(true), 0.1));
        attrSimWeights.add(new AttrSimWeight(5, new Levenshtein(true), 0.1));
        attrSimWeights.add(new AttrSimWeight(6, new Jaccard(new Tokenizer(3, true), false), 0.1));
        attrSimWeights.add(new AttrSimWeight(7, new Levenshtein(true), 0.1));
        attrSimWeights.add(new AttrSimWeight(8, new Levenshtein(true), 0.2));
        RecordComparator recordComparator = new RecordComparator(attrSimWeights, 0.8);

        SortedNeighborhood snm = new SortedNeighborhood();
        Set<Duplicate> duplicatesDetected = snm.detectDuplicates(cd, new int[]{1,2,3,5,7,9}, 5, recordComparator);
        assertEquals(42, duplicatesDetected.size());

        duplicatesDetected.retainAll(duplicatesGold);
        assertEquals(40, duplicatesDetected.size());
    }

    private List<Duplicate> parseCDGoldstandard(Relation cdGold, Relation cd) {
        List<Duplicate> duplicatesGold = new ArrayList<>(cdGold.getRecords().length);
        for (String[] record : cdGold.getRecords()) {
            IntList indexes = new IntArrayList(2);
            for (int i = 0; i < cd.getRecords().length; i++) {
                if (cd.getRecords()[i][0].equals(record[0]) || cd.getRecords()[i][0].equals(record[1])) {
                    indexes.add(i);
                    if (indexes.size() == 2)
                        break;
                }
            }
            duplicatesGold.add(new Duplicate(indexes.getInt(0), indexes.getInt(1), 1.0, cd));
        }
        return duplicatesGold;
    }
}
