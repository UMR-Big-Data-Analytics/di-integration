package de.di.schema_matching;

import de.di.Relation;
import de.di.schema_matching.helper.AUCPR;
import de.di.schema_matching.helper.AUROC;
import de.di.schema_matching.structures.CorrespondenceMatrix;
import de.di.schema_matching.structures.SimilarityMatrix;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * The FirstLineSchemaMatcherTest tests if the matching results are at least as good as a trivial implementation of
 * the FirstLineSchemaMatcher, which uses only a single similarity measure in a naive way.
 */
public class FirstLineSchemaMatcherTest {

    private static final String s = File.separator;

    @Test
    public void testMatchingQuality_dblp_acm() {
        this.runTestFor("dblp_acm", new double[]{0.88}, new double[]{0.88});
    }

    @Test
    public void testMatchingQuality_fdb1_mb2() {
        this.runTestFor("fdb1_mb2", new double[]{0.34, 0.68, 1.0, 0.69, 0.85, 0.94}, new double[]{0.00, 0.05, 1.0, 0.25, 0.08, 0.25});
    }

    @Test
    public void testMatchingQuality_Musicians_joinable() {
        this.runTestFor("Musicians_joinable", new double[]{0.99}, new double[]{0.94});
    }

    @Test
    public void testMatchingQuality_Musicians_semjoinable() {
        this.runTestFor("Musicians_semjoinable", new double[]{0.87}, new double[]{0.77});
    }

    @Test
    public void testMatchingQuality_Musicians_unionable() {
        this.runTestFor("Musicians_unionable", new double[]{0.94}, new double[]{0.90});
    }

    @Test
    public void testMatchingQuality_Musicians_viewunion() {
        this.runTestFor("Musicians_viewunion", new double[]{0.81}, new double[]{0.60});
    }

    private void runTestFor(String scenario, double[] expectedROC, double[] expectedPR) {
        List<Path> sources = this.scanFiles("data" + s + "schema_matching" + s + scenario + s + "source");
        List<Path> targets = this.scanFiles("data" + s + "schema_matching" + s + scenario + s + "target");

        Collections.sort(sources);
        Collections.sort(targets);

        int relationMatch = 0;
        for (Path source : sources) {
            for (Path target : targets) {
                // Check if this relation pair is actually a pair that is supposed to match
                Path groundTruth = Path.of(source.toString().replaceFirst("source", "ground_truth").replace(".csv", "___") + target.getFileName());
                if (!groundTruth.toFile().exists())
                    continue;

                // Load the relations and ground truth data
                Relation sourceRelation = new Relation(source.toString(), true, ',', StandardCharsets.UTF_8);
                Relation targetRelation = new Relation(target.toString(), true, ',', StandardCharsets.UTF_8);
                Relation groundRelation = new Relation(groundTruth.toString(), false, ',', StandardCharsets.UTF_8);

                // Calculate the matching
                FirstLineSchemaMatcher matcher = new FirstLineSchemaMatcher();

                SimilarityMatrix simMatrix = matcher.match(sourceRelation, targetRelation);

                // Translate ground truth data into a correlation matrix
                CorrespondenceMatrix corrMatrix = new CorrespondenceMatrix(groundRelation, sourceRelation, targetRelation);

                // Evaluate the matching
                AUROC rocAlgorithm = new AUROC();
                AUCPR prAlgorithm = new AUCPR();

                double rocScore = rocAlgorithm.run(corrMatrix.getMatrix(), simMatrix.getMatrix());
                double prScore = prAlgorithm.run(corrMatrix.getMatrix(), simMatrix.getMatrix());

                assertTrue(expectedROC[relationMatch] <= rocScore);
                assertTrue(expectedPR[relationMatch] <= prScore);
/*
                // Print a lot of stuff
                System.out.println(simMatrix);
                System.out.println(corrMatrix);
                System.out.println("Scores: " + rocScore + ", " + prScore);

                DoubleList trueMatchScores = new DoubleArrayList();
                DoubleList falseMatchScores = new DoubleArrayList();
                for (int i = 0; i < simMatrix.getMatrix().length; i++) {
                    for (int j = 0; j < simMatrix.getMatrix()[0].length; j++) {
                        if (corrMatrix.getMatrix()[i][j] == 1)
                            trueMatchScores.add(simMatrix.getMatrix()[i][j]);
                        else
                            falseMatchScores.add(simMatrix.getMatrix()[i][j]);
                    }
                }
                System.out.println(falseMatchScores.stream().max(Double::compareTo).get());
                System.out.println(trueMatchScores.stream().min(Double::compareTo).get());
*/
                relationMatch++;
            }
        }
    }

    private List<Path> scanFiles(String folderPath) {
        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            return paths.filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
