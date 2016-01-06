package com.zalas.traffic.dynamic.light_controller;

import com.google.common.collect.ImmutableMap;
import com.zalas.traffic.controller.TrafficController;
import com.zalas.traffic.dynamic.controller.DynamicTrafficController;
import com.zalas.traffic.dynamic.prediction.network.NeuralNetworkPredictor;
import com.zalas.traffic.io.csv.CsvLineReader;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.zalas.traffic.io.utils.Utils.*;

public class PredictionTrafficController implements TrafficController {

    public static final int NUMBER_OF_INPUTS = 100;
    private final IterationToTimeMapper iterationToTimeMapper;
    private final List<List<Double>> realTraffic;
    private final DynamicTrafficController dynamicTrafficController;
    private final Map<String, Map<String, NeuralNetworkPredictor>> predictorsToTime = new HashMap<>();
    private final Map<String, Map<String, List<Double>>> historicalDataToTime = new HashMap<>();

    public PredictionTrafficController(IterationToTimeMapper iterationToTimeMapper, List<List<Double>> realTraffic,
                                       DynamicTrafficController dynamicTrafficController) throws Exception {

        this.iterationToTimeMapper = iterationToTimeMapper;
        this.realTraffic = realTraffic;
        this.dynamicTrafficController = dynamicTrafficController;

        predictorsToTime.put(TIME_MORNING, createMapDirectionToPredictor(TIME_MORNING));
        predictorsToTime.put(TIME_AFTERNOON, createMapDirectionToPredictor(TIME_AFTERNOON));
        predictorsToTime.put(TIME_EVENING, createMapDirectionToPredictor(TIME_EVENING));

        historicalDataToTime.put(TIME_MORNING, createMapDirectionToHistoricalData(TIME_MORNING));
        historicalDataToTime.put(TIME_AFTERNOON, createMapDirectionToHistoricalData(TIME_AFTERNOON));
        historicalDataToTime.put(TIME_EVENING, createMapDirectionToHistoricalData(TIME_EVENING));
    }

    @Override
    public int getLightCycle(int north, int east, int south, int west, int iteration) {
        int lightCycle = -1;
        try {
            String time = iterationToTimeMapper.map(iteration);
            Map<String, NeuralNetworkPredictor> directionToPredictor = predictorsToTime.get(time);
            Map<String, Integer> trafficPredictions = newHashMap();

            for (String direction : newArrayList(STREET_NORTH, STREET_EAST, STREET_SOUTH, STREET_WEST)) {
                NeuralNetworkPredictor predictor = directionToPredictor.get(direction);
                List<Double> historicalData = historicalDataToTime.get(time).get(direction);

                Double predictedTraffic = predictor.getPrediction(convertListToArray(historicalData));
                Double realTraffic = getRealTrafficForIteration(iteration, direction);

                trafficPredictions.put(direction, ((int) (predictedTraffic + realTraffic) / 2));

                System.out.println(direction + ": " + getRealTrafficForIteration(iteration + 1, direction) + "," + predictedTraffic);
                updateHistoricalData(realTraffic, historicalData);

            }

            lightCycle = dynamicTrafficController.getLightCycle(
                    trafficPredictions.get(STREET_NORTH),
                    trafficPredictions.get(STREET_EAST),
                    trafficPredictions.get(STREET_SOUTH),
                    trafficPredictions.get(STREET_WEST),
                    iteration
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return lightCycle;
    }

    private void updateHistoricalData(Double realTraffic, List<Double> historicalData) {
        historicalData.remove(0);
        historicalData.add(realTraffic);
    }

    private File getFileWithHistoricalData(String time, String direction) throws URISyntaxException {
        return new File(getClass().getResource("/data/" + direction + "-" + time + ".csv").toURI());
    }

    private Double getRealTrafficForIteration(int iteration, String traffic) {
        try {switch (traffic) {
            case STREET_NORTH:
                return realTraffic.get(iteration).get(0);
            case STREET_EAST:
                return realTraffic.get(iteration).get(1);
            case STREET_SOUTH:
                return realTraffic.get(iteration).get(2);
            case STREET_WEST:
                return realTraffic.get(iteration).get(3);
        }
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("Error: No scenario for iteration: " +iteration);
        }
        throw new PredictionException("Cannot get traffic for iteration: " + iteration);
    }

    private Map<String, NeuralNetworkPredictor> createMapDirectionToPredictor(String time) throws Exception {
        NeuralNetworkPredictor northPredictor = NeuralNetworkPredictor.load(createNnFileName(STREET_NORTH, time));
        NeuralNetworkPredictor eastPredictor = NeuralNetworkPredictor.load(createNnFileName(STREET_EAST, time));
        NeuralNetworkPredictor southPredictor = NeuralNetworkPredictor.load(createNnFileName(STREET_SOUTH, time));
        NeuralNetworkPredictor westPredictor = NeuralNetworkPredictor.load(createNnFileName(STREET_WEST, time));

        Map<String, NeuralNetworkPredictor> directionToPredictor = ImmutableMap.of(STREET_NORTH, northPredictor,
                STREET_EAST, eastPredictor, STREET_SOUTH, southPredictor, STREET_WEST, westPredictor);

        return directionToPredictor;
    }

    private Map<String, List<Double>> createMapDirectionToHistoricalData(String time) throws Exception {
        List<Double> historicalDataNorth = getHistoricalData(getFileWithHistoricalData(time, STREET_NORTH), NUMBER_OF_INPUTS);
        List<Double> historicalDataEast = getHistoricalData(getFileWithHistoricalData(time, STREET_EAST), NUMBER_OF_INPUTS);
        List<Double> historicalDataSouth = getHistoricalData(getFileWithHistoricalData(time, STREET_SOUTH), NUMBER_OF_INPUTS);
        List<Double> historicalDataWest = getHistoricalData(getFileWithHistoricalData(time, STREET_WEST), NUMBER_OF_INPUTS);

        Map<String, List<Double>> directionToHistoricalData = ImmutableMap.of(STREET_NORTH, historicalDataNorth,
                STREET_EAST, historicalDataEast, STREET_SOUTH, historicalDataSouth, STREET_WEST, historicalDataWest);

        return directionToHistoricalData;
    }

    private String createNnFileName(String street, String time) throws IOException {
        return getNetworksOutputDirectory() + street + "-" + time + ".nnp";
    }

    private List<Double> getHistoricalData(File dataFile, int numberOfInputs) throws IOException {
        List<Double> historicalData = new CsvLineReader().getValuesFromColumn(dataFile, COLUMN_WITH_VALUES);
        int startElement = historicalData.size() - numberOfInputs;
        int endElement = historicalData.size();
        return historicalData.subList(startElement, endElement);
    }

}
