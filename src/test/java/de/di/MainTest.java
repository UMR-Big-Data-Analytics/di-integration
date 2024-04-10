package de.di;

import org.junit.Test;

import java.io.File;

public class MainTest {

    @Test
    public void testApplicationInterface() {
        String[] args;
        String s = File.separator;

        args = new String[]{"Jaccard", "--string1", "Data Integration Uni Marburg", "--string2", "Datenintegration Universität Marburg", "--bagSemantics", "false", "--tokenSize", "2", "--usePadding", "true"};
        Main.main(args);
        System.out.println();

        args = new String[]{"Levenshtein", "--string1", "Data Integration Uni Marburg", "--string2", "Datenintegration Universität Marburg", "--withDamerau", "true"};
        Main.main(args);
        System.out.println();

        args = new String[]{"LocalitySensitiveHashing", "--string1", "Data Integration Uni Marburg", "--string2", "Datenintegration Universität Marburg", "--bagSemantics", "false", "--tokenSize", "2", "--usePadding", "true", "--numHashFunctions", "2"};
        Main.main(args);
        System.out.println();

        args = new String[]{"UCCProfiler", "--input", "data" + s + "data_profiling", "--hasHeader", "true", "--separator", ";"};
        Main.main(args);
        System.out.println();

        args = new String[]{"INDProfiler", "--input", "data" + s + "data_profiling", "--hasHeader", "true", "--separator", ";", "--discoverNary", "false"};
        Main.main(args);
        System.out.println();

        args = new String[]{"FirstLineSchemaMatcher", "--scenarioPath", "data" + s + "schema_matching" + s + "fdb1_mb2"};
        Main.main(args);
        System.out.println();

        args = new String[]{"SecondLineSchemaMatcher", "--scenarioPath", "data" + s + "schema_matching" + s + "fdb1_mb2"};
        Main.main(args);
        System.out.println();

        args = new String[]{"DuplicateDetection", "--inputFile", "data" + s + "duplicate_detection" + s + "cd.csv", "--hasHeader", "true", "--separator", ";", "--sortingKeys", "0,1", "--windowSize", "3"};
        Main.main(args);
        System.out.println();
    }
}
