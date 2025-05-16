package de.di.similarity_measures.helper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinHashTest {

    @Test
    public void testMinHashing() {
        MinHash minHash = new MinHash(100);
        String result = null;

        assertEquals("bcd", minHash.hash(new String[]{"fgh", "bcd", "abc", "cde", "def"}));

        assertEquals("bac", minHash.hash(new String[]{"abc", "bca", "bac", "xyz", "uvw"}));

        assertEquals("bac", minHash.hash(new String[]{"abc", "bca", "bac", "xyz", "bca"}));
    }
}
