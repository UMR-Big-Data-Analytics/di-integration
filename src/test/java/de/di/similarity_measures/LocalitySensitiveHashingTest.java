package de.di.similarity_measures;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocalitySensitiveHashingTest {

    @Test
    public void testCorrectness() {

        LocalitySensitiveHashing localitySensitiveHashing = new LocalitySensitiveHashing(2, false, 2);
        float result = localitySensitiveHashing.calculate("Big Data Systems", "Data Integration");
        assertEquals(0, result, 0.000001);
    }
}
