package com.zalas.traffic.dynamic.prediction;


import com.zalas.traffic.io.csv.CsvLineReader;
import com.zalas.traffic.io.report.ReportWriter;
import com.zalas.traffic.dynamic.prediction.network.NeuralNetworkPredictor;
import com.zalas.traffic.dynamic.utils.Utils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class NeuralNetworkPredictionTester {

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

    public double checkAccuracyAndGenerateReport() throws IOException {

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
        reportWriter.save(Utils.getNetworksAccuracyOutputDirectory() + createOutputFilename());

        return finalAccuracy;
    }

    private double calculateFinalAccuracy(double finalAccuracyAcc, int numberOfElements) {
        return (finalAccuracyAcc / numberOfElements) * 100;
    }

    private List<Double> getHistoricalData() throws IOException {
        ArrayList<Double> historicalData = csvLineReader.getValuesFromColumn(historicalDataFile, Utils.COLUMN_WITH_VALUES);
        return historicalData.subList(historicalData.size() - predictor.getNoOfInputs(), historicalData.size());
    }

    private ArrayList<Double> getTestingData() throws IOException {
        return csvLineReader.getValuesFromColumn(testingDataFile, Utils.COLUMN_WITH_VALUES);
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
