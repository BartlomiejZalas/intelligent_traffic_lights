package com.zalas.traffic.prediction;

import com.zalas.traffic.io.CsvLineReader;
import com.zalas.traffic.prediction.learning_data.LearningData;
import com.zalas.traffic.prediction.normalization.Normalizer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class NeuralNetworkPredictor {

    private int slidingWindowSize;
    private File rawDataFile;

    private LearningData learningData;
    private NeuralNetwork<BackPropagation> neuralNetwork;
    private Normalizer normalizer;

    public NeuralNetworkPredictor(int slidingWindowSize, File rawDataFile) {
        this.rawDataFile = rawDataFile;
        this.slidingWindowSize = slidingWindowSize;
        this.learningData = new LearningData(slidingWindowSize);
    }

    public void run() throws Exception {
        System.out.println("Preparing data");
        prepareData();

        System.out.println("Training starting");
        trainNetwork();

        System.out.println("Testing network");
        testNetwork();
    }

    void prepareData() throws Exception {

        CsvLineReader csvLineReader = new CsvLineReader();
        ArrayList<Double> values = csvLineReader.getValuesFromColumn(rawDataFile, 1);
        normalizer = new Normalizer(values);
        ArrayList<Double> normalizedValues = normalizer.normalizeValues();


        for (int i = 0; i < normalizedValues.size() - (slidingWindowSize + 1); i++) {
            double[] trainingArray = new double[slidingWindowSize];
            for (int w = 0; w < slidingWindowSize; w++) {
                trainingArray[w] = normalizedValues.get(i + w);
            }
            System.out.println(Arrays.toString(trainingArray) + "->" + normalizedValues.get(i + slidingWindowSize));
            learningData.addRow(trainingArray, normalizedValues.get(i + slidingWindowSize));
        }

    }

    void trainNetwork() throws IOException, URISyntaxException {
        neuralNetwork = new MultiLayerPerceptron(slidingWindowSize, 2 * slidingWindowSize + 1, 1);

        int maxIterations = 1000;
        double learningRate = 0.001;
        double maxError = 0.001;
        SupervisedLearning learningRule = neuralNetwork.getLearningRule();
        learningRule.setMaxError(maxError);
        learningRule.setLearningRate(learningRate);
        learningRule.setMaxIterations(maxIterations);
        learningRule.addListener(new LearningEventListener() {
            public void handleLearningEvent(LearningEvent learningEvent) {
                SupervisedLearning rule = (SupervisedLearning) learningEvent.getSource();
                System.out.println("Network error for interation " + rule.getCurrentIteration() + ": "+ rule.getTotalNetworkError());
            }
        });

        DataSet trainingSet = loadTrainingData();
        neuralNetwork.learn(trainingSet);
    }

    DataSet loadTrainingData() throws IOException {
        DataSet trainingSet = new DataSet(slidingWindowSize, 1);
        for (int i = 0; i < learningData.countRows(); i++) {
            double[] trainValues = learningData.getRow(i).getDataToLearn();
            double expectedValue[] = new double[]{learningData.getRow(i).getExpectedValue()};
            trainicd cd ngSet.addRow(new DataSetRow(trainValues, expectedValue));
        }
        return trainingSet;
    }

    void testNetwork() throws URISyntaxException {
        neuralNetwork.setInput(normalizer.normalizeValue(2056.15),
                normalizer.normalizeValue(2061.02), normalizer.normalizeValue(2086.24),
                normalizer.normalizeValue(2067.89), normalizer.normalizeValue(2059.69));

        neuralNetwork.calculate();
        double[] networkOutput = neuralNetwork.getOutput();
        System.out.println("Expected value  : 2066.96");
        System.out.println("Predicted value : " + normalizer.deNormalizeValue(networkOutput[0]));
    }

}

