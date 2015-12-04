package com.zalas.traffic.dynamic.data;

import com.zalas.traffic.io.csv.CsvLineReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

;

public class DataSetFromCsvCreator {
    private CsvLineReader csvLineReader;

    public DataSetFromCsvCreator(CsvLineReader csvLineReader) {

        this.csvLineReader = csvLineReader;
    }

    public DataSet prepareDataSet(File csvFile) throws IOException {
        List<List<Double>> rows = csvLineReader.getRows(csvFile);
        List<DataRow> dataRows = new ArrayList<>();
        for(List<Double> row : rows) {
            dataRows.add(new DataRow(new double[]{row.get(0), row.get(1), row.get(2), row.get(3)}, row.get(4)));
        }
        return new DataSet(dataRows);
    }
}
