package com.zalas.traffic.ui;

import com.zalas.traffic.prediction.NeuralNetworkPredictor;
import com.zalas.traffic.prediction.NeuralNetworkPredictorFactory;

import java.io.File;

public class Launcher {

    public static void main(String[] args) throws Exception {
        new Launcher().run();
    }

    public void run() throws Exception {

        NeuralNetworkPredictorFactory factory = new NeuralNetworkPredictorFactory();
        File inputFile = new File(getClass().getResource("/rawTrainingData.csv").toURI());

        NeuralNetworkPredictor predictor = factory.create(inputFile, 6);
        predictor.train();
        double prediction = predictor.getPrediction(new double[]{2061.05, 2056.15, 2061.02, 2086.24, 2067.89, 2059.69});
        System.out.println("Expected value: " + 2066.96);
        System.out.println("Prediction:     " + prediction);
    }


}
