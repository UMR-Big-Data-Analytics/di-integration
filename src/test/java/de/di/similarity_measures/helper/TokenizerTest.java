package de.di.similarity_measures.helper;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TokenizerTest {

    @Test
    public void testTokenizationWithoutPadding() {
        Tokenizer tokenizer = null;
        String[] result, test = null;

        tokenizer = new Tokenizer(3, false);
        result = tokenizer.tokenize("Integration");
        assertEquals(9, result.length);
        test = new String[]{"Int", "nte", "teg", "egr", "gra", "rat", "ati", "tio", "ion"};
        assertArrayEquals(test, result);

        tokenizer = new Tokenizer(2, false);
        result = tokenizer.tokenize("Big Data Systems");
        assertEquals(15, result.length);
        test = new String[]{"Bi", "ig", "g ", " D", "Da", "at", "ta", "a ", " S", "Sy", "ys", "st", "te", "em", "ms"};
        assertArrayEquals(test, result);
    }

    @Test
    public void testTokenizationWithPadding() {
        Tokenizer tokenizer = null;
        String[] result, test = null;
        String p = Tokenizer.paddingSymbol;

        tokenizer = new Tokenizer(3, true);
        result = tokenizer.tokenize("Integration");
        assertEquals(13, result.length);
        test = new String[]{p + p + "I", p + "In", "Int", "nte", "teg", "egr", "gra", "rat", "ati", "tio", "ion", "on" + p, "n" + p + p};
        assertArrayEquals(test, result);

        tokenizer = new Tokenizer(2, true);
        result = tokenizer.tokenize("Big Data Systems");
        assertEquals(17, result.length);
        test = new String[]{p + "B", "Bi", "ig", "g ", " D", "Da", "at", "ta", "a ", " S", "Sy", "ys", "st", "te", "em", "ms", "s" + p};
        assertArrayEquals(test, result);
    }

    @Test
    public void testTooShortString() {
        Tokenizer tokenizer = null;
        String[] result, test = null;
        String p = Tokenizer.paddingSymbol;

        tokenizer = new Tokenizer(4, false);
        result = tokenizer.tokenize("Int");
        test = new String[0];
        assertArrayEquals(test, result);

        tokenizer = new Tokenizer(2, false);
        result = tokenizer.tokenize("");
        test = new String[0];
        assertArrayEquals(test, result);

        tokenizer = new Tokenizer(2, true);
        result = tokenizer.tokenize("I");
        test = new String[]{p + "I", "I" + p};
        assertArrayEquals(test, result);

        tokenizer = new Tokenizer(2, true);
        result = tokenizer.tokenize("");
        test = new String[]{p + p};
        assertArrayEquals(test, result);

        tokenizer = new Tokenizer(3, true);
        result = tokenizer.tokenize("");
        test = new String[]{p + p + p, p + p + p};
        assertArrayEquals(test, result);
    }
}
