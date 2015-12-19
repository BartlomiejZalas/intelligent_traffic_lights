package com.zalas.traffic.dynamic.data;

import com.zalas.traffic.dynamic.normalization.TrafficLevelsNormalizator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.inject.internal.util.$Lists.newArrayList;

public class DataSet implements Serializable {

    private static TrafficLevelsNormalizator normalizator = new TrafficLevelsNormalizator();

    private List<DataRow> dataRows = new ArrayList<>();

    public DataSet(List<DataRow> dataRows) {
        this.dataRows = dataRows;
    }

    public double[][] getInputsAsNormalizedArray() {
        double[][] result = new double[dataRows.size()][];
        for (int i = 0; i < dataRows.size(); i++) {
            result[i] = normalizeInputArray(dataRows.get(i).getInputs());
        }
        return result;
    }

    public double[][] getOutputAsNormalizedArray() {
        double[][] result = new double[dataRows.size()][];
        for (int i = 0; i < dataRows.size(); i++) {
            result[i] = new double[]{normalizeOutput(dataRows.get(i).getOutput())};
        }
        return result;
    }

    public List<List<Double>> getInputsAsList() {
        List<List<Double>> inputs = newArrayList();
        for (int i = 0; i < dataRows.size(); i++) {
            inputs.add(
                    Arrays.stream(dataRows.get(i).getInputs())
                            .boxed()
                            .collect(Collectors.toList())
            );
        }
        return inputs;
    }

    public List<Double> getOutputAsList() {
        List<Double> outputs = newArrayList();
        for (int i = 0; i < dataRows.size(); i++) {
            outputs.add(dataRows.get(i).getOutput());
        }
        return outputs;
    }

    private double[] normalizeInputArray(double[] inputs) {
        double[] result = new double[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            result[i] = normalizeInput(inputs[i]);
        }
        return result;
    }

    private double normalizeInput(double input) {
        return normalizator.normalizeTrafficStatus((int) input);
    }

    private double normalizeOutput(double output) {
        return normalizator.normalizeLightCycle((int) output);
    }

    public List<DataRow> getDataRows() {
        return dataRows;
    }
}
