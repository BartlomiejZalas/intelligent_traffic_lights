package com.zalas.traffic.dynamic.controller;

import com.zalas.traffic.dynamic.network.NeuralNetwork;
import com.zalas.traffic.dynamic.normalization.TrafficLevelsNormalizator;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class DynamicTrafficController implements TrafficController {
    private NeuralNetwork encogNeuralNetwork;
    private TrafficLevelsNormalizator normalizator = new TrafficLevelsNormalizator();

    public DynamicTrafficController(NeuralNetwork encogNeuralNetwork) {
        this.encogNeuralNetwork = encogNeuralNetwork;
    }

    @Override
    public int getLightCycle(int north, int east, int south, int west) {
        double[] inputs = new double[4];
        List<Integer> trafficStatus = newArrayList(north, east, south, west);
        for (int direction = 0; direction < trafficStatus.size(); direction++) {
            inputs[direction] = normalizator.normalizeTrafficStatus(trafficStatus.get(direction));
        }
        double result = encogNeuralNetwork.getOutput(inputs);
        return normalizator.deNormalizeLightCycle(result);
    }
}
