package com.zalas.traffic.dynamic.controller;

import java.util.List;

public interface TrafficController {
    /**
     * @param trafficStatus 4 element list - one element - one direction; 0 - NORTH, 1 - EAST, 2 - SOUTH, 3 - WEST
     * @return int light settings
     */
    int getLightCycle(List<Integer> trafficStatus);
}
