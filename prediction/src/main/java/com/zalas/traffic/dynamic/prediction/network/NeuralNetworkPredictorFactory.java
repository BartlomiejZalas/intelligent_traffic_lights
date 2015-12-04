package com.zalas.traffic.dynamic.prediction.network;

import com.zalas.traffic.dynamic.prediction.normalization.NormalizedValues;
import com.zalas.traffic.io.csv.CsvLineReader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class NeuralNetworkPredictorFactory {

    public NeuralNetworkPredictor create(File inputData, int amountOfTrainingElements) throws URISyntaxException, IOException {
        CsvLineReader csvLineReader = new CsvLineReader();
        List<Double> values = csvLineReader.getValuesFromColumn(inputData, 1);
        return new NeuralNetworkPredictor(amountOfTrainingElements, new NormalizedValues(values));
    }
}

