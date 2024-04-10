package de.di.similarity_measures.helper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinHashTest {

    @Test
    public void testMinHashing() {
        MinHash minHash = null;
        String result = null;

        minHash = new MinHash(0);
        result = minHash.hash(new String[]{"fgh", "bcd", "abc", "cde", "def"});
        assertEquals("abc", result);

        minHash = new MinHash(1);
        result = minHash.hash(new String[]{"abc", "bca", "bac", "xyz", "uvw"});
        assertEquals("bac", result);

        minHash = new MinHash(2);
        result = minHash.hash(new String[]{"abc", "bca", "bac", "xyz", "bca"});
        assertEquals("bca", result);
    }
}
