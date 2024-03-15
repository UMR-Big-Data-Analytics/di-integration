package de.di.similarity_measures;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LevenshteinTest {

    @Test
    public void testCorrectness() {

        Levenshtein levenshtein = new Levenshtein(false);
        float result = levenshtein.calculate("Big Data Systems", "Data Integration");
        assertEquals(0, result, 0.000001);
    }
}
