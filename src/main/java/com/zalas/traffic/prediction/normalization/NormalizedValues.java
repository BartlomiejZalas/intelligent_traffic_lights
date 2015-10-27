package com.zalas.traffic.prediction.normalization;

import java.util.ArrayList;
import java.util.Collections;

public class NormalizedValues {

    private double max;
    private double min;

    private ArrayList<Double> deNormalizedValues;

    public NormalizedValues(ArrayList<Double> deNormalizedValues) {
        this.deNormalizedValues = deNormalizedValues;
        findMax(deNormalizedValues);
        findMin(deNormalizedValues);
    }

    public ArrayList<Double> normalizeValues() {
        ArrayList<Double> normalizedValues = new ArrayList<>();
        for (double value : deNormalizedValues) {
            normalizedValues.add(normalizeValue(value));
        }
        return normalizedValues;
    }

    public double normalizeValue(double input) {
        return (((input - min) / (max - min)) * 0.8) + 0.1;
    }

    public double deNormalizeValue(double input) {
        return ((min*(0.9 - input)) + (max * (input-0.1)))/0.8;
    }

    private void findMin(ArrayList<Double> deNormalizedValues) {
        min = Collections.min(deNormalizedValues);
    }

    private void findMax(ArrayList<Double> deNormalizedValues) {
        max = Collections.max(deNormalizedValues);
    }
}
