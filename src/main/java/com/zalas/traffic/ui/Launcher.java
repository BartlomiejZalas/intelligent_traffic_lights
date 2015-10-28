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
        File inputFile = new File(getClass().getResource("/broniewskiegoN/Broniewskiego N - afternoon.csv").toURI());

        NeuralNetworkPredictor predictor = factory.create(inputFile, 100);
        predictor.train();
        double prediction = predictor.getPrediction(new double[]{
                149, 161, 135, 135, 163, 203, 141, 125, 156, 158, 122, 122, 174, 175, 154, 149, 145, 152, 133, 136, 191,
                178, 136, 142, 134, 153, 140, 139, 181, 183, 149, 129, 136, 153, 123, 119, 164, 208, 137, 148, 134, 142,
                143, 141, 172, 175, 141, 134, 154, 141, 148, 133, 172, 181, 139, 140, 152, 164, 134, 125, 167, 176, 148,
                137, 149, 154, 139, 136, 178, 209, 149, 144, 132, 146, 140, 127, 176, 175, 155, 132, 135, 158, 148, 130,
                195, 185, 134, 146, 135, 152, 125, 127, 185, 203, 159, 134, 140, 151, 125, 139
        });

        System.out.println("Expected value: " + 178);
        System.out.println("Prediction:     " + prediction);
    }


}
