package com.zalas.traffic.dynamic;

import com.zalas.traffic.dynamic.data.DataSet;

public class Main {

    public static void main(String[] args) throws Exception {
        DataSet dataSet = new DataSet();
        NeuralNetwork neuralNetwork = new NeuralNetwork(dataSet);
        neuralNetwork.create();
        neuralNetwork.train();
        double[] testInputs = {4, 1, 2, 1};
        double result = neuralNetwork.getOutput(testInputs);
        neuralNetwork.close();

        System.out.println("Output: "+ result + ", Expected: "+2);

        new ReportPrinter(dataSet, testInputs, result).createReport();

        System.out.println("Report saved");
    }
}
