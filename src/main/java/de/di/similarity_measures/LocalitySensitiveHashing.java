package de.di.similarity_measures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LocalitySensitiveHashing implements SimilarityMeasure {

    private int tokenSize;
    private boolean usePadding;
    private List<Comparator> comparators;

    public LocalitySensitiveHashing(int tokenSize, boolean usePadding, int numHashFunctions) {
        this.tokenSize = tokenSize;
        this.usePadding = usePadding;
        this.comparators = new ArrayList<>(numHashFunctions);
        for (int i = 0; i < numHashFunctions; i++)
            this.comparators.add(this.createComparatorFor(i));
    }

    @Override
    public float calculate(String string1, String string2) {
        return 0;
    }

    @Override
    public float calculate(String[] strings1, String[] strings2) {
        return 0;
    }

    private Comparator<String> createComparatorFor(int index) {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int i = index;
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
            }
        };
    }
}
