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
        File inputData = new File(getClass().getResource("/rawTrainingData.csv").toURI());

        NeuralNetworkPredictor predictor = factory.create(inputData, 6);
        predictor.train();
        predictor.test();
//        predictor.getPrediction();
    }


}
