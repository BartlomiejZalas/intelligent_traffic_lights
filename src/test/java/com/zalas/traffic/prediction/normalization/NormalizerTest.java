package com.zalas.traffic.prediction.normalization;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NormalizerTest {

    private static final double MIN_VALUE = 1.0;
    private static final double MAX_VALUE = 6.0;
    private final static ArrayList<Double> VALUES =
            new ArrayList<Double>(Arrays.asList(3.8, MIN_VALUE, 2.5, 2.6, MAX_VALUE, 5.0));

    private Normalizer normalizer;

    @Test
    public void normalizeValue_shouldReturnNormalizedValue() throws Exception {
        normalizer = new Normalizer(VALUES);
        assertEquals(0.02, normalizer.normalizeValue(MIN_VALUE), 0.1);
        assertEquals(0.7, normalizer.normalizeValue(MAX_VALUE), 0.1);
    }

    @Test
    public void deNormalizeValue_shouldReturnDeNormalizedValue() throws Exception {
        normalizer = new Normalizer(VALUES);
        assertEquals(MIN_VALUE, normalizer.deNormalizeValue(normalizer.normalizeValue(MIN_VALUE)), 0.1);
        assertEquals(MAX_VALUE, normalizer.deNormalizeValue(normalizer.normalizeValue(MAX_VALUE)), 0.1);
    }

    @Test
    public void normalizeValues_shouldReturnValuesAsNormalized_whenMinIsDotOneAndMaxIsDotNine() throws Exception {
        normalizer = new Normalizer(VALUES);
        assertNormalization(normalizer.normalizeValues());
    }

    @Test
    public void normalizeValues_shouldReturnValuesAsNormalized_whenMinIsZeroAndMaxIsOne() throws Exception {
        normalizer = new Normalizer(VALUES);
        assertNormalization(normalizer.normalizeValues());
    }

    private void assertNormalization(ArrayList<Double> normalized) {
        for (double normalizedValue : normalized) {
            assertTrue(normalizedValue >= 0);
            assertTrue(normalizedValue <= 1);
        }
    }
}