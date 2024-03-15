package de.di.similarity_measures;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JaccardTest {

    @Test
    public void testCorrectness() {

        Jaccard jaccard = new Jaccard(2, false);
        float result = jaccard.calculate("Big Data Systems", "Data Integration");
        assertEquals(0, result, 0.000001);
    }
}
