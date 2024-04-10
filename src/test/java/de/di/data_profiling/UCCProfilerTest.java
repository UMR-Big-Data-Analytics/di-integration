package de.di.data_profiling;

import de.di.Relation;
import de.di.data_profiling.structures.AttributeList;
import de.di.data_profiling.structures.UCC;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UCCProfilerTest {

    @Test
    public void testCorrectness_abcde() {
        UCCProfiler profiler = new UCCProfiler();
        Relation relation = new Relation("data" + File.separator + "data_profiling" + File.separator + "abcde.csv");

        List<UCC> uccs = profiler.profile(relation);
        assertEquals(5, uccs.size());

        List<UCC> expectedUccs = new ArrayList<>(5);
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{0, 1})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{0, 2, 4})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{1, 2, 4})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{1, 3, 4})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{2, 3, 4})));
        assertEquals(expectedUccs, uccs);
    }

    @Test
    public void testCorrectness_abcdefghi() {
        UCCProfiler profiler = new UCCProfiler();
        Relation relation = new Relation("data" + File.separator + "data_profiling" + File.separator + "abcdefghi.csv");

        List<UCC> uccs = profiler.profile(relation);
        assertEquals(20, uccs.size());

        List<UCC> expectedUccs = new ArrayList<>(20);
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{0, 4, 5})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{0, 5, 8})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{3, 4, 5})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{4, 5, 8})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{0, 1, 6, 8})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{0, 2, 6, 8})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{0, 6, 7, 8})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{1, 2, 4, 5})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{1, 4, 5, 6})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{1, 4, 5, 7})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{1, 4, 6, 8})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{2, 4, 5, 6})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{2, 4, 6, 8})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{3, 5, 7, 8})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{4, 6, 7, 8})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{1, 2, 5, 7, 8})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{1, 2, 6, 7, 8})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{1, 3, 6, 7, 8})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{1, 5, 6, 7, 8})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{2, 3, 6, 7, 8})));
        assertEquals(expectedUccs, uccs);
    }

    @Test
    public void testCorrectness_tpch_nation() {
        UCCProfiler profiler = new UCCProfiler();
        Relation relation = new Relation("data" + File.separator + "data_profiling" + File.separator + "tpch_nation.csv");

        List<UCC> uccs = profiler.profile(relation);
        assertEquals(3, uccs.size());

        List<UCC> expectedUccs = new ArrayList<>(3);
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{0})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{1})));
        expectedUccs.add(new UCC(relation, new AttributeList(new int[]{3})));
        assertEquals(expectedUccs, uccs);
    }
}
