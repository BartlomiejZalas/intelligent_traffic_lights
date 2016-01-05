package com.zalas.traffic.dynamic.controller;

import com.zalas.traffic.controller.TrafficController;
import com.zalas.traffic.dynamic.controller.scaler.TrafficScaler;
import com.zalas.traffic.dynamic.network.NeuralNetwork;
import com.zalas.traffic.dynamic.normalization.TrafficLevelsNormalizator;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class DynamicTrafficController implements TrafficController {
    private NeuralNetwork neuralNetwork;
    private TrafficLevelsNormalizator normalizator = new TrafficLevelsNormalizator();
    private final TrafficScaler trafficScaler;

    public DynamicTrafficController(NeuralNetwork neuralNetwork, TrafficScaler trafficScaler) {
        this.neuralNetwork = neuralNetwork;
        this.trafficScaler = trafficScaler;
    }

    @Override
    public int getLightCycle(int north, int east, int south, int west, int iteration) {

        List<Integer> trafficStatus = createListWithScaledDirections(north, east, south, west);

        System.out.println("INputs for network:" + trafficStatus);

        double[] inputs = new double[4];
        for (int direction = 0; direction < trafficStatus.size(); direction++) {
            inputs[direction] = normalizator.normalizeTrafficStatus(trafficStatus.get(direction));
        }
        double result = neuralNetwork.getOutput(inputs);
        return normalizator.deNormalizeLightCycle(result);
    }

    private List<Integer> createListWithScaledDirections(int north, int east, int south, int west) {
        return newArrayList(
                    trafficScaler.scale(north),
                    trafficScaler.scale(east),
                    trafficScaler.scale(south),
                    trafficScaler.scale(west)
            );
    }
}
