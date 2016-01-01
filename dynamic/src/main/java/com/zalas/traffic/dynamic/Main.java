package com.zalas.traffic.dynamic;

import com.zalas.traffic.dynamic.controller.DynamicTrafficController;
import com.zalas.traffic.dynamic.controller.scaler.NoScaleTrafficScaler;
import com.zalas.traffic.dynamic.data.DataSet;
import com.zalas.traffic.dynamic.data.DataSetFromCsvCreator;
import com.zalas.traffic.dynamic.evaluation.DynamicTrafficTester;
import com.zalas.traffic.dynamic.evaluation.TestResult;
import com.zalas.traffic.dynamic.network.NeuralNetwork;
import com.zalas.traffic.dynamic.network.NeurophNeuralNetwork;
import com.zalas.traffic.io.csv.CsvLineReader;
import com.zalas.traffic.io.report.DynamicTrafficReportData;
import com.zalas.traffic.io.report.DynamicTrafficTestReportData;
import com.zalas.traffic.io.report.HtmlReportWriter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static com.zalas.traffic.io.utils.Utils.getDynamicOutputDirectory;

public class Main {

    private static final String REPORT_NAME = "dynamic-report.html";

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        DataSet learningDS = prepareDataSet("/traffic-all.csv");
        DataSet testDS = prepareDataSet("/traffic-all.csv");

        NeuralNetwork neuralNetwork = prepareNetwork(learningDS);
        List<TestResult> results = testSolution(testDS, neuralNetwork);
        createReport(learningDS,testDS, results);

        int sum =results.size();
        List<TestResult> collect = results.stream().filter(r -> r.getWasCorrect()).collect(Collectors.toList());
        System.out.println("Correctness:" + (double) collect.size() / (double) sum);
    }

    private DataSet prepareDataSet(String fileName) throws URISyntaxException, IOException {
        System.out.println("Preparing data set");
        File csvDataFile = new File(getClass().getResource(fileName).toURI());
        return new DataSetFromCsvCreator(new CsvLineReader()).prepareDataSet(csvDataFile);
    }

    private NeuralNetwork prepareNetwork(DataSet dataSet) throws IOException {
        System.out.println("Preparing network");
        NeurophNeuralNetwork neuralNetwork = new NeurophNeuralNetwork(dataSet);
        neuralNetwork.create();
        neuralNetwork.train();
        neuralNetwork.close();
        neuralNetwork.save("dynamicNN.nnet");
        return neuralNetwork;
    }

    private List<TestResult> testSolution(DataSet dataSet, NeuralNetwork encogNeuralNetwork) {
        System.out.println("Test solution");
        DynamicTrafficController controller = new DynamicTrafficController(encogNeuralNetwork, new NoScaleTrafficScaler());
        DynamicTrafficTester tester = new DynamicTrafficTester(dataSet, controller);
        return tester.test();
    }

    private void createReport(DataSet dataSet, DataSet testDataSet, List<TestResult> results) throws IOException, URISyntaxException {
        System.out.println("Create report");

        DynamicTrafficReportData learningReportData = createLearningReportData(dataSet);
        DynamicTrafficTestReportData testReportData = createTestReportData(testDataSet, results);

        HtmlReportWriter htmlReportWriter = new HtmlReportWriter(learningReportData, testReportData);
        File outputFile = new File(getDynamicOutputDirectory() + REPORT_NAME);
        htmlReportWriter.createReport(outputFile);

        System.out.println("Report saved in " + outputFile);
    }

    private DynamicTrafficReportData createLearningReportData(DataSet dataSet) {
        return new DynamicTrafficReportData(dataSet.getInputsAsList(), dataSet.getOutputAsList());
    }

    private DynamicTrafficTestReportData createTestReportData(DataSet dataSet, List<TestResult> results) {
        List<Double> controllerOutputs = results.stream().map(result -> (double) result.getComputedLightCycle()).collect(Collectors.toList());
        List<Boolean> controllerCorrectness = results.stream().map(TestResult::getWasCorrect).collect(Collectors.toList());

        return new DynamicTrafficTestReportData(
                new DynamicTrafficReportData(dataSet.getInputsAsList(), controllerOutputs), controllerCorrectness
        );
    }
}
