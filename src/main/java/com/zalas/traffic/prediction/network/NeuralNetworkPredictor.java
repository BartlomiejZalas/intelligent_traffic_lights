package com.zalas.traffic.prediction.network;

import com.zalas.traffic.prediction.normalization.NormalizedValues;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;

import java.io.IOException;
import java.util.ArrayList;

public class NeuralNetworkPredictor {

    private int slidingWindowSize;
    private DataSet learningData;
    private NormalizedValues normalizedValues;
    private NeuralNetwork<BackPropagation> neuralNetwork;

    private static final int MAX_ITERATIONS = 1000;
    private static final double LEARNING_RATE = 0.001;
    private static final double MAX_ERROR = 0.001;

    public NeuralNetworkPredictor(int slidingWindowSize, NormalizedValues normalizedValues) {
        this.slidingWindowSize = slidingWindowSize;
        this.normalizedValues = normalizedValues;
        this.learningData = new DataSet(slidingWindowSize, 1);
        this.neuralNetwork = createNeuralNetwork();
    }

    public void train() throws IOException {
        prepareData();
        trainNetwork();
    }

    public double getPrediction(double... values) {
        for (int i = 0; i < values.length; i++) {
            values[i] = normalizedValues.normalizeValue(values[i]);
        }
        neuralNetwork.setInput(values);
        neuralNetwork.calculate();
        return normalizedValues.deNormalizeValue(neuralNetwork.getOutput()[0]);
    }

    private void prepareData() throws IOException {
        ArrayList<Double> values = normalizedValues.normalizeValues();
        for (int i = 0; i < values.size() - slidingWindowSize; i++) {
            learningData.addRow(new DataSetRow(createTrainingArray(values, i), new double[]{values.get(i + slidingWindowSize)}));
        }
    }

    private double[] createTrainingArray(ArrayList<Double> normalizedValues, int i) {
        double[] trainingArray = new double[slidingWindowSize];
        for (int w = 0; w < slidingWindowSize; w++) {
            trainingArray[w] = normalizedValues.get(i + w);
        }
        return trainingArray;
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

    private void trainNetwork() {
        neuralNetwork.learn(learningData);
    }

    public NeuralNetwork<BackPropagation> getNeuralNetwork() {
        return neuralNetwork;
    }

    public void setNeuralNetwork(NeuralNetwork<BackPropagation> neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    public NormalizedValues getNormalizedValues() {
        return normalizedValues;
    }

    public DataSet getLearningData() {
        return learningData;
    }
}

