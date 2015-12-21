package com.zalas.traffic.dynamic.controller;

public interface TrafficController {
    int getLightCycle(int north, int east, int south, int west);
}
