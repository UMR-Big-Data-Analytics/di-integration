package de.di.schema_matching;

import de.di.Relation;
import de.di.schema_matching.structures.CorrespondenceMatrix;
import de.di.schema_matching.structures.SimilarityMatrix;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class SecondLineSchemaMatcherTest {

    private static final String s = File.separator;

    @Test
    public void testCorrectness() {
        Relation sourceRelation = new Relation("S", new String[]{"A0", "A1", "A2", "A3"}, new String[10][]);
        Relation targetRelation = new Relation("T", new String[]{"A0", "A1", "A2", "A3", "A4"}, new String[10][]);

        double[][] simMatrix = new double[4][];
        simMatrix[0] = new double[]{0.23,0.98,0.00,0.00,0.21};
        simMatrix[1] = new double[]{0.11,0.39,0.92,0.41,0.00};
        simMatrix[2] = new double[]{0.15,0.88,0.11,0.19,0.63};
        simMatrix[3] = new double[]{0.04,0.02,0.00,0.21,0.79};

        int[][] corrMatrix = new int[4][];
        corrMatrix[0] = new int[]{0,1,0,0,0};
        corrMatrix[1] = new int[]{0,0,1,0,0};
        corrMatrix[2] = new int[]{0,0,0,1,0};
        corrMatrix[3] = new int[]{0,0,0,0,1};
        CorrespondenceMatrix expected = new CorrespondenceMatrix(corrMatrix, sourceRelation, targetRelation);

        // The expected correlation matric for this example is actually the same for both the Hungarian algorithm
        // and the Stable Marriage algorithm:

        // 0 - *1,0,4,2,3;
        // 1 - *2,3,1,0,4;
        // 2 - 1,4,*3,0,2;
        // 3 - *4,3,0,1,2;

        // 0 - 0,2,1,3;
        // 1 - 0,2,3,1;
        // 2 - 1,2,0,3;
        // 3 - 1,3,2,0;
        // 4 - 3,2,0,1;

        SecondLineSchemaMatcher matcher = new SecondLineSchemaMatcher();
        CorrespondenceMatrix result = matcher.match(new SimilarityMatrix(simMatrix, sourceRelation, targetRelation));
        assertEquals(expected, result);
    }
}