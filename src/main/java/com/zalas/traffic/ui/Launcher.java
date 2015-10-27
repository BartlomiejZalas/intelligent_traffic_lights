package com.zalas.traffic.ui;

import com.zalas.traffic.prediction.network.NeuralNetworkPredictor;
import com.zalas.traffic.prediction.network.NeuralNetworkPredictorFactory;

import java.io.File;

public class Launcher {

    public static void main(String[] args) throws Exception {
        new Launcher().run();
    }

    public void run() throws Exception {

        NeuralNetworkPredictorFactory factory = new NeuralNetworkPredictorFactory();
        File inputFile = new File(getClass().getResource("/trainingNorth.csv").toURI());

        NeuralNetworkPredictor predictor = factory.create(inputFile, 5);
        predictor.train();

        double prediction = predictor.getPrediction(new double[]{36, 23, 15, 14, 11});

        System.out.println("Expected value: " + 6);
        System.out.println("Prediction:     " + prediction);
    }


}
