package com.zalas.traffic.light_controller;

import com.zalas.traffic.io.CsvLineReader;
import com.zalas.traffic.prediction.network.NeuralNetworkPredictor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.zalas.traffic.utils.Utils.*;

public class LightController {

    public static void main(String[] args) throws Exception {
        new LightController().run();
    }

    public void run() throws Exception {
        for (String dataFileName : getDataFileNamesForTime(TIME_EVENING)) {
            NeuralNetworkPredictor predictor = NeuralNetworkPredictor.load(PREDICTORS_OUTPUT_DIRECTORY + dataFileName + ".nnp");
            File trainingDataFile = new File(getClass().getResource("/data/" + dataFileName + ".csv").toURI());
            List<Double> subHistoricalData = getHistoricalData(trainingDataFile, predictor);
            double output = predictor.getPrediction(convertListToArray(subHistoricalData));
            System.out.println(dataFileName + " prediction at 18:00-18:30: " + output);
        }
    }

    private List<Double> getHistoricalData(File dataFile, NeuralNetworkPredictor predictor) throws IOException {
        ArrayList<Double> historicalData = new CsvLineReader().getValuesFromColumn(dataFile, COLUMN_WITH_VALUES);
        int startElement = historicalData.size() - predictor.getNoOfInputs();
        int endElement = historicalData.size();
        return historicalData.subList(startElement, endElement);
    }


}
