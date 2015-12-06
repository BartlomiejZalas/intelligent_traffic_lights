package com.zalas.traffic.dynamic.controller;

import com.zalas.traffic.dynamic.network.NeuralNetwork;
import com.zalas.traffic.dynamic.normalization.TrafficLevelsNormalizator;

import java.util.List;

public class DynamicTrafficController implements TrafficController {
    private NeuralNetwork neuralNetwork;
    private TrafficLevelsNormalizator normalizator = new TrafficLevelsNormalizator();

    public DynamicTrafficController(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    @Override
    public int getLightCycle(List<Integer> trafficStatus) {
        double[] inputs = new double[4];
        for(int direction = 0;direction < trafficStatus.size(); direction++) {
            inputs[direction] = normalizator.normalizeTrafficStatus(trafficStatus.get(direction));
        }
        double result = neuralNetwork.getOutput(inputs);
        return normalizator.deNormalizeLightCycle(result);
    }
}