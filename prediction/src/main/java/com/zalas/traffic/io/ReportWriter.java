package com.zalas.traffic.io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ReportWriter {

    private List<ReportRow> reportRows = new ArrayList<>();
    private double finalAccuracy = 0;


    public void addReportRow(double expectedValue, double prediction, double percentageAccuracy) {
        ReportRow row = new ReportRow(expectedValue, prediction, percentageAccuracy);
        reportRows.add(row);
    }

    public void addSummary(double finalAccuracy) {
        this.finalAccuracy = finalAccuracy;
    }

    public void save(String filePath) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(filePath, "UTF-8");
        for(ReportRow row : reportRows) {
            writeRow(writer, row);
        }
        writeSummary(writer);
        writer.close();
    }

    private void writeSummary(PrintWriter writer) {
        writer.println("Final accuracy: " + finalAccuracy + "%");
    }

    private void writeRow(PrintWriter writer, ReportRow row) {
        writer.println("Expected value: " + row.getExpectedValue());
        writer.println("Prediction:     " + row.getPrediction());
        writer.println("%:              " + row.getPercentageAccuracy() * 100);
        writer.println("**************************************");
    }
}
