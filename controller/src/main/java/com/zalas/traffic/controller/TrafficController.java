package com.zalas.traffic.controller;

public interface TrafficController {
    int getLightCycle(int north, int east, int south, int west, int iteration);
}
