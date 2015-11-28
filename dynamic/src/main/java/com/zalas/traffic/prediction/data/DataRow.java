package com.zalas.traffic.prediction.data;

public class DataRow {

    private double[] inputs = new double[4];
    private double output;

    public DataRow(double[] inputs, double output) {
        this.inputs = inputs;
        this.output = output;
    }

    public double[] getInputs() {
        return inputs;
    }

    public double getOutput() {
        return output;
    }
}
