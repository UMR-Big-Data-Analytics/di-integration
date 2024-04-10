package de.di.similarity_measures.helper;

import java.util.Comparator;
import java.util.stream.Stream;

public class MinHash {

    // The comparator for the MinHashing; the comparator defines the sortation for this token-based minhash function
    private final Comparator<String> comparator;

    public MinHash(int sortByPosition) {
        this.comparator = this.createComparatorFor(sortByPosition);
    }

    public String hash(final String[] strings) {
        if (strings.length == 0)
            return "";

        return Stream.of(strings)
                .min(this.comparator)
                .orElseThrow();
    }

    private Comparator<String> createComparatorFor(int sortByPosition) {
        return (o1, o2) -> {
            int i = sortByPosition;
            int charComparison = 0;
            while (true) {
                if ((o1.length() < i + 1) && (o2.length() < i + 1))
                    return o1.compareTo(o2);
                if (o1.length() < i + 1)
                    return -1;
                if (o2.length() < i + 1)
                    return 1;
                charComparison = Character.compare(o1.charAt(i), o2.charAt(i));
                if (charComparison != 0)
                    return charComparison;
                i++;
            }
        };
    }
}