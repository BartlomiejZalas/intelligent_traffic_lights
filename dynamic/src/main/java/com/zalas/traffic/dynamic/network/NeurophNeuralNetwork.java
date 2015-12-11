package com.zalas.traffic.dynamic.network;

import com.zalas.traffic.dynamic.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.SupervisedLearning;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;

public class NeurophNeuralNetwork implements NeuralNetwork {

    private DataSet dataSet;
    private org.neuroph.core.NeuralNetwork<BackPropagation> neuralNetwork;

    private static final int MAX_ITERATIONS = 10000;
    private static final double LEARNING_RATE = 0.01;
    private static final double MAX_ERROR = 0.0001;

    public NeurophNeuralNetwork(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public void create() {
        neuralNetwork = new MultiLayerPerceptron(4, 16, 8, 4, 2, 1);
        SupervisedLearning learningRule = neuralNetwork.getLearningRule();
        learningRule.setMaxError(MAX_ERROR);
        learningRule.setLearningRate(LEARNING_RATE);
        learningRule.setMaxIterations(MAX_ITERATIONS);
        learningRule.addListener(new DebugEventListener());
    }

    @Override
    public void train() {
        org.neuroph.core.data.DataSet neurophDataSet = convertDataSet();
        neuralNetwork.learn(neurophDataSet);
    }

    @Override
    public double getOutput(double[] inputs) {
        neuralNetwork.setInput(inputs);
        neuralNetwork.calculate();
        return neuralNetwork.getOutput()[0];
    }

    @Override
    public void close() {
    }


    private org.neuroph.core.data.DataSet convertDataSet() {
        org.neuroph.core.data.DataSet neuroptDataSet = new org.neuroph.core.data.DataSet(4, 1);
        double[][] inputs = dataSet.getInputsAsNormalizedArray();
        double[][] outputs = dataSet.getOutputAsNormalizedArray();
        for (int i = 0; i < inputs.length; i++) {
            neuroptDataSet.addRow(new DataSetRow(inputs[i], outputs[i]));
        }
        return neuroptDataSet;
    }
}
