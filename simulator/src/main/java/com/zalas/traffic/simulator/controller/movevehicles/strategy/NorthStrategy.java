package com.zalas.traffic.simulator.controller.movevehicles.strategy;

import com.zalas.traffic.simulator.controller.movevehicles.MoveVehiclesStrategy;
import com.zalas.traffic.simulator.model.TrafficModel;

public class NorthStrategy implements MoveVehiclesStrategy {
    @Override
    public void moveVehicles(TrafficModel trafficModel) {
        trafficModel.decreaseNorth();
    }
}
