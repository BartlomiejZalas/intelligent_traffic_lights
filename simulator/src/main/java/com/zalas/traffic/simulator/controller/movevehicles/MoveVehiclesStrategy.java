package com.zalas.traffic.simulator.controller.movevehicles;

import com.zalas.traffic.simulator.model.TrafficModel;

public interface MoveVehiclesStrategy {
    void moveVehicles(TrafficModel trafficModel);
}
