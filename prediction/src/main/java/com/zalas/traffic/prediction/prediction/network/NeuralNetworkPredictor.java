package com.zalas.traffic.prediction.prediction.network;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import com.zalas.traffic.prediction.prediction.normalization.NormalizedValues;

import java.io.*;
import java.util.ArrayList;

public class NeuralNetworkPredictor implements Serializable {

    private int noOfInputs;
    private DataSet learningData;
    private NormalizedValues normalizedValues;
    private NeuralNetwork<BackPropagation> neuralNetwork;

    private static final int MAX_ITERATIONS = 1000;
    private static final double LEARNING_RATE = 0.001;
    private static final double MAX_ERROR = 0.0035;

    public NeuralNetworkPredictor(int noOfInputs, NormalizedValues normalizedValues) {
        this.noOfInputs = noOfInputs;
        this.normalizedValues = normalizedValues;
        this.learningData = new DataSet(noOfInputs, 1);
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
        for (int i = 0; i < values.size() - noOfInputs; i++) {
            learningData.addRow(new DataSetRow(createTrainingArray(values, i), new double[]{values.get(i + noOfInputs)}));
        }
    }

    private double[] createTrainingArray(ArrayList<Double> normalizedValues, int i) {
        double[] trainingArray = new double[noOfInputs];
        for (int w = 0; w < noOfInputs; w++) {
            trainingArray[w] = normalizedValues.get(i + w);
        }
        return trainingArray;
    }

    private NeuralNetwork<BackPropagation> createNeuralNetwork() {
        NeuralNetwork<BackPropagation> neuralNetwork = new MultiLayerPerceptron(noOfInputs, noOfInputs / 4, 1);
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

    public void save(String filePath) throws IOException {
        ObjectOutputStream out = null;
        try {
            File file = new File(filePath);
            out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
            out.writeObject(this);
            out.flush();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static NeuralNetworkPredictor load(String filePath) throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
            return (NeuralNetworkPredictor) in.readObject();
        } finally {
            if (in != null) {
                in.close();
            }
        }
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

    public int getNoOfInputs() {
        return noOfInputs;
    }

    public static int getMaxIterations() {
        return MAX_ITERATIONS;
    }

    public static double getLearningRate() {
        return LEARNING_RATE;
    }

    public static double getMaxError() {
        return MAX_ERROR;
    }
}

