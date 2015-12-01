package com.zalas.traffic.dynamic.data;

import java.util.ArrayList;
import java.util.List;

public class DataSet {

    private static final int INPUTS_COUNT = 4;
    private static final int OUTPUTS_COUNT = 45;


    private List<DataRow> dataRows = new ArrayList<DataRow>();

    public DataSet() {
        dataRows.add(new DataRow(new double[]{4, 1, 2, 1}, 2));
        dataRows.add(new DataRow(new double[]{1, 4, 2, 2}, 3));
        dataRows.add(new DataRow(new double[]{1, 2, 4, 0}, 0));
        dataRows.add(new DataRow(new double[]{1, 1, 1, 4}, 1));
        dataRows.add(new DataRow(new double[]{3, 4, 3, 1}, 3));
        dataRows.add(new DataRow(new double[]{4, 0, 4, 2}, 6));
        dataRows.add(new DataRow(new double[]{4, 2, 3, 3}, 2));
        dataRows.add(new DataRow(new double[]{3, 4, 2, 3}, 3));
        dataRows.add(new DataRow(new double[]{3, 3, 4, 3}, 0));
        dataRows.add(new DataRow(new double[]{1, 3, 2, 1}, 3));
    }

    public DataSet(List<DataRow> dataRows) {
        this.dataRows = dataRows;
    }

    public double[][] getInputsAsArray() {
        double[][] result = new double[dataRows.size()][];
        for (int i = 0; i < dataRows.size(); i++) {
            result[i] = normalizeArray(dataRows.get(i).getInputs());
        }
        return result;
    }

    private double[] normalizeArray(double[] inputs) {
        double[] result = new double[inputs.length];
        for(int i = 0; i < inputs.length; i++) {
            result[i] = normalizeValue(inputs[i]);
        }
        return result;
    }

    private double normalizeValue(double input) {
        return input/INPUTS_COUNT;
    }

    public double[][] getOutputAsArray() {
        double[][] result = new double[dataRows.size()][];
        for (int i = 0; i < dataRows.size(); i++) {
            result[i] = new double[]{normalizeOutput(dataRows.get(i).getOutput())};
        }
        return result;
    }

    private double normalizeOutput(double output) {
        return output/OUTPUTS_COUNT;
    }

    public List<DataRow> getDataRows() {
        return dataRows;
    }
}
