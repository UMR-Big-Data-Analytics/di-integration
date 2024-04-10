package de.di.similarity_measures;

import de.di.similarity_measures.helper.Tokenizer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocalitySensitiveHashingTest {

    @Test
    public void testCorrectnessBag() {
        LocalitySensitiveHashing localitySensitiveHashing = null;
        double result = 0;

        localitySensitiveHashing = new LocalitySensitiveHashing(new Tokenizer(2, false), true, 2);
        result = localitySensitiveHashing.calculate("Big Data Systems", "Data Integration");
        assertEquals((double) 1 / 4, result, 0.000001);

        localitySensitiveHashing = new LocalitySensitiveHashing(new Tokenizer(2, false), true, 2);
        result = localitySensitiveHashing.calculate(new String[]{"aa", "ba", "ab", "ba", "cb", "cb", "da"}, new String[]{"aa", "bb", "ab", "ba", "cb", "fa", "eb"});
        assertEquals((double) 2 / 4, result, 0.000001);
    }

    @Test
    public void testCorrectnessSet() {
        LocalitySensitiveHashing localitySensitiveHashing = null;
        double result = 0;

        localitySensitiveHashing = new LocalitySensitiveHashing(new Tokenizer(2, false), false, 2);
        result = localitySensitiveHashing.calculate("Big Data Systems", "Data Integration");
        assertEquals((double) 1 / 3, result, 0.000001);

        localitySensitiveHashing = new LocalitySensitiveHashing(new Tokenizer(2, false), false, 2);
        result = localitySensitiveHashing.calculate(new String[]{"aa", "ba", "ab", "ba", "cb", "cb", "da"}, new String[]{"aa", "bb", "ab", "ba", "cb", "fa", "eb"});
        assertEquals((double) 1 / 1, result, 0.000001);
    }

    @Test
    public void testNull() {
        LocalitySensitiveHashing localitySensitiveHashing = null;
        double result = 0;

        localitySensitiveHashing = new LocalitySensitiveHashing(new Tokenizer(2, false), false, 2);
        result = localitySensitiveHashing.calculate("", "Data Integration");
        assertEquals((double) 0 / 3, result, 0.000001);

        localitySensitiveHashing = new LocalitySensitiveHashing(new Tokenizer(2, false), false, 2);
        result = localitySensitiveHashing.calculate(new String[]{"aa", "", "ab", "ba", "cb", "", "da"}, new String[]{"", "bb", "ab", "ba", "cb", "fa", "eb"});
        assertEquals((double) 1 / 1, result, 0.000001);
    }
}
