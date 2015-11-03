package com.zalas.traffic.ui;

import com.zalas.traffic.prediction.NeuralNetworkPredictionTester;
import com.zalas.traffic.prediction.network.NeuralNetworkPredictor;
import com.zalas.traffic.prediction.network.NeuralNetworkPredictorFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Launcher {

    private static final String NETWORKS_OUT_DIR = "/home/bartek/AITraffic/networks/";

    public static void main(String[] args) throws Exception {
        new Launcher().run();
    }

    public void run() throws Exception {

        File morningFile = new File(getClass().getResource("/broniewskiegoN/BroniewskiegoN-morning.csv").toURI());
        File afternoonFile = new File(getClass().getResource("/broniewskiegoN/BroniewskiegoN-afternoon.csv").toURI());
        File eveningFile = new File(getClass().getResource("/broniewskiegoN/BroniewskiegoN-evening.csv").toURI());

        File morningFileTest = new File(getClass().getResource("/broniewskiegoN/test/BroniewskiegoN-morning.csv").toURI());
        File afternoonFileTest = new File(getClass().getResource("/broniewskiegoN/test/BroniewskiegoN-afternoon.csv").toURI());
        File eveningFileTest = new File(getClass().getResource("/broniewskiegoN/test/BroniewskiegoN-evening.csv").toURI());

        testNetwork(morningFile, morningFileTest);
        testNetwork(afternoonFile, afternoonFileTest);
        testNetwork(eveningFile, eveningFileTest);


        morningFile = new File(getClass().getResource("/broniewskiegoS/BroniewskiegoS-morning.csv").toURI());
        afternoonFile = new File(getClass().getResource("/broniewskiegoS/BroniewskiegoS-afternoon.csv").toURI());
        eveningFile = new File(getClass().getResource("/broniewskiegoS/BroniewskiegoS-evening.csv").toURI());

        morningFileTest = new File(getClass().getResource("/broniewskiegoS/test/BroniewskiegoS-morning.csv").toURI());
        afternoonFileTest = new File(getClass().getResource("/broniewskiegoS/test/BroniewskiegoS-afternoon.csv").toURI());
        eveningFileTest = new File(getClass().getResource("/broniewskiegoS/test/BroniewskiegoS-evening.csv").toURI());

        testNetwork(morningFile, morningFileTest);
        testNetwork(afternoonFile, afternoonFileTest);
        testNetwork(eveningFile, eveningFileTest);



        morningFile = new File(getClass().getResource("/glowna/Glowna-morning.csv").toURI());
        afternoonFile = new File(getClass().getResource("/glowna/Glowna-afternoon.csv").toURI());
        eveningFile = new File(getClass().getResource("/glowna/Glowna-evening.csv").toURI());

        morningFileTest = new File(getClass().getResource("/glowna/test/Glowna-morning.csv").toURI());
        afternoonFileTest = new File(getClass().getResource("/glowna/test/Glowna-afternoon.csv").toURI());
        eveningFileTest = new File(getClass().getResource("/glowna/test/Glowna-evening.csv").toURI());

        testNetwork(morningFile, morningFileTest);
        testNetwork(afternoonFile, afternoonFileTest);
        testNetwork(eveningFile, eveningFileTest);



        morningFile = new File(getClass().getResource("/kusocinskiego/Kusocinskiego-morning.csv").toURI());
        afternoonFile = new File(getClass().getResource("/kusocinskiego/Kusocinskiego-afternoon.csv").toURI());
        eveningFile = new File(getClass().getResource("/kusocinskiego/Kusocinskiego-evening.csv").toURI());

        morningFileTest = new File(getClass().getResource("/kusocinskiego/test/Kusocinskiego-morning.csv").toURI());
        afternoonFileTest = new File(getClass().getResource("/kusocinskiego/test/Kusocinskiego-afternoon.csv").toURI());
        eveningFileTest = new File(getClass().getResource("/kusocinskiego/test/Kusocinskiego-evening.csv").toURI());

        testNetwork(morningFile, morningFileTest);
        testNetwork(afternoonFile, afternoonFileTest);
        testNetwork(eveningFile, eveningFileTest);

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
