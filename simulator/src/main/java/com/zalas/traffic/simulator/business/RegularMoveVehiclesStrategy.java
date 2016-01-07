package com.zalas.traffic.simulator.business;

import com.zalas.traffic.domain.TrafficFlow;
import com.zalas.traffic.domain.TrafficModel;

public class RegularMoveVehiclesStrategy implements MoveVehiclesStrategy {
    @Override
    public void moveVehicles(TrafficModel trafficModel, TrafficFlow flow, int vehiclesMoved) {
        trafficModel.decreaseDirection(flow.getFrom(), vehiclesMoved);
    }
}
