package de.di.similarity_measures.helper;

import it.unimi.dsi.fastutil.chars.Char2IntAVLTreeMap;
import it.unimi.dsi.fastutil.chars.Char2IntMap;

import java.util.Comparator;
import java.util.Random;
import java.util.stream.Stream;

public class MinHash {

    // The comparator for the MinHashing; the comparator defines the sortation for this token-based minhash function
    private final Comparator<String> comparator;

    public MinHash(long seed) {
        // Create universe off all ASCI chars
        char[] universe = new char[128];
        for (int i = 0; i < 128; i++)
            universe[i] = (char) i;

        // Randomly shuffle the universe
        Random random = new Random(seed);
        for (int i = 0; i < 128; i++) {
            int randomIndexToSwap = random.nextInt(universe.length);
            char temp = universe[randomIndexToSwap];
            universe[randomIndexToSwap] = universe[i];
            universe[i] = temp;
        }

        // Hash chars to positions in universe for faster sorting
        Char2IntMap charIndex = new Char2IntAVLTreeMap();
        for (int i = 0; i < 128; i++)
            charIndex.put(universe[i], i);

        // Create a comparator for our randomized universe
        this.comparator = this.createComparatorFor(charIndex);
    }

    public String hash(final String[] strings) {
        if (strings.length == 0)
            return "";

        return Stream.of(strings)
                .min(this.comparator)
                .orElseThrow();
    }

    private Comparator<String> createComparatorFor(Char2IntMap charIndex) {
        return (o1, o2) -> {
            if (o1.equals(o2))
                return 0;

            // Compare strings character-wise w.r.t. our universe
            for (int i = 0; i < Math.min(o1.length(), o2.length()); i++) {
                int index1 = charIndex.get(o1.charAt(i));
                int index2 = charIndex.get(o2.charAt(i));
                if (index1 != index2)
                    return index1 - index2;
            }

            // If the strings have same prefixes, take the longer one as minimum;
            // this is different to compareTo() semantics, but avoids having "" always as the smallest value
            return o2.length() - o1.length();
        };
    }
}