package com.zalas.traffic.io.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class CsvLineReader {

    public List<Double> getValuesFromColumn(File csvFile, int column) throws IOException {
        return getLinesFromFile(csvFile).stream()
                .map(line -> getValueFromColumn(line, column))
                .collect(Collectors.toList());
    }

    public List<List<Double>> getRows(File csvFile) throws IOException {
        return  getLinesFromFile(csvFile).stream()
                .map(this::getDoublesFromLine)
                .collect(Collectors.toList());
    }

    private List<String> getLinesFromFile(File csvFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(csvFile));
        try {
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } finally {
            reader.close();
        }
    }

    private List<Double> getDoublesFromLine(String line) {
        List<Double> numbers = new ArrayList<>();
        numbers.addAll(asList(line.split(",")).stream()
                .map(Double::valueOf)
                .collect(Collectors.toList()));
        return numbers;
    }

    private Double getValueFromColumn(String line, int column) {
        return getDoublesFromLine(line).get(column);
    }


}
