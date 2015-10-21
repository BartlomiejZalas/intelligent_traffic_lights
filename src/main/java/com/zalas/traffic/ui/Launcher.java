package com.zalas.traffic.ui;

import com.zalas.traffic.prediction.NeuralNetworkPredictor;
import com.zalas.traffic.prediction.NeuralNetworkPredictorFactory;

public class Launcher {

    public static void main(String[] args) throws Exception {
        new Launcher().run();
    }

    public void run() throws Exception {

        NeuralNetworkPredictorFactory factory = new NeuralNetworkPredictorFactory();

        NeuralNetworkPredictor predictor = factory.create("/rawTrainingData.csv", 6);
        predictor.train();
        predictor.test();
    }


}
