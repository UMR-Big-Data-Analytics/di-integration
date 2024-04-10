package de.di;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public class Relation {

    private String name;
    private String[] attributes;
    private String[][] records;

    public static List<Relation> readAllRelationsIn(String folderPath, boolean hasHeader, char separator, Charset charset) {
        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            return paths.filter(Files::isRegularFile)
                    .map(filePath -> new Relation(filePath, hasHeader, separator, charset))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Relation(String filePath) {
        this(filePath, true, ';', StandardCharsets.UTF_8);
    }

    public Relation(String filePath, boolean hasHeader, char separator, Charset charset) {
        this(Path.of(filePath), hasHeader, separator, charset);
    }

    public Relation(Path filePath, boolean hasHeader, char separator, Charset charset) {
        this.name = filePath.getFileName().toString().split("\\.")[0];

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(separator)
                .withQuoteChar('"')
                .withEscapeChar('\\')
                .withStrictQuotes(false)
                .withIgnoreLeadingWhiteSpace(false)
                .withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                .build();

        ArrayList<String[]> records;
        try {
            BufferedReader buffer = Files.newBufferedReader(filePath, charset);
            CSVReader reader = new CSVReaderBuilder(buffer).withCSVParser(parser).build();

            if (hasHeader)
                this.attributes = reader.readNext();

            records = new ArrayList<>();
            String[] line;
            while ((line = reader.readNext()) != null) {
                for (int i = 0; i < line.length; i++)
                    if (line[i] == null)
                        line[i] = "";
                records.add(line);
            }
            reader.close();
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }

        this.records = new String[records.size()][];
        for (int i = 0; i < records.size(); i++)
            this.records[i] = records.get(i);

        if (!hasHeader && this.records.length != 0) {
            this.attributes = new String[this.records[0].length];
            for (int i = 0; i < this.records[0].length; i++)
                this.attributes[i] = String.valueOf(i);
        }
    }

    public String[][] getColumns() {
        String[][] columns = new String[this.attributes.length][];
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
        builder.append(this.name).append(Arrays.toString(this.attributes));
        for (String[] record : this.records)
            builder.append("\r\n").append(Arrays.toString(record));
        return builder.toString();
    }
}
