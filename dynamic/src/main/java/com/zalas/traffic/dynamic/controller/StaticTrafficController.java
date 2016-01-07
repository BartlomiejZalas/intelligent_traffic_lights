package com.zalas.traffic.dynamic.controller;

import com.zalas.traffic.controller.TrafficController;
import com.zalas.traffic.domain.TrafficDirection;

public class StaticTrafficController implements TrafficController {

    private static final int LIGHT_CYCLES_COUNT = 16;

    @Override
    public int getLightCycle(int north, int east, int south, int west, int iteration) {
        return iteration % LIGHT_CYCLES_COUNT;
    }

    @Override
    public void amplify(int iteration, TrafficDirection directionInNeighbourToThis) {
        throw new IllegalStateException(
                "Traffic amplification is not implemented in Static Controller. Moreover it has no sense here ;)");
    }
}
