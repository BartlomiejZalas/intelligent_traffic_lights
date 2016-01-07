package com.zalas.traffic.dynamic.controller;

import com.zalas.traffic.controller.TrafficController;
import com.zalas.traffic.domain.TrafficDirection;
import com.zalas.traffic.dynamic.controller.scaler.TrafficScaler;
import com.zalas.traffic.dynamic.network.NeuralNetwork;
import com.zalas.traffic.dynamic.normalization.TrafficLevelsNormalizator;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.zalas.traffic.domain.TrafficDirection.*;

public class DynamicTrafficController implements TrafficController {
    private NeuralNetwork neuralNetwork;
    private TrafficLevelsNormalizator normalizator = new TrafficLevelsNormalizator();
    private final TrafficScaler trafficScaler;

    private Map<Integer, TrafficDirection> amplificationInIteration = newHashMap();

    public DynamicTrafficController(NeuralNetwork neuralNetwork, TrafficScaler trafficScaler) {
        this.neuralNetwork = neuralNetwork;
        this.trafficScaler = trafficScaler;
    }

    @Override
    public int getLightCycle(int north, int east, int south, int west, int iteration) {

        List<Integer> trafficStatus = createListWithScaledDirections(north, east, south, west);

        amplifyWhenNeeded(trafficStatus, iteration);

        System.out.println("INputs for network:" + trafficStatus);

        double[] inputs = new double[4];
        for (int direction = 0; direction < trafficStatus.size(); direction++) {
            inputs[direction] = normalizator.normalizeTrafficStatus(trafficStatus.get(direction));
        }
        double result = neuralNetwork.getOutput(inputs);
        return normalizator.deNormalizeLightCycle(result);
    }

    private void amplifyWhenNeeded(List<Integer> trafficStatus, int iteration) {
        TrafficDirection directionToAmp = amplificationInIteration.get(iteration);
        if (directionToAmp == null) return;

        if (directionToAmp == NORTH) {
            trafficStatus.set(0, trafficStatus.get(0) + 1);
        }
        if (directionToAmp == EAST) {
            trafficStatus.set(1, trafficStatus.get(1) + 1);
        }
        if (directionToAmp == SOUTH) {
            trafficStatus.set(2, trafficStatus.get(2) + 1);
        }
        if (directionToAmp == WEST) {
            trafficStatus.set(3, trafficStatus.get(3) + 1);
        }
    }

    @Override
    public void amplify(int iteration, TrafficDirection direction) {
        amplificationInIteration.put(iteration, direction);
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
