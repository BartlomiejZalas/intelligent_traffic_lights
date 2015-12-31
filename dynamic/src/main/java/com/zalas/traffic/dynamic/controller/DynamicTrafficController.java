package com.zalas.traffic.dynamic.controller;

import com.zalas.traffic.dynamic.network.NeuralNetwork;
import com.zalas.traffic.dynamic.normalization.TrafficLevelsNormalizator;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class DynamicTrafficController implements TrafficController {
    private NeuralNetwork neuralNetwork;
    private TrafficLevelsNormalizator normalizator = new TrafficLevelsNormalizator();
    private final TrafficScaler trafficScaler = new TrafficScaler();

    public DynamicTrafficController(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    @Override
    public int getLightCycle(int north, int east, int south, int west) {

        double[] inputs = new double[4];
        List<Integer> trafficStatus = newArrayList(
                trafficScaler.scale(north),
                trafficScaler.scale(east),
                trafficScaler.scale(south),
                trafficScaler.scale(west)
        );

        System.out.println("INputs for network:" +
                trafficScaler.scale(north) +
                trafficScaler.scale(east) +
                trafficScaler.scale(south) +
                trafficScaler.scale(west));

        for (int direction = 0; direction < trafficStatus.size(); direction++) {
            inputs[direction] = normalizator.normalizeTrafficStatus(trafficStatus.get(direction));
        }
        double result = neuralNetwork.getOutput(inputs);
        return normalizator.deNormalizeLightCycle(result);
    }
}
