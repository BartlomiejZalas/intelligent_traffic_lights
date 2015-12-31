package com.zalas.traffic.dynamic.controller;

public class StaticTrafficController implements TrafficController {

    private static final int LIGHT_CYCLES_COUNT = 16;
    private int counter;

    @Override
    public int getLightCycle(int north, int east, int south, int west) {
        counter = (counter + 1) % LIGHT_CYCLES_COUNT;
        return counter;
    }
}
