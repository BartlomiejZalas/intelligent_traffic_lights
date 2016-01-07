package com.zalas.traffic.simulator.business;

import com.zalas.traffic.domain.TrafficFlow;
import com.zalas.traffic.domain.TrafficModel;

public interface MoveVehiclesStrategy {
    void moveVehicles(TrafficModel trafficModel, TrafficFlow flow, int vehiclesMoved);
}
