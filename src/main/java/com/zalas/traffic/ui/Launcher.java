package com.zalas.traffic.ui;

import com.zalas.traffic.prediction.NeuralNetworkPredictionTester;
import com.zalas.traffic.prediction.network.NeuralNetworkPredictor;
import com.zalas.traffic.prediction.network.NeuralNetworkPredictorFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Launcher {

    private static final String[] STREETS = new String[]{"broniewskiegoN", "broniewskiegoS", "glowna", "kusocinskiego"};
    private static final String[] TIME_OF_DAY = new String[]{"morning", "afternoon", "evening"};

    public static void main(String[] args) throws Exception {
        new Launcher().run();
    }

    public void run() throws Exception {

        for (String street : STREETS) {
            for (String time : TIME_OF_DAY) {
                File trainingDataFile = new File(getClass().getResource("/data/" + street + "-" + time + ".csv").toURI());
                File testDataFile = new File(getClass().getResource("/test/" + street + "-" + time + ".csv").toURI());
                NeuralNetworkPredictor predictor = createPredictor(trainingDataFile);
                testPredictor(trainingDataFile, testDataFile, predictor);
            }
        }

        System.out.println("Test completed");

    }

    private NeuralNetworkPredictor createPredictor(File dataFile) throws IOException, URISyntaxException {
        NeuralNetworkPredictorFactory factory = new NeuralNetworkPredictorFactory();
        NeuralNetworkPredictor predictor = factory.create(dataFile, 100);
        predictor.train();
        return predictor;
    }

    private void testPredictor(File dataFile, File testFile, NeuralNetworkPredictor predictor) throws URISyntaxException, IOException {
        NeuralNetworkPredictionTester neuralNetworkPredictionTester = new NeuralNetworkPredictionTester(dataFile, testFile, predictor);
        neuralNetworkPredictionTester.test();
    }

}
