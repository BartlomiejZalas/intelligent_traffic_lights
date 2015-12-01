package com.zalas.traffic.dynamic.prediction.normalization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class NormalizedValues implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NormalizedValues)) return false;

        NormalizedValues that = (NormalizedValues) o;

        if (Double.compare(that.max, max) != 0) return false;
        if (Double.compare(that.min, min) != 0) return false;
        if (deNormalizedValues != null ? !deNormalizedValues.equals(that.deNormalizedValues) : that.deNormalizedValues != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(max);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(min);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (deNormalizedValues != null ? deNormalizedValues.hashCode() : 0);
        return result;
    }
}
