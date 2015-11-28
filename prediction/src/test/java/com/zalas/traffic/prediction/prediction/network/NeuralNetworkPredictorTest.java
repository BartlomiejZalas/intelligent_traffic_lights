package com.zalas.traffic.prediction.prediction.network;

import com.zalas.traffic.prediction.prediction.normalization.NormalizedValues;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import static com.google.common.collect.Lists.newArrayList;
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
    private File test_file;

    @Before
    public void setUp() throws Exception {
        test_file = File.createTempFile("predictor", "nnp");;
        test_file.deleteOnExit();
    }

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

    @Test
    public void save_shouldSerializeAndSaveFileWithPredictor() throws Exception {
        NormalizedValues normalizer = new NormalizedValues(newArrayList(1.2,2.1));
        NeuralNetworkPredictor neuralNetworkPredictor = new NeuralNetworkPredictor(LEARNING_SET_SIZE, normalizer);

        neuralNetworkPredictor.save(test_file.getAbsolutePath());

        assertTrue(test_file.exists());
    }

    @Test
    public void load_shouldUnserializeAndLoadFileWithPredictor() throws Exception {
        NormalizedValues normalizer = new NormalizedValues(newArrayList(1.2,2.1));
        NeuralNetworkPredictor neuralNetworkPredictor = new NeuralNetworkPredictor(LEARNING_SET_SIZE, normalizer);
        neuralNetworkPredictor.save(test_file.getAbsolutePath());

        NeuralNetworkPredictor loadedPredictor = NeuralNetworkPredictor.load(test_file.getAbsolutePath());
        assertEquals(LEARNING_SET_SIZE, loadedPredictor.getNoOfInputs());
        assertEquals(normalizer, loadedPredictor.getNormalizedValues());
        assertNotNull(loadedPredictor.getLearningData());
        assertNotNull(loadedPredictor.getNeuralNetwork());
    }

    private ArrayList<Double> createNormalizedValues() {
        ArrayList<Double> normalizedValues = new ArrayList<>(Arrays.asList(0.1, 0.2, 0.3, 0.4, 0.5));
        given(normalizer.normalizeValues()).willReturn(normalizedValues);
        return normalizedValues;
    }
}