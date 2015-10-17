package com.zalas.traffic.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvLineReader {

    public ArrayList<Double> getValuesFromColumn(File csvFile, int column) throws IOException {

        ArrayList<Double> values = new ArrayList<Double>();
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

    private Double getValueFromColumn(String line, int column) {
        String[] tokens = line.split(",");
        return Double.valueOf(tokens[column]);
    }


}
