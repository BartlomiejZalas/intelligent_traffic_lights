package com.zalas.traffic.prediction.normalization;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class NormalizedValuesTest {

    private static final double MIN_VALUE = 1.0;
    private static final double MAX_VALUE = 6.0;
    private final static ArrayList<Double> VALUES =
            new ArrayList<Double>(Arrays.asList(3.8, MIN_VALUE, 2.5, 2.6, MAX_VALUE, 5.0));

    private NormalizedValues normalizedValues;

    @Test
    public void normalizeValue_shouldReturnNormalizedValue() throws Exception {
        normalizedValues = new NormalizedValues(VALUES);
        assertTrue(normalizedValues.normalizeValue(3.8) > 0.1);
        assertTrue(normalizedValues.normalizeValue(3.8) < 0.9);
    }

    @Test
    public void deNormalizeValue_shouldReturnDeNormalizedValue() throws Exception {
        normalizedValues = new NormalizedValues(VALUES);
        assertTrue(normalizedValues.deNormalizeValue(0.5) >= MIN_VALUE);
        assertTrue(normalizedValues.deNormalizeValue(0.5) <= MAX_VALUE);
    }

    @Test
    public void normalizeValues_shouldReturnValuesAsNormalized_whenMinIsDotOneAndMaxIsDotNine() throws Exception {
        normalizedValues = new NormalizedValues(VALUES);
        assertNormalization(normalizedValues.normalizeValues());
    }

    @Test
    public void normalizeValues_shouldReturnValuesAsNormalized_whenMinIsZeroAndMaxIsOne() throws Exception {
        normalizedValues = new NormalizedValues(VALUES);
        assertNormalization(normalizedValues.normalizeValues());
    }

    private void assertNormalization(ArrayList<Double> normalized) {
        for (double normalizedValue : normalized) {
            assertTrue(normalizedValue >= 0);
            assertTrue(normalizedValue <= 1);
        }
    }
}