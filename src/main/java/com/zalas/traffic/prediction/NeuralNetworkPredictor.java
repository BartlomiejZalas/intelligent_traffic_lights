package com.zalas.traffic.prediction;

import com.zalas.traffic.io.CsvLineReader;
import com.zalas.traffic.prediction.learning_data.LearningData;
import com.zalas.traffic.prediction.normalization.Normalizer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class NeuralNetworkPredictor {

    private int slidingWindowSize;
    private File rawDataFile;

    private LearningData learningData;

    private Normalizer normalizer;
    private static final int MAX_ITERATIONS = 1000;
    private static final double LEARNING_RATE = 0.001;
    private static final double MAX_ERROR = 0.001;

    public NeuralNetworkPredictor(int slidingWindowSize, File rawDataFile) {
        this.rawDataFile = rawDataFile;
        this.slidingWindowSize = slidingWindowSize;
        this.learningData = new LearningData(slidingWindowSize);

    }

    public void run() throws IOException {
        NeuralNetwork<BackPropagation> neuralNetwork = createNeuralNetwork();

        prepareData();
        trainNetwork(neuralNetwork);
        testNetwork(neuralNetwork);
    }

    private void prepareData() throws IOException {

        CsvLineReader csvLineReader = new CsvLineReader();
        ArrayList<Double> values = csvLineReader.getValuesFromColumn(rawDataFile, 1);

        normalizer = new Normalizer(values);
        ArrayList<Double> normalizedValues = normalizer.normalizeValues();

        for (int i = 0; i < normalizedValues.size() - slidingWindowSize; i++) {
            learningData.addRow(createTrainingArray(normalizedValues, i), normalizedValues.get(i + slidingWindowSize));
        }
    }

    private NeuralNetwork<BackPropagation> createNeuralNetwork() {
        NeuralNetwork<BackPropagation> neuralNetwork = new MultiLayerPerceptron(slidingWindowSize, 2 * slidingWindowSize + 1, 1);
        SupervisedLearning learningRule = neuralNetwork.getLearningRule();
        learningRule.setMaxError(MAX_ERROR);
        learningRule.setLearningRate(LEARNING_RATE);
        learningRule.setMaxIterations(MAX_ITERATIONS);
        learningRule.addListener(new DebugEventListener());
        return neuralNetwork;
    }

    private void trainNetwork(NeuralNetwork<BackPropagation> neuralNetwork) {
        neuralNetwork.learn(loadTrainingData());
    }

    private DataSet loadTrainingData() {
        DataSet trainingSet = new DataSet(slidingWindowSize, 1);
        for (int i = 0; i < learningData.countRows(); i++) {
            double[] trainValues = learningData.getRow(i).getDataToLearn();
            double expectedValue[] = new double[]{learningData.getRow(i).getExpectedValue()};
            trainingSet.addRow(new DataSetRow(trainValues, expectedValue));
        }
        return trainingSet;
    }

    private double[] createTrainingArray(ArrayList<Double> normalizedValues, int i) {
        double[] trainingArray = new double[slidingWindowSize];
        for (int w = 0; w < slidingWindowSize; w++) {
            trainingArray[w] = normalizedValues.get(i + w);
        }
        return trainingArray;
    }

    private void testNetwork(NeuralNetwork<BackPropagation> neuralNetwork) {
        neuralNetwork.setInput(
                normalizer.normalizeValue(2056.15),
                normalizer.normalizeValue(2061.02),
                normalizer.normalizeValue(2086.24),
                normalizer.normalizeValue(2067.89),
                normalizer.normalizeValue(2059.69)
        );
        neuralNetwork.calculate();

        double[] networkOutput = neuralNetwork.getOutput();
        System.out.println("Expected value  : " + 2066.96);
        System.out.println("Predicted value : " + normalizer.deNormalizeValue(networkOutput[0]));
    }

}

