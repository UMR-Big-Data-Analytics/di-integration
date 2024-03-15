package de.di;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.exceptions.CsvValidationException;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

@Getter
public class Relation {

    private String name;
    private String[] labels;
    private String[][] records;

    public Relation(String filePath) throws IOException, CsvValidationException {

        Path path = Path.of(filePath);
        this.name = path.getFileName().toString().split("\\.")[0];

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withQuoteChar('"')
                .withEscapeChar('\\')
                .withStrictQuotes(false)
                .withIgnoreLeadingWhiteSpace(false)
                .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                .build();

        BufferedReader buffer = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        CSVReader reader = new CSVReaderBuilder(buffer).withCSVParser(parser).build();

        this.labels = reader.readNext();

        ArrayList<String[]> records = new ArrayList<>();
        String[] line;
        while ((line = reader.readNext()) != null)
            records.add(line);
        reader.close();
        this.records = new String[records.size()][];
        for (int i = 0; i < records.size(); i++)
            this.records[i] = records.get(i);
    }

    public String[][] getColumns() {
        String[][] columns = new String[this.labels.length][];
        for (int i = 0; i < columns.length; i++)
            columns[i] = new String[this.records.length];
        for (int j = 0; j < this.records.length; j++)
            for (int i = 0; i < columns.length; i++)
                columns[i][j] = this.records[j][i];
        return columns;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.name).append(Arrays.toString(labels));
        for (String[] record : this.records)
            builder.append("\r\n").append(Arrays.toString(record));
        return builder.toString();
    }
}
