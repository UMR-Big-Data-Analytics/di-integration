package de.di.data_profiling;

import de.di.Relation;
import de.di.data_profiling.structures.IND;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class INDProfilerTest {

    @Test
    public void testCorrectness() {
        INDProfiler profiler = new INDProfiler();
        List<Relation> relations = new ArrayList<>();
        relations.add(new Relation("data" + File.separator + "data_profiling" + File.separator + "abcde.csv"));
        relations.add(new Relation("data" + File.separator + "data_profiling" + File.separator + "abcdefghi.csv"));
        relations.add(new Relation("data" + File.separator + "data_profiling" + File.separator + "tpch_nation.csv"));
        relations.add(new Relation("data" + File.separator + "data_profiling" + File.separator + "tpch_region.csv"));
        relations.add(new Relation("data" + File.separator + "data_profiling" + File.separator + "tpch_supplier.csv"));

        List<IND> inds = profiler.profile(relations, false);
        assertEquals(211, inds.size());
    }
}
