package com.zalas.traffic.light_controller;

import com.zalas.traffic.io.CsvLineReader;
import com.zalas.traffic.prediction.network.NeuralNetworkPredictor;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LightController {

    private static final String TIME = "evening";
    private static final String[] STREETS = new String[]{"broniewskiegoN", "broniewskiegoS", "glowna", "kusocinskiego"};
    private static final String PREDICTORS_OUTPUT_DIRECTORY = "/home/bartek/AITraffic/networks/";

    private final CsvLineReader csvLineReader = new CsvLineReader();

    public static void main(String[] args) throws Exception {
        new LightController().run();
    }

    public void run() throws Exception {
        for (String dataFileName : getDataFileNamesForMorning()) {
            File trainingDataFile = new File(getClass().getResource("/data/" + dataFileName + ".csv").toURI());
            NeuralNetworkPredictor predictor = NeuralNetworkPredictor.load(PREDICTORS_OUTPUT_DIRECTORY + dataFileName + ".nnp");
            double output = getOutput(trainingDataFile, predictor);
            System.out.println(dataFileName + " prediction at 18:00-18:30: " + output);
        }
    }

    private double getOutput(File trainingDataFile, NeuralNetworkPredictor predictor) throws Exception {
        List<Double> subHistoricalData = getHistoricalData(trainingDataFile, predictor);
        return predictor.getPrediction(convertListToArray(subHistoricalData));
    }

    private List<String> getDataFileNamesForMorning() {
        List<String> files = new ArrayList<>();
        for (String street : STREETS) {
            files.add(street + "-" + TIME);
        }
        return files;
    }

    private List<Double> getHistoricalData(File trainingDataFile, NeuralNetworkPredictor predictor) throws IOException {
        ArrayList<Double> historicalData = csvLineReader.getValuesFromColumn(trainingDataFile, 1);
        return historicalData.subList(historicalData.size() - predictor.getNoOfInputs(), historicalData.size());
    }

    private double[] convertListToArray(List<Double> subHistoricalData) {
        Double[] subHistoricalDataArr = new Double[subHistoricalData.size()];
        subHistoricalData.toArray(subHistoricalDataArr);
        return ArrayUtils.toPrimitive(subHistoricalDataArr);
    }

}
