package de.di.similarity_measures.helper;

import lombok.Getter;

@Getter
public class Tokenizer {

    // The symbol that should be used for padding, if "usePadding == true"
    public static String paddingSymbol = "$";

    // The size of the tokens that the tokenizer should use
    private final int tokenSize;

    // The choice of whether padding should be used for the tokenization
    private final boolean usePadding;

    public Tokenizer(final int tokenSize, final boolean usePadding) {
        this.tokenSize = tokenSize;
        this.usePadding = usePadding;
    }

    /**
     * Tokenizes the input string into tokens of lengths this.tokenSize.
     * If "this.usePadding == true", the input strings are padded with "this.tokenSize - 1" paddingSymbols.
     * @param string The string argument that should be tokenized.
     * @return The tokenized argument.
     */
    public String[] tokenize(final String string) {
        String s = string;
        if (this.usePadding) {
            String padding = paddingSymbol.repeat(Math.max(0, this.tokenSize - 1));
            s = padding + s + padding;
        }

        if (s.length() < this.tokenSize)
            return new String[0];

        int numTokens = s.length() - (this.tokenSize - 1);
        String[] tokens = new String[numTokens];
        for (int i = 0; i < numTokens; i++)
            tokens[i] = s.substring(i, i + this.tokenSize);
        return tokens;
    }
}
