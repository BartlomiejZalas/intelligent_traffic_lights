package com.zalas.traffic.dynamic.light_controller;

import com.google.common.collect.ImmutableMap;
import com.zalas.traffic.controller.TrafficController;
import com.zalas.traffic.domain.TrafficDirection;
import com.zalas.traffic.domain.TrafficModel;
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
    private final TrafficModel currentTrafficModel;
    private final DynamicTrafficController dynamicTrafficController;
    private final Map<String, Map<String, NeuralNetworkPredictor>> predictorsToTime = new HashMap<>();
    private final Map<String, Map<String, List<Double>>> historicalDataToTime = new HashMap<>();

    public PredictionTrafficController(IterationToTimeMapper iterationToTimeMapper, TrafficModel currentTrafficModel,
                                       DynamicTrafficController dynamicTrafficController) throws Exception {

        this.iterationToTimeMapper = iterationToTimeMapper;
        this.currentTrafficModel = currentTrafficModel;
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
        System.out.println("**********************");
        int lightCycle = -1;
        try {
            String time = iterationToTimeMapper.map(iteration);
            Map<String, NeuralNetworkPredictor> directionToPredictor = predictorsToTime.get(time);
            Map<String, Integer> trafficPredictions = newHashMap();
            for (String direction : newArrayList(STREET_NORTH, STREET_EAST, STREET_SOUTH, STREET_WEST)) {
                NeuralNetworkPredictor predictor = directionToPredictor.get(direction);
                List<Double> historicalData = historicalDataToTime.get(time).get(direction);

                Double predictedTraffic = predictor.getPrediction(convertListToArray(historicalData));
                int realTraffic = getRealTrafficForIteration(iteration, direction);

                trafficPredictions.put(direction, ((int) (predictedTraffic + realTraffic) / 2));
                updateHistoricalData(realTraffic, historicalData);

                System.out.println("Input to DC "+direction+":"+realTraffic+"+"+predictedTraffic+"="+trafficPredictions.get(direction));
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

    @Override
    public void amplify(int iteration, TrafficDirection directionInNeighbourToThis) {
        dynamicTrafficController.amplify(iteration, directionInNeighbourToThis);
    }

    private void updateHistoricalData(int realTraffic, List<Double> historicalData) {
        historicalData.remove(0);
        historicalData.add((double)realTraffic);
    }

    private File getFileWithHistoricalData(String time, String direction) throws URISyntaxException {
        return new File(getClass().getResource("/data/" + direction + "-" + time + ".csv").toURI());
    }

    private int getRealTrafficForIteration(int iteration, String traffic) {
        try {
            switch (traffic) {
                case STREET_NORTH:
                    return currentTrafficModel.getTrafficNorth();
                case STREET_EAST:
                    return currentTrafficModel.getTrafficEast();
                case STREET_SOUTH:
                    return currentTrafficModel.getTrafficSouth();
                case STREET_WEST:
                    return currentTrafficModel.getTrafficWest();
            }
        } catch (IndexOutOfBoundsException e) {
            return 0;
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
