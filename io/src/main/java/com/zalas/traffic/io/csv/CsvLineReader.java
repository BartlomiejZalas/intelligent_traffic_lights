package com.zalas.traffic.io.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvLineReader {

    public ArrayList<Double> getValuesFromColumn(File csvFile, int column) throws IOException {

        ArrayList<Double> values = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(csvFile));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                values.add(getValueFromColumn(line, column));
            }
        } finally {
            reader.close();
        }
        return values;
    }

    public List<List<Double>> getRows(File csvFile) throws IOException {
        List<List<Double>> rows = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(csvFile));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                rows.add(getDoublesFromLine(line));
            }
        } finally {
            reader.close();
        }
        return rows;
    }

    private List<Double> getDoublesFromLine(String line) {
        List<Double> numbers = new ArrayList<>();
        String[] tokens = line.split(",");
        for (String number : tokens) {
            numbers.add(Double.valueOf(number));
        }
        return numbers;
    }

    private Double getValueFromColumn(String line, int column) {
        String[] tokens = line.split(",");
        return Double.valueOf(tokens[column]);
    }


}
