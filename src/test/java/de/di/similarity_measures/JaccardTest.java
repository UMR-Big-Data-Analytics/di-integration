package de.di.similarity_measures;

import de.di.similarity_measures.helper.Tokenizer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JaccardTest {

    @Test
    public void testCorrectnessStringsBag() {
        Jaccard jaccard = null;
        double result = 0;

        jaccard = new Jaccard(new Tokenizer(2, false), true);
        result = jaccard.calculate("VL Big Data Systems 2020", "VL Big Data Integration 2022");
        assertEquals((double) 15 / 50, result, 0.000001);

        jaccard = new Jaccard(new Tokenizer(4, true), true);
        result = jaccard.calculate("Tim Tim Tina", "Tina Tim Tim");
        assertEquals((double) 7 / 30, result, 0.000001);
    }

    @Test
    public void testCorrectnessStringsSet() {
        Jaccard jaccard = null;
        double result = 0;

        jaccard = new Jaccard(new Tokenizer(2, false), false);
        result = jaccard.calculate("VL Big Data Systems 2020", "VL Big Data Integration 2022");
        assertEquals((double) 15 / 33, result, 0.000001);

        jaccard = new Jaccard(new Tokenizer(4, true), false);
        result = jaccard.calculate("Tim Tim Tina", "Tina Tim Tim");
        assertEquals((double) 7 / 19, result, 0.000001);
    }

    @Test
    public void testCorrectnessStringListsBag() {
        Jaccard jaccard = null;
        double result = 0;

        jaccard = new Jaccard(new Tokenizer(2, false), true);
        result = jaccard.calculate(new String[]{"a", "b", "a", "b", "c", "c", "d"}, new String[]{"a", "b", "a", "b", "c", "f", "e"});
        assertEquals((double) 5 / 14, result, 0.000001);

        jaccard = new Jaccard(new Tokenizer(2, true), true);
        result = jaccard.calculate(new String[]{"a", "b", "a", "b", "c", "c", "d"}, new String[]{"a", "b", "a", "b", "c", "f", "e"});
        assertEquals((double) 5 / 14, result, 0.000001);
    }

    @Test
    public void testCorrectnessStringListsSet() {
        Jaccard jaccard = null;
        double result = 0;

        jaccard = new Jaccard(new Tokenizer(2, false), false);
        result = jaccard.calculate(new String[]{"a", "b", "a", "b", "c", "c", "d"}, new String[]{"a", "b", "a", "b", "c", "f", "e"});
        assertEquals((double) 3 / 6, result, 0.000001);

        jaccard = new Jaccard(new Tokenizer(2, true), false);
        result = jaccard.calculate(new String[]{"a", "b", "a", "b", "c", "c", "d"}, new String[]{"a", "b", "a", "b", "c", "f", "e"});
        assertEquals((double) 3 / 6, result, 0.000001);
    }

    @Test
    public void testNull() {
        Jaccard jaccard = null;
        double result = 0;

        jaccard = new Jaccard(new Tokenizer(4, true), false);
        result = jaccard.calculate("", "Tina Tim Tim");
        assertEquals(0, result, 0.000001);

        jaccard = new Jaccard(new Tokenizer(2, true), false);
        result = jaccard.calculate(new String[]{"a", "", "a", "b", "c", "", "d"}, new String[]{"a", "b", "", "b", "c", "f", ""});
        assertEquals((double) 4 / 6, result, 0.000001);
    }
}
