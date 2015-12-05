package com.zalas.traffic.dynamic;

import com.google.common.collect.Lists;
import com.zalas.traffic.dynamic.data.DataSet;
import com.zalas.traffic.dynamic.data.DataSetFromCsvCreator;
import com.zalas.traffic.dynamic.network.NeuralNetwork;
import com.zalas.traffic.io.csv.CsvLineReader;
import com.zalas.traffic.io.report.DynamicTrafficReportData;
import com.zalas.traffic.io.report.HtmlReportWriter;
import com.zalas.traffic.io.utils.Utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.inject.internal.util.$Lists.newArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        File csvDataFile = new File(getClass().getResource("/dynamicDataSet.csv").toURI());
        DataSet dataSet = new DataSetFromCsvCreator(new CsvLineReader()).prepareDataSet(csvDataFile);
        NeuralNetwork neuralNetwork = new NeuralNetwork(dataSet);
        neuralNetwork.create();
        neuralNetwork.train();
        double[] testInputs = {4, 1, 2, 1};
        List<Double> testInputsList = Arrays.stream(testInputs).boxed().collect(Collectors.toList());
        double result = neuralNetwork.getOutput(testInputs)*15;

        DynamicTrafficReportData learningReportData = new DynamicTrafficReportData(dataSet.getInputsAsList(), dataSet.getOutputAsList());

        List<List<Double>> testInputsListData = Lists.<List<Double>>newArrayList(testInputsList);
        DynamicTrafficReportData testReportData = new DynamicTrafficReportData(testInputsListData, newArrayList(result));

        System.out.println("Output: " + result + ", Expected: " + 2);

        HtmlReportWriter htmlReportWriter = new HtmlReportWriter(learningReportData, testReportData);
        File outputFile = new File(Utils.getDynamicOutputDirectory() + "dynamic-report.html");
        htmlReportWriter.createReport(outputFile);

        System.out.println("Report saved in "+outputFile);
    }
}
