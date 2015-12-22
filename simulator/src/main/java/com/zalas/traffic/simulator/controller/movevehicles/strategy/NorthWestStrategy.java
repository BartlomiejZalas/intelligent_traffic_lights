package com.zalas.traffic.simulator.controller.movevehicles.strategy;

import com.zalas.traffic.simulator.controller.movevehicles.MoveVehiclesStrategy;
import com.zalas.traffic.simulator.model.TrafficModel;

public class NorthWestStrategy implements MoveVehiclesStrategy {
    @Override
    public void moveVehicles(TrafficModel trafficModel) {
        trafficModel.decreaseNorth();
        trafficModel.decreaseWest();
    }
}
