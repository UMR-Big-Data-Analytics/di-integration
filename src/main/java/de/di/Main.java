package de.di;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;
import de.di.data_profiling.INDProfiler;
import de.di.data_profiling.UCCProfiler;
import de.di.data_profiling.structures.IND;
import de.di.data_profiling.structures.UCC;
import de.di.duplicate_detection.RecordComparator;
import de.di.duplicate_detection.SortedNeighborhood;
import de.di.duplicate_detection.TransitiveClosure;
import de.di.duplicate_detection.structures.Duplicate;
import de.di.schema_matching.FirstLineSchemaMatcher;
import de.di.schema_matching.SecondLineSchemaMatcher;
import de.di.schema_matching.helper.AUCPR;
import de.di.schema_matching.helper.AUROC;
import de.di.schema_matching.structures.CorrespondenceMatrix;
import de.di.schema_matching.structures.SimilarityMatrix;
import de.di.similarity_measures.Jaccard;
import de.di.similarity_measures.Levenshtein;
import de.di.similarity_measures.LocalitySensitiveHashing;
import de.di.similarity_measures.SimilarityMeasure;
import de.di.similarity_measures.helper.Tokenizer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        CommandJaccard commandJaccard = new CommandJaccard();
        CommandLevenshtein commandLevenshtein = new CommandLevenshtein();
        CommandLocalitySensitiveHashing commandLocalitySensitiveHashing = new CommandLocalitySensitiveHashing();
        CommandUCCProfiler commandUCCProfiler = new CommandUCCProfiler();
        CommandINDProfiler commandINDProfiler = new CommandINDProfiler();
        CommandFirstLineSchemaMatcher commandFirstLineSchemaMatcher = new CommandFirstLineSchemaMatcher();
        CommandSecondLineSchemaMatcher commandSecondLineSchemaMatcher = new CommandSecondLineSchemaMatcher();
        CommandDuplicateDetection commandDuplicateDetection = new CommandDuplicateDetection();

        JCommander jCommander = JCommander.newBuilder()
                .addCommand(CommandJaccard.COMMAND, commandJaccard)
                .addCommand(CommandLevenshtein.COMMAND, commandLevenshtein)
                .addCommand(CommandLocalitySensitiveHashing.COMMAND, commandLocalitySensitiveHashing)
                .addCommand(CommandUCCProfiler.COMMAND, commandUCCProfiler)
                .addCommand(CommandINDProfiler.COMMAND, commandINDProfiler)
                .addCommand(CommandFirstLineSchemaMatcher.COMMAND, commandFirstLineSchemaMatcher)
                .addCommand(CommandSecondLineSchemaMatcher.COMMAND, commandSecondLineSchemaMatcher)
                .addCommand(CommandDuplicateDetection.COMMAND, commandDuplicateDetection)
                .build();

        try {
            jCommander.parse(args);

            if (jCommander.getParsedCommand() == null)
                throw new ParameterException("No command given.");

            long time = System.currentTimeMillis();

            System.out.println("// " + jCommander.getParsedCommand() + " //");

            switch (jCommander.getParsedCommand()) {
                case CommandJaccard.COMMAND:
                    SimilarityMeasure jaccard = new Jaccard(new Tokenizer(commandJaccard.tokenSize, commandJaccard.usePadding), commandJaccard.bagSemantics);
                    System.out.println(jaccard.calculate(commandJaccard.string1, commandJaccard.string2));
                    break;
                case CommandLevenshtein.COMMAND:
                    SimilarityMeasure levenshtein = new Levenshtein(commandLevenshtein.withDamerau);
                    System.out.println(levenshtein.calculate(commandLevenshtein.string1, commandLevenshtein.string2));
                    break;
                case CommandLocalitySensitiveHashing.COMMAND:
                    SimilarityMeasure localitySensitiveHashing = new LocalitySensitiveHashing(new Tokenizer(commandLocalitySensitiveHashing.tokenSize, commandLocalitySensitiveHashing.usePadding), commandLocalitySensitiveHashing.bagSemantics, commandLocalitySensitiveHashing.numHashFunctions);
                    System.out.println(localitySensitiveHashing.calculate(commandLocalitySensitiveHashing.string1, commandLocalitySensitiveHashing.string2));
                    break;
                case CommandUCCProfiler.COMMAND:
                    UCCProfiler uccProfiler = new UCCProfiler();
                    List<UCC> uccResult = new ArrayList<>();
                    for (Relation relation : Relation.readAllRelationsIn(commandUCCProfiler.inputPath, commandUCCProfiler.hasHeader, commandUCCProfiler.separator.charAt(0), StandardCharsets.UTF_8))
                        uccResult.addAll(uccProfiler.profile(relation));
                    uccResult.forEach(System.out::println);
                    break;
                case CommandINDProfiler.COMMAND:
                    INDProfiler indProfiler = new INDProfiler();
                    List<Relation> relations = Relation.readAllRelationsIn(commandINDProfiler.inputPath, commandINDProfiler.hasHeader, commandINDProfiler.separator.charAt(0), StandardCharsets.UTF_8);
                    List<IND> indResult = indProfiler.profile(relations, commandINDProfiler.discoverNary);
                    indResult.forEach(System.out::println);
                    break;
                case CommandFirstLineSchemaMatcher.COMMAND:
                    runMatchingFor(commandFirstLineSchemaMatcher.scenarioPath, false);
                    break;
                case CommandSecondLineSchemaMatcher.COMMAND:
                    runMatchingFor(commandSecondLineSchemaMatcher.scenarioPath, true);
                    break;
                case CommandDuplicateDetection.COMMAND:
                    Relation relation = new Relation(commandDuplicateDetection.scenarioPath, commandDuplicateDetection.hasHeader, commandDuplicateDetection.separator.charAt(0), StandardCharsets.ISO_8859_1);
                    RecordComparator recordComparator = SortedNeighborhood.suggestRecordComparatorFor(relation);
                    SortedNeighborhood sortedNeighborhood = new SortedNeighborhood();
                    int[] sortingKeys = Arrays.stream(commandDuplicateDetection.sortingKeys.split(",")).mapToInt(Integer::parseInt).toArray();
                    Set<Duplicate> duplicates = sortedNeighborhood.detectDuplicates(relation, sortingKeys, commandDuplicateDetection.windowSize, recordComparator);
                    TransitiveClosure transitiveClosure = new TransitiveClosure();
                    duplicates = transitiveClosure.calculate(duplicates);
                    duplicates.forEach(System.out::println);
                    break;
                default:
                    throw new AssertionError();
            }

            System.out.println("Runtime: " + (System.currentTimeMillis() - time) / 1000 + " sec");

        } catch (ParameterException e) {
            System.out.printf("Could not parse args: %s\n", e.getMessage());
            jCommander.usage();
            System.exit(1);
        }
    }

    @Parameters(commandDescription = "Execute the Jaccard algorithm.")
    private static class CommandJaccard {

        public static final String COMMAND = "Jaccard";

        @Parameter(names = {"--string1"}, description = "First input string for the comparison", required = true, arity = 1)
        String string1;

        @Parameter(names = {"--string2"}, description = "Second input string for the comparison", required = true, arity = 1)
        String string2;

        @Parameter(names = {"--bagSemantics"}, description = "Specification to use bag or set semantics", required = false, arity = 1)
        boolean bagSemantics = false;

        @Parameter(names = {"--tokenSize"}, description = "Specification of the token size to be used", required = false, arity = 1)
        int tokenSize = 2;

        @Parameter(names = {"--usePadding"}, description = "Specification of whether or not padding should be used", required = false, arity = 1)
        boolean usePadding = false;
    }

    @Parameters(commandDescription = "Execute the Levenshtein algorithm.")
    private static class CommandLevenshtein {

        public static final String COMMAND = "Levenshtein";

        @Parameter(names = {"--string1"}, description = "First input string for the comparison", required = true, arity = 1)
        String string1;

        @Parameter(names = {"--string2"}, description = "Second input string for the comparison", required = true, arity = 1)
        String string2;

        @Parameter(names = {"--withDamerau"}, description = "Specification of whether Levenshtein or DamerauLevenshtein should be calculated", required = false, arity = 1)
        boolean withDamerau = true;
    }

    @Parameters(commandDescription = "Execute the Jaccard algorithm.")
    private static class CommandLocalitySensitiveHashing {

        public static final String COMMAND = "LocalitySensitiveHashing";

        @Parameter(names = {"--string1"}, description = "First input string for the comparison", required = true, arity = 1)
        String string1;

        @Parameter(names = {"--string2"}, description = "Second input string for the comparison", required = true, arity = 1)
        String string2;

        @Parameter(names = {"--bagSemantics"}, description = "Specification to use bag or set semantics", required = false, arity = 1)
        boolean bagSemantics = false;

        @Parameter(names = {"--tokenSize"}, description = "Specification of the token size to be used", required = false, arity = 1)
        int tokenSize = 2;

        @Parameter(names = {"--usePadding"}, description = "Specification of whether or not padding should be used", required = false, arity = 1)
        boolean usePadding = false;

        @Parameter(names = {"--numHashFunctions"}, description = "Specification of the number of minHash functions to be used; must be smaller or equal to the tokenSize", required = false, arity = 1)
        int numHashFunctions = 2;
    }

    @Parameters(commandDescription = "Execute the UCCProfiler data profiling algorithm.")
    private static class CommandUCCProfiler {

        public static final String COMMAND = "UCCProfiler";

        @Parameter(names = {"--input"}, description = "Path of the input folder; the profiling will consider all files in that folder", required = false, arity = 1)
        String inputPath = "data" + File.separator + "data_profiling";

        @Parameter(names = {"--hasHeader"}, description = "File has header flag", required = false, arity = 1)
        boolean hasHeader = true;

        @Parameter(names = {"--separator"}, description = "File separator character", required = false, arity = 1)
        String separator = ";";
    }

    @Parameters(commandDescription = "Execute the INDProfiler data profiling algorithm.")
    private static class CommandINDProfiler {

        public static final String COMMAND = "INDProfiler";

        @Parameter(names = {"--input"}, description = "Path of the input folder; the profiling will consider all files in that folder", required = false, arity = 1)
        String inputPath = "data" + File.separator + "data_profiling";

        @Parameter(names = {"--hasHeader"}, description = "File has header flag", required = false, arity = 1)
        boolean hasHeader = true;

        @Parameter(names = {"--separator"}, description = "File separator character", required = false, arity = 1)
        String separator = ";";

        @Parameter(names = {"--discoverNary"}, description = "Flag to indicate whether n-ary INDs should be discovered as well", required = false, arity = 1)
        boolean discoverNary = false;
    }

    @Parameters(commandDescription = "Execute the FirstLineSchemaMatcher algorithm.")
    private static class CommandFirstLineSchemaMatcher {

        public static final String COMMAND = "FirstLineSchemaMatcher";

        @Parameter(names = {"--scenarioPath"}, description = "Path of the matching scenario", required = true, arity = 1)
        String scenarioPath;
    }

    @Parameters(commandDescription = "Execute the SecondLineSchemaMatcher algorithm.")
    private static class CommandSecondLineSchemaMatcher {

        public static final String COMMAND = "SecondLineSchemaMatcher";

        @Parameter(names = {"--scenarioPath"}, description = "Path of the matching scenario", required = true, arity = 1)
        String scenarioPath;
    }

    @Parameters(commandDescription = "Execute the entire duplicate detection pipeline.")
    private static class CommandDuplicateDetection {

        public static final String COMMAND = "DuplicateDetection";

        @Parameter(names = {"--inputFile"}, description = "Path of the input file for the duplicate detection", required = true, arity = 1)
        String scenarioPath;

        @Parameter(names = {"--hasHeader"}, description = "File has header flag", required = false, arity = 1)
        boolean hasHeader = true;

        @Parameter(names = {"--separator"}, description = "File separator character", required = false, arity = 1)
        String separator = ";";

        @Parameter(names = {"--sortingKeys"}, description = "Comma-separated list of sorting key indexes", required = false, arity = 1)
        String sortingKeys = "0,1";

        @Parameter(names = {"--windowSize"}, description = "Window size for the Sorted Neighborhood Method", required = false, arity = 1)
        int windowSize = 5;
    }

    private static void runMatchingFor(String scenarioPath, boolean withSecondLineMatcher) {
        List<Path> sources = scanFiles(scenarioPath + File.separator + "source");
        List<Path> targets = scanFiles(scenarioPath + File.separator + "target");

        Collections.sort(sources);
        Collections.sort(targets);

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

                // Calculate first-line matching
                FirstLineSchemaMatcher firstMatcher = new FirstLineSchemaMatcher();
                SimilarityMatrix simMatrix = firstMatcher.match(sourceRelation, targetRelation);

                // Calculate second-line matching
                SecondLineSchemaMatcher secondMatcher = new SecondLineSchemaMatcher();
                CorrespondenceMatrix corrMatrix = (withSecondLineMatcher) ? secondMatcher.match(simMatrix) : null;

                // Translate ground truth data into a correlation matrix
                CorrespondenceMatrix goldMatrix = new CorrespondenceMatrix(groundRelation, sourceRelation, targetRelation);

                // Evaluate the matching
                AUROC rocAlgorithm = new AUROC();
                AUCPR prAlgorithm = new AUCPR();

                double rocScore, prScore;
                if (withSecondLineMatcher && corrMatrix != null && corrMatrix.getMatrix() != null) {
                    rocScore = rocAlgorithm.run(goldMatrix.getMatrix(), corrMatrix.getMatrix());
                    prScore = prAlgorithm.run(goldMatrix.getMatrix(), corrMatrix.getMatrix());
                } else if (corrMatrix == null || corrMatrix.getMatrix() == null) {
                    rocScore = -1;
                    prScore = -1;
                } else {
                    rocScore = rocAlgorithm.run(goldMatrix.getMatrix(), simMatrix.getMatrix());
                    prScore = prAlgorithm.run(goldMatrix.getMatrix(), simMatrix.getMatrix());
                }

                // Print results
                System.out.println("ROC-AUC:\t" + rocScore);
                System.out.println("PR-AUC: \t" + prScore);

                System.out.println((withSecondLineMatcher) ? corrMatrix : simMatrix);
                System.out.println(goldMatrix);
            }
        }
    }

    private static List<Path> scanFiles(String folderPath) {
        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            return paths.filter(Files::isRegularFile).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
