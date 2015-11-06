package com.zalas.traffic.ui;

import com.zalas.traffic.prediction.NeuralNetworkPredictionTester;
import com.zalas.traffic.prediction.network.NeuralNetworkPredictor;
import com.zalas.traffic.prediction.network.NeuralNetworkPredictorFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Launcher {

    public static void main(String[] args) throws Exception {
        new Launcher().run();
    }

    public void run() throws Exception {

        String[] streets = new String[]{"broniewskiegoN", "broniewskiegoS", "glowna", "kusocinskiego"};
        String[] timeOfDay = new String[]{"morning", "afternoon", "evening"};

        for (String street : streets) {
            for (String time : timeOfDay) {
                File trainingDataFile = new File(getClass().getResource("/" + street + "/" + street + "-" + time + ".csv").toURI());
                File testDataFile = new File(getClass().getResource("/" + street + "/test/" + street + "-" + time + ".csv").toURI());
                testNetwork(trainingDataFile, testDataFile);
            }
        }

        System.out.println("Test completed");

    }

    private void testNetwork(File dataFile, File testFile) throws URISyntaxException, IOException {
        NeuralNetworkPredictorFactory factory = new NeuralNetworkPredictorFactory();
        NeuralNetworkPredictor predictorAfternoon = factory.create(dataFile, 100);
        predictorAfternoon.train();

        NeuralNetworkPredictionTester neuralNetworkPredictionTester = new NeuralNetworkPredictionTester(dataFile, testFile, predictorAfternoon);
        neuralNetworkPredictionTester.test();
    }

}
