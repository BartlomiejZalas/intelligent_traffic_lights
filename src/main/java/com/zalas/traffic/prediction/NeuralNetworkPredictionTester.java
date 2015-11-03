package com.zalas.traffic.prediction;


import com.zalas.traffic.io.CsvLineReader;
import com.zalas.traffic.io.ReportWriter;
import com.zalas.traffic.prediction.network.NeuralNetworkPredictor;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetworkPredictionTester {


    private static final String REPORT_OUTPUT_DIR = "/home/bartek/AITraffic/networks_accuracy/";

    private File historicalDataFile;
    private File testingDataFile;
    private NeuralNetworkPredictor predictor;
    private final CsvLineReader csvLineReader = new CsvLineReader();
    private ReportWriter reportWriter = new ReportWriter();

    public NeuralNetworkPredictionTester(File historicalDataFile, File testingDataFile, NeuralNetworkPredictor predictor) throws FileNotFoundException, UnsupportedEncodingException {
        this.historicalDataFile = historicalDataFile;
        this.testingDataFile = testingDataFile;
        this.predictor = predictor;
    }

    public void test() throws IOException {

        double finalAccuracyAccumulator = 0;

        List<Double> subHistoricalData = getHistoricalData();
        List<Double> testingData = getTestingData();

        for (double expectedValue : testingData) {
            double prediction = predictor.getPrediction(convertListToArray(subHistoricalData));
            double percentageAccuracy = calculatePercentageAccuracy(expectedValue, prediction);

            finalAccuracyAccumulator += percentageAccuracy;
            updateHistoricalValues(subHistoricalData, expectedValue);

            reportWriter.addReportRow(expectedValue, prediction, percentageAccuracy);
        }

        double finalAccuracy = calculateFinalAccuracy(finalAccuracyAccumulator, testingData.size());

        reportWriter.addSummary(finalAccuracy);
        reportWriter.save(REPORT_OUTPUT_DIR + createOutputFilename());
    }

    private double calculateFinalAccuracy(double finalAccuracyAcc, int numberOfElements) {
        return (finalAccuracyAcc / numberOfElements) * 100;
    }

    private List<Double> getHistoricalData() throws IOException {
        ArrayList<Double> historicalData = csvLineReader.getValuesFromColumn(historicalDataFile, 1);
        return historicalData.subList(historicalData.size() - predictor.getNoOfInputs(), historicalData.size());
    }

    private ArrayList<Double> getTestingData() throws IOException {
        return csvLineReader.getValuesFromColumn(testingDataFile, 1);
    }

    private void updateHistoricalValues(List<Double> subHistoricalData, double expectedValue) {
        subHistoricalData.remove(0);
        subHistoricalData.add(expectedValue);
    }

    private String createOutputFilename() {
        return historicalDataFile.getName().replace(".csv", "") + ".txt";
    }

    private double[] convertListToArray(List<Double> subHistoricalData) {
        Double[] subHistoricalDataArr = new Double[subHistoricalData.size()];
        subHistoricalData.toArray(subHistoricalDataArr);
        return ArrayUtils.toPrimitive(subHistoricalDataArr);
    }

    private double calculatePercentageAccuracy(double expectedValue, double prediction) {
        double percentageAccuracy;
        if (expectedValue > prediction) {
            percentageAccuracy = prediction / expectedValue;
        } else {
            percentageAccuracy = expectedValue / prediction;
        }
        return percentageAccuracy;
    }
}
