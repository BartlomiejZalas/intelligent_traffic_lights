package com.zalas.traffic.dynamic;

import com.zalas.traffic.dynamic.data.DataSet;
import com.zalas.traffic.dynamic.data.DataSetFromCsvCreator;
import com.zalas.traffic.dynamic.network.NeuralNetwork;
import com.zalas.traffic.io.csv.CsvLineReader;
import com.zalas.traffic.io.report.HtmlReportWriter;

import java.io.File;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        File csvDataFile = new File(getClass().getResource("/dynamicDataSet.csv").toURI());
        DataSet dataSet = new DataSetFromCsvCreator(new CsvLineReader()).prepareDataSet(csvDataFile);
        dataSet.getDataRows().stream()
                .forEach(row -> System.out.println(Arrays.toString(row.getInputs()) + ":" + row.getOutput()));

        NeuralNetwork neuralNetwork = new NeuralNetwork(dataSet);
        neuralNetwork.create();
        neuralNetwork.train();
        double[] testInputs = {4, 1, 2, 1};
        double result = neuralNetwork.getOutput(testInputs);
        neuralNetwork.close();

        System.out.println("Output: " + result + ", Expected: " + 2);

        new HtmlReportWriter(dataSet.getInputsAsArray(), dataSet.getOutputAsArray(), testInputs, result).createReport();

        System.out.println("Report saved");
    }
}
