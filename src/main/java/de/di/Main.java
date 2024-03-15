package de.di;

import com.opencsv.exceptions.CsvValidationException;
import de.di.data_profiling.structures.IND;
import de.di.data_profiling.structures.UCC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");

        try {
            Relation relation = new Relation("data/tpch_nation.csv");

            System.out.println(relation.toString());

            Set<String> attributes = new HashSet<>(3);
            attributes.add("6");
            attributes.add("2");
            attributes.add("5");
            UCC ucc = new UCC(relation.getName(), attributes);
            IND ind = new IND(relation.getName(), new ArrayList<>(attributes), relation.getName(), new ArrayList<>(attributes));

            System.out.println(ucc);

            System.out.println(ind);

        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}