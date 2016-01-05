package com.zalas.traffic.dynamic.controller;

import com.zalas.traffic.controller.TrafficController;

public class StaticTrafficController implements TrafficController {

    private static final int LIGHT_CYCLES_COUNT = 16;

    @Override
    public int getLightCycle(int north, int east, int south, int west, int iteration) {
        return iteration % LIGHT_CYCLES_COUNT;
    }
}
