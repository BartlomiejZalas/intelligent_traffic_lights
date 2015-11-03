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


//        double prediction = predictorMorning.getPrediction(new double[]{
//                129, 127, 146, 120, 76, 91, 110, 116, 105, 111, 115, 132, 126, 115, 124, 150, 146, 129, 124, 123, 78,
//                95, 106, 118, 115, 107, 101, 145, 134, 121, 146, 126, 138, 127, 143, 114, 69, 80, 123, 119, 112, 110,
//                104, 122, 120, 109, 122, 128, 127, 111, 148, 107, 80, 98, 119, 120, 108, 114, 116, 125, 132, 116, 129,
//                133, 133, 108, 143, 124, 64, 91, 112, 119, 101, 111, 118, 131, 115, 123, 138, 133, 123, 122, 134, 106,
//                72, 81, 120, 102, 117, 108, 106, 135, 118, 115, 147, 142, 141, 125, 134, 124});
//        System.out.println("Expected value: " + 78);
//        System.out.println("Prediction:     " + prediction);


//        NeuralNetworkPredictor predictorAfternoon = factory.create(afternoonFile, 100);
//        predictorAfternoon.train();

//        prediction = predictorAfternoon.getPrediction(new double[]{
//                149, 161, 135, 135, 163, 203, 141, 125, 156, 158, 122, 122, 174, 175, 154, 149, 145, 152, 133, 136, 191,
//                178, 136, 142, 134, 153, 140, 139, 181, 183, 149, 129, 136, 153, 123, 119, 164, 208, 137, 148, 134, 142,
//                143, 141, 172, 175, 141, 134, 154, 141, 148, 133, 172, 181, 139, 140, 152, 164, 134, 125, 167, 176, 148,
//                137, 149, 154, 139, 136, 178, 209, 149, 144, 132, 146, 140, 127, 176, 175, 155, 132, 135, 158, 148, 130,
//                195, 185, 134, 146, 135, 152, 125, 127, 185, 203, 159, 134, 140, 151, 125, 139
//        });
//        System.out.println("Expected value: " + 178);
//        System.out.println("Prediction:     " + prediction);


//        NeuralNetworkPredictor predictorEvening = factory.create(eveningFile, 100);
//        predictorEvening.train();

//        prediction = predictorEvening.getPrediction(new double[]{
//                104, 85, 63, 61, 124, 126, 122, 100, 112, 80, 69, 59, 123, 146, 121, 111, 113, 87, 71, 61, 123, 120,
//                127, 119, 98, 78, 70, 54, 133, 142, 109, 104, 101, 78, 66, 58, 124, 145, 126, 104, 96, 79, 63, 55, 117,
//                138, 106, 99, 100, 86, 63, 58, 130, 144, 119, 120, 93, 75, 67, 58, 136, 132, 130, 103, 106, 78, 77, 61,
//                115, 142, 110, 104, 115, 79, 74, 50, 138, 142, 110, 104, 102, 78, 76, 49, 115, 133, 128, 100, 104, 78,
//                75, 53, 130, 142, 118, 103, 115, 83, 65, 49
//        });
//        System.out.println("Expected value: " + 119);
//        System.out.println("Prediction:     " + prediction);


        NeuralNetworkPredictor predictorMorning = factory.create(morningFile, 100);
        predictorMorning.train();

        NeuralNetworkPredictionTester neuralNetworkPredictionTester = new NeuralNetworkPredictionTester(morningFile, morningFileTest, predictorMorning);


    }


}
