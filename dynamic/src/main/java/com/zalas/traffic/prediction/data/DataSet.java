package com.zalas.traffic.prediction.data;

import java.util.ArrayList;
import java.util.Arrays;

public class DataSet {

    private ArrayList<DataRow> dataRows = new ArrayList<DataRow>();

    public DataSet() {
        dataRows.add(new DataRow(new double[]{1, 0.25, 0.5, 0.25}, 0.3));
        dataRows.add(new DataRow(new double[]{0.25, 1, 0.5, 0.5}, 0.4));
        dataRows.add(new DataRow(new double[]{0.25, 2, 1, 0}, 0.1));
        dataRows.add(new DataRow(new double[]{0.25, 0.25, 0.25, 1}, 0.2));
        dataRows.add(new DataRow(new double[]{0.75, 1, 0.75, 1}, 0.5));
        dataRows.add(new DataRow(new double[]{1, 0, 1, 2}, 1));
        dataRows.add(new DataRow(new double[]{1, 0.5, 0.75, 0.75}, 0.3));
        dataRows.add(new DataRow(new double[]{0.75, 1, 0.5, 0.75}, 0.4));
        dataRows.add(new DataRow(new double[]{0.75, 0.75, 1, 0.75}, 0.1));
        dataRows.add(new DataRow(new double[]{0.25, 0.75, 0.5, 1}, 0.2));

    }

    public double[][] getInputsAsArray() {
        double[][] result = new double[dataRows.size()][];
        for (int i = 0; i < dataRows.size(); i++) {
            result[i] = dataRows.get(i).getInputs();
        }
        return result;
    }

    public double[][] getOutputAsArray() {
        double[][] result = new double[dataRows.size()][];
        for (int i = 0; i < dataRows.size(); i++) {
            result[i] = new double[]{dataRows.get(i).getOutput()};
        }
        return result;
    }

    public static void main(String[] args) {
        double[][] arr = new DataSet().getInputsAsArray();
        for(double[] subAr : arr) {
            System.out.print(Arrays.toString(subAr)+",");
        }

        System.out.println("");

        double[][] arrO = new DataSet().getOutputAsArray();
        for(double[] subAr : arrO) {
            System.out.print(Arrays.toString(subAr));
        }
    }

    public ArrayList<DataRow> getDataRows() {
        return dataRows;
    }
}
