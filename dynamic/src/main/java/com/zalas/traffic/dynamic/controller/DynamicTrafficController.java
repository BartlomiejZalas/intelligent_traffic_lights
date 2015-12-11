package com.zalas.traffic.dynamic.controller;

import com.zalas.traffic.dynamic.network.NeuralNetwork;
import com.zalas.traffic.dynamic.normalization.TrafficLevelsNormalizator;

import java.util.List;

public class DynamicTrafficController implements TrafficController {
    private NeuralNetwork encogNeuralNetwork;
    private TrafficLevelsNormalizator normalizator = new TrafficLevelsNormalizator();

    public DynamicTrafficController(NeuralNetwork encogNeuralNetwork) {
        this.encogNeuralNetwork = encogNeuralNetwork;
    }

    @Override
    public int getLightCycle(List<Integer> trafficStatus) {
        double[] inputs = new double[4];
        for (int direction = 0; direction < trafficStatus.size(); direction++) {
            inputs[direction] = normalizator.normalizeTrafficStatus(trafficStatus.get(direction));
        }
        double result = encogNeuralNetwork.getOutput(inputs);
        return normalizator.deNormalizeLightCycle(result);
    }
}
