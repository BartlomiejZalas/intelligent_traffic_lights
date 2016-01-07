package com.zalas.traffic.controller;

import com.zalas.traffic.domain.TrafficDirection;

public interface TrafficController {
    int getLightCycle(int north, int east, int south, int west, int iteration);

    void amplify(int iteration, TrafficDirection directionInNeighbourToThis);
}
