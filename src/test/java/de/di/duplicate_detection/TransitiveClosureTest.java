package de.di.duplicate_detection;

import de.di.Relation;
import de.di.duplicate_detection.structures.Duplicate;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class TransitiveClosureTest {

    @Test
    public void testCorrectness() {
        Relation fakeRelation = new Relation("fakeName", new String[]{"fakeAttribute"}, new String[10][]);

        Set<Duplicate> duplicates = new HashSet<>();
        duplicates.add(new Duplicate(1, 2, 1.0, fakeRelation));
        duplicates.add(new Duplicate(2, 3, 1.0, fakeRelation));
        duplicates.add(new Duplicate(3, 4, 1.0, fakeRelation));
        duplicates.add(new Duplicate(5, 6, 1.0, fakeRelation));
        duplicates.add(new Duplicate(7, 5, 1.0, fakeRelation));

        TransitiveClosure transitiveClosure = new TransitiveClosure();
        Set<Duplicate> result = transitiveClosure.calculate(duplicates);

        Set<Duplicate> expected = new HashSet<>();
        expected.add(new Duplicate(1, 2, 1.0, fakeRelation));
        expected.add(new Duplicate(1, 3, 1.0, fakeRelation));
        expected.add(new Duplicate(1, 4, 1.0, fakeRelation));
        expected.add(new Duplicate(2, 3, 1.0, fakeRelation));
        expected.add(new Duplicate(2, 4, 1.0, fakeRelation));
        expected.add(new Duplicate(3, 4, 1.0, fakeRelation));
        expected.add(new Duplicate(5, 6, 1.0, fakeRelation));
        expected.add(new Duplicate(5, 7, 1.0, fakeRelation));
        expected.add(new Duplicate(6, 7, 1.0, fakeRelation));

        assertEquals(expected, result);
    }
}
