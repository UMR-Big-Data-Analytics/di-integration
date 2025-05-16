package de.di.similarity_measures;

import de.di.similarity_measures.helper.Tokenizer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LocalitySensitiveHashingTest {

    @Test
    public void testCorrectnessStrings() {
        LocalitySensitiveHashing lsh = new LocalitySensitiveHashing(new Tokenizer(2, true), 20);;
        double result = 0;

        result = lsh.calculate("Big Data Systems", "Data Integration");
        assertEquals((double) 6 / 20, result, 0.000001);
    }

    @Test
    public void testCorrectnessStringLists() {
        LocalitySensitiveHashing lsh = new LocalitySensitiveHashing(new Tokenizer(2, true), 20);;
        double result = 0;

        result = lsh.calculate(new String[]{"aa", "ba", "ab", "ba", "cb", "cb", "da", "tt", "at"}, new String[]{"aa", "bb", "ab", "ba", "cb", "fa", "eb", "tt", "at"});
        assertEquals((double) 9 / 20, result, 0.000001);
    }

    @Test
    public void testNull() {
        LocalitySensitiveHashing lsh = new LocalitySensitiveHashing(new Tokenizer(2, true), 20);;
        double result = 0;

        result = lsh.calculate("", "Data Integration");
        assertEquals((double) 0 / 20, result, 0.000001);

        result = lsh.calculate(new String[]{"aa", "", "ab", "ba", "cb", "", "da", "tt", "at"}, new String[]{"", "bb", "ab", "ba", "cb", "fa", "eb", "tt", "at"});
        assertEquals((double) 4 / 20, result, 0.000001);
    }
}
