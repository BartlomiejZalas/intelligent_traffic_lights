package com.zalas.traffic.prediction;

import com.zalas.traffic.io.CsvLineReader;
import com.zalas.traffic.prediction.normalization.NormalizedValues;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class NeuralNetworkPredictorFactory {

    public NeuralNetworkPredictor create(File inputData, int amountOfTrainingElements) throws URISyntaxException, IOException {
        CsvLineReader csvLineReader = new CsvLineReader();
        ArrayList<Double> values = csvLineReader.getValuesFromColumn(inputData, 1);
        return new NeuralNetworkPredictor(amountOfTrainingElements, new NormalizedValues(values));
    }
}

