package com.zalas.traffic.prediction.prediction.learning_data;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LearningDataTest {

    private static final int NUMBER_OF_LEARNING_ELEMENTS_IN_ROW = 3;
    private LearningData learningData;

    @Before
    public void setUp() throws Exception {
        learningData = new LearningData(NUMBER_OF_LEARNING_ELEMENTS_IN_ROW);
    }

    @Test
    public void countRows_shouldReturnZero_whenThereIsNoRows() throws Exception {
        assertEquals(0, learningData.countRows());
    }

    @Test
    public void countRows_shouldReturnOne_whenThereIsOneRow() throws Exception {
        learningData.addRow(new double[]{1.1, 2.2, 3.3}, 0);
        assertEquals(1, learningData.countRows());
    }

    @Test(expected = LearningDataException.class)
    public void addLearningRow_shouldThrowException_whenLearningDataHasDifferentNumberOfElementsThanDefined() throws Exception {
        learningData.addRow(new double[]{2.0, 1.5}, 8);
    }

    @Test
    public void addLearningRow_shouldAddLearningDataAndExpectedResult_whenThereIsOneRow() throws Exception {
        double[] dataToLearn = new double[]{1.0, 2.7, 3.5};
        double expectedResult = 3.0;

        learningData.addRow(dataToLearn, expectedResult);

        assertEquals(1, learningData.countRows());
        assertEquals(dataToLearn, learningData.getRow(0).getDataToLearn());
        assertEquals(expectedResult, learningData.getRow(0).getExpectedValue(), 1);
    }
}