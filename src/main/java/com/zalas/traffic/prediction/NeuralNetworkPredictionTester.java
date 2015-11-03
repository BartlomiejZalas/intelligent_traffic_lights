package com.zalas.traffic.prediction;


import com.zalas.traffic.io.CsvLineReader;
import com.zalas.traffic.prediction.network.NeuralNetworkPredictor;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NeuralNetworkPredictionTester {
    public NeuralNetworkPredictionTester(File historicalDataFile, File testingDataFile, NeuralNetworkPredictor predictor) throws IOException {

        CsvLineReader csvLineReader = new CsvLineReader();
        ArrayList<Double> historicalData = csvLineReader.getValuesFromColumn(historicalDataFile, 1);
        List<Double> subHistoricalData = historicalData.subList(historicalData.size() - 100, historicalData.size());


        ArrayList<Double> testingData = csvLineReader.getValuesFromColumn(testingDataFile, 1);


        for (double testingValue : testingData) {
            Double[] subHistoricalDataArr = new Double[subHistoricalData.size()];
            subHistoricalData.toArray(subHistoricalDataArr);

            System.out.println(Arrays.toString(subHistoricalDataArr));

            double prediction = predictor.getPrediction(ArrayUtils.toPrimitive(subHistoricalDataArr));
            System.out.println("Expected value: " + testingValue);
            System.out.println("Prediction:     " + prediction);

            subHistoricalData.remove(0);
            subHistoricalData.add(testingValue);

            System.out.println("**************************************");
        }

    }
}
