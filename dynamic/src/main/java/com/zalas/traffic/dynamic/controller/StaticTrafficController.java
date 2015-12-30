package com.zalas.traffic.dynamic.controller;

public class StaticTrafficController implements TrafficController {
    @Override
    public int getLightCycle(int north, int east, int south, int west) {
        return 0;
    }
}
