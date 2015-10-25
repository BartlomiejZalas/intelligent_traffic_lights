package com.zalas.traffic.prediction;

import com.zalas.traffic.prediction.normalization.NormalizedValues;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NeuralNetworkPredictorTest {

    @Mock
    private NormalizedValues normalizer;
    @Mock
    private NeuralNetwork neuralNetwork;

    private static final int LEARNING_SET_SIZE = 3;
    private static final double OUTPUT_VALUE = 8.5;

    @Test
    public void train_shouldPrepareLearningDataAndTrainNetwork() throws Exception {
        ArrayList<Double> normalizedValues = createNormalizedValues();
        NeuralNetworkPredictor neuralNetworkPredictor = new NeuralNetworkPredictor(LEARNING_SET_SIZE, normalizer);
        neuralNetworkPredictor.setNeuralNetwork(neuralNetwork);

        neuralNetworkPredictor.train();

        assertEquals(neuralNetworkPredictor.getLearningData().getRows().size(), normalizedValues.size() - LEARNING_SET_SIZE);
        verify(neuralNetwork).learn(any(DataSet.class));
    }

    @Test
    public void getPrediction_shouldDelegateToNeuralNetworkAndReturnValue() throws Exception {
        NeuralNetworkPredictor neuralNetworkPredictor = new NeuralNetworkPredictor(LEARNING_SET_SIZE, normalizer);
        given(neuralNetwork.getOutput()).willReturn(new double[]{OUTPUT_VALUE});
        given(normalizer.deNormalizeValue(OUTPUT_VALUE)).willReturn(OUTPUT_VALUE);
        neuralNetworkPredictor.setNeuralNetwork(neuralNetwork);
        neuralNetworkPredictor.train();

        double result = neuralNetworkPredictor.getPrediction(new double[]{1});

        verify(neuralNetwork).calculate();
        verify(neuralNetwork).getOutput();
        assertEquals(OUTPUT_VALUE, result, 0.01);

    }

    private ArrayList<Double> createNormalizedValues() {
        ArrayList<Double> normalizedValues = new ArrayList<>(Arrays.asList(0.1, 0.2, 0.3, 0.4, 0.5));
        given(normalizer.normalizeValues()).willReturn(normalizedValues);
        return normalizedValues;
    }
}