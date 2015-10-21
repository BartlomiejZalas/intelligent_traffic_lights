package com.zalas.traffic.prediction;

import com.zalas.traffic.io.CsvLineReader;
import com.zalas.traffic.prediction.normalization.NormalizedValues;

import java.io.File;
import java.util.ArrayList;

public class Launcher {

    public static void main(String[] args) throws Exception {
        new Launcher().run();
    }

    public void run() throws Exception {

        File inputData = new File(getClass().getResource("/rawTrainingData.csv").toURI());
        CsvLineReader csvLineReader = new CsvLineReader();
        ArrayList<Double> values = csvLineReader.getValuesFromColumn(inputData, 1);

        NeuralNetworkPredictor predictor = new NeuralNetworkPredictor(6, new NormalizedValues(values));
        predictor.train();
        predictor.test();
    }
}
