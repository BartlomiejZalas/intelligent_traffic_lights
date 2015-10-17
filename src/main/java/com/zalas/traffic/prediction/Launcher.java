package com.zalas.traffic.prediction;

import java.io.File;

public class Launcher {

    public static void main(String[] args) throws Exception {
        new Launcher().run();
    }

    public void run() throws Exception {
        File inputData = new File(getClass().getResource("/rawTrainingData.csv").toURI());
        NeuralNetworkPredictor predictor = new NeuralNetworkPredictor(5, inputData);
        predictor.run();
    }
}
