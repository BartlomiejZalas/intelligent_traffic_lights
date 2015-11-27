package com.zalas.traffic.ui;

import com.zalas.traffic.prediction.NeuralNetworkPredictionTester;
import com.zalas.traffic.prediction.network.NeuralNetworkPredictor;
import com.zalas.traffic.prediction.network.NeuralNetworkPredictorFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.zalas.traffic.utils.Utils.*;

public class Launcher {

    public static void main(String[] args) throws Exception {
        Launcher launcher = new Launcher();
        System.out.println("Creating network");
        launcher.createPredictors();
        System.out.println("Testing network");
        launcher.testPredictors();
    }

    public void createPredictors() throws Exception {
        for (String dataFileName : getDataFileNames()) {
            File trainingDataFile = new File(getClass().getResource("/data/" + dataFileName + ".csv").toURI());
            NeuralNetworkPredictor predictor = createPredictor(trainingDataFile);
            predictor.save(PREDICTORS_OUTPUT_DIRECTORY + dataFileName + ".nnp");
            System.out.println(dataFileName + " predictor saved in " + PREDICTORS_OUTPUT_DIRECTORY);
        }
    }

    public void testPredictors() throws Exception {
        for (String dataFileName : getDataFileNames()) {
            File trainingDataFile = new File(getClass().getResource("/data/" + dataFileName + ".csv").toURI());
            File testDataFile = new File(getClass().getResource("/test/" + dataFileName + ".csv").toURI());
            NeuralNetworkPredictor predictor = NeuralNetworkPredictor.load(PREDICTORS_OUTPUT_DIRECTORY + dataFileName + ".nnp");
            double accuracy = testPredictor(trainingDataFile, testDataFile, predictor);
            System.out.println(dataFileName + " predictor test completed. Accuracy :" + accuracy + "%");
        }
    }

    private List<String> getDataFileNames() {
        List<String> files = new ArrayList<>();
        for (String street : STREETS) {
            for (String time : TIMES_OF_DAY) {
                files.add(street + "-" + time);
            }
        }
        return files;
    }

    private NeuralNetworkPredictor createPredictor(File dataFile) throws IOException, URISyntaxException {
        NeuralNetworkPredictorFactory factory = new NeuralNetworkPredictorFactory();
        NeuralNetworkPredictor predictor = factory.create(dataFile, 100);
        long time = System.nanoTime();
        predictor.train();
        System.out.println("Learning time: " + ((double)(System.nanoTime() - time) / 1000000000));
        return predictor;
    }

    private double testPredictor(File dataFile, File testFile, NeuralNetworkPredictor predictor) throws URISyntaxException, IOException {
        NeuralNetworkPredictionTester neuralNetworkPredictionTester = new NeuralNetworkPredictionTester(dataFile, testFile, predictor);
        return neuralNetworkPredictionTester.checkAccuracyAndGenerateReport();
    }

}
