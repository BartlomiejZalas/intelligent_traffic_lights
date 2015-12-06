package com.zalas.traffic.dynamic;

import com.zalas.traffic.dynamic.controller.DynamicTrafficController;
import com.zalas.traffic.dynamic.data.DataSet;
import com.zalas.traffic.dynamic.data.DataSetFromCsvCreator;
import com.zalas.traffic.dynamic.evaluation.DynamicTrafficTester;
import com.zalas.traffic.dynamic.network.NeuralNetwork;
import com.zalas.traffic.io.csv.CsvLineReader;
import com.zalas.traffic.io.report.DynamicTrafficReportData;
import com.zalas.traffic.io.report.DynamicTrafficTestReportData;
import com.zalas.traffic.io.report.HtmlReportWriter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static com.zalas.traffic.io.utils.Utils.getDynamicOutputDirectory;

public class Main {

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        DataSet dataSet = prepareDataSet();

        NeuralNetwork neuralNetwork = prepareNetwork(dataSet);

        List<Boolean> results = testSolution(dataSet, neuralNetwork);

        createReport(dataSet, results);
    }

    private DataSet prepareDataSet() throws URISyntaxException, IOException {
        System.out.println("Preparing data set");
        File csvDataFile = new File(getClass().getResource("/dynamicDataSet.csv").toURI());
        return new DataSetFromCsvCreator(new CsvLineReader()).prepareDataSet(csvDataFile);
    }

    private NeuralNetwork prepareNetwork(DataSet dataSet) {
        System.out.println("Preparing network");
        NeuralNetwork neuralNetwork = new NeuralNetwork(dataSet);
        neuralNetwork.create();
        neuralNetwork.train();
        return neuralNetwork;
    }

    private List<Boolean> testSolution(DataSet dataSet, NeuralNetwork neuralNetwork) {
        System.out.println("Test solution");
        DynamicTrafficController controller = new DynamicTrafficController(neuralNetwork);
        DynamicTrafficTester tester = new DynamicTrafficTester(dataSet, controller);
        return tester.test();
    }

    private void createReport(DataSet dataSet, List<Boolean> results) throws IOException, URISyntaxException {
        System.out.println("Create report");
        DynamicTrafficReportData learningReportData = new DynamicTrafficReportData(dataSet.getInputsAsList(), dataSet.getOutputAsList());
        DynamicTrafficTestReportData testReportData = new DynamicTrafficTestReportData(learningReportData, results);

        HtmlReportWriter htmlReportWriter = new HtmlReportWriter(learningReportData, testReportData);
        File outputFile = new File(getDynamicOutputDirectory() + "dynamic-report.html");
        htmlReportWriter.createReport(outputFile);

        System.out.println("Report saved in " + outputFile);
    }
}
