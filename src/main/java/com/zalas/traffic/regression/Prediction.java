package com.zalas.traffic.regression;

import com.zalas.traffic.prediction.network.NeuralNetworkPredictor;
import com.zalas.traffic.prediction.network.NeuralNetworkPredictorFactory;

import java.io.File;

import static java.lang.Math.*;

public class Prediction {

    private static final double EXPECTED_VALUE = 2066.96;

    public static void main(String[] args) throws Exception {
        new Prediction().run();
    }

    public void run() throws Exception {

        NeuralNetworkPredictorFactory factory = new NeuralNetworkPredictorFactory();
        File inputFile = new File(getClass().getResource("/rawTrainingData.csv").toURI());

        NeuralNetworkPredictor predictor = factory.create(inputFile, 6);
        predictor.train();
        double prediction = predictor.getPrediction(new double[]{2061.05, 2056.15, 2061.02, 2086.24, 2067.89, 2059.69});
        System.out.println("Expected value: " + EXPECTED_VALUE);
        System.out.println("Prediction:     " + prediction);
        if (abs(prediction - EXPECTED_VALUE) > 10 ) {
            throw new Exception("Prediction error!");
        } else {
            System.out.println("Prediction test OK");
        }
    }
}
