package com.zalas.traffic.dynamic.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.inject.internal.util.$Lists.newArrayList;

public class DataSet {

    private static final int INPUTS_COUNT = 4;
    private static final int OUTPUTS_COUNT = 15;

    private List<DataRow> dataRows = new ArrayList<>();

    public DataSet(List<DataRow> dataRows) {
        this.dataRows = dataRows;
    }

    public double[][] getInputsAsNormalizedArray() {
        double[][] result = new double[dataRows.size()][];
        for (int i = 0; i < dataRows.size(); i++) {
            result[i] = normalizeArray(dataRows.get(i).getInputs());
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

    private double[] normalizeArray(double[] inputs) {
        double[] result = new double[inputs.length];
        for (int i = 0; i < inputs.length; i++) {
            result[i] = normalizeValue(inputs[i]);
        }
        return result;
    }

    private double normalizeValue(double input) {
        return input / INPUTS_COUNT;
    }

    private double normalizeOutput(double output) {
        return output / OUTPUTS_COUNT;
    }

    public List<DataRow> getDataRows() {
        return dataRows;
    }
}
