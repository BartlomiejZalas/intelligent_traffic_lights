package com.zalas.traffic.prediction;

import com.zalas.traffic.io.CsvLineReader;
import com.zalas.traffic.prediction.normalization.NormalizedValues;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class NeuralNetworkPredictorFactory {

    public NeuralNetworkPredictor create(String resourceFilePath, int amountOfTrainingElements) throws URISyntaxException, IOException {
        File inputData = new File(getClass().getResource(resourceFilePath).toURI());
        CsvLineReader csvLineReader = new CsvLineReader();
        ArrayList<Double> values = csvLineReader.getValuesFromColumn(inputData, 1);
        return new NeuralNetworkPredictor(amountOfTrainingElements, new NormalizedValues(values));
    }
}

