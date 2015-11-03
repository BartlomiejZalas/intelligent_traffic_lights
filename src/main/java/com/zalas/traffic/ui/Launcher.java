package com.zalas.traffic.ui;

import com.zalas.traffic.prediction.NeuralNetworkPredictionTester;
import com.zalas.traffic.prediction.network.NeuralNetworkPredictor;
import com.zalas.traffic.prediction.network.NeuralNetworkPredictorFactory;

import java.io.File;

public class Launcher {

    private static final String NETWORKS_OUT_DIR = "/home/bartek/AITraffic/networks/";

    public static void main(String[] args) throws Exception {
        new Launcher().run();
    }

    public void run() throws Exception {

        NeuralNetworkPredictorFactory factory = new NeuralNetworkPredictorFactory();
        File morningFile = new File(getClass().getResource("/broniewskiegoN/BroniewskiegoN-morning.csv").toURI());
        File afternoonFile = new File(getClass().getResource("/broniewskiegoN/BroniewskiegoN-afternoon.csv").toURI());
        File eveningFile = new File(getClass().getResource("/broniewskiegoN/BroniewskiegoN-evening.csv").toURI());

        File morningFileTest = new File(getClass().getResource("/broniewskiegoN/test/BroniewskiegoN-morning.csv").toURI());
        File afternoonFileTest = new File(getClass().getResource("/broniewskiegoN/test/BroniewskiegoN-afternoon.csv").toURI());
        File eveningFileTest = new File(getClass().getResource("/broniewskiegoN/test/BroniewskiegoN-evening.csv").toURI());


        NeuralNetworkPredictor predictorAfternoon = factory.create(afternoonFile, 100);
        predictorAfternoon.train();


//        NeuralNetworkPredictor predictorEvening = factory.create(eveningFile, 100);
//        predictorEvening.train();




//        NeuralNetworkPredictor predictorMorning = factory.create(morningFile, 100);
//        predictorMorning.train();

        NeuralNetworkPredictionTester neuralNetworkPredictionTester = new NeuralNetworkPredictionTester(afternoonFile, afternoonFileTest, predictorAfternoon);
        neuralNetworkPredictionTester.test();


    }


}
