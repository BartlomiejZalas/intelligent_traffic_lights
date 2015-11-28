package com.zalas.traffic.prediction.prediction.network;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NeuralNetworkPredictorFactoryTest {

    private NeuralNetworkPredictorFactory neuralNetworkPredictorFactory;

    @Before
    public void setUp() throws Exception {
        this.neuralNetworkPredictorFactory = new NeuralNetworkPredictorFactory();
    }

    @Test
    public void create_shouldCreateNNPredictorWithDefinedNumberOfLearningElementsAndInputData() throws Exception {
        int amountOfTrainingElements = 4;
        File inputData = new File(getClass().getResource("/testRawTestData.csv").toURI());

        NeuralNetworkPredictor neuralNetworkPredictor = neuralNetworkPredictorFactory.create(inputData, amountOfTrainingElements);

        assertNotNull(neuralNetworkPredictor);
        assertNotNull(neuralNetworkPredictor.getNormalizedValues());
        assertEquals(neuralNetworkPredictor.getNeuralNetwork().getInputsCount(), amountOfTrainingElements);
    }
}