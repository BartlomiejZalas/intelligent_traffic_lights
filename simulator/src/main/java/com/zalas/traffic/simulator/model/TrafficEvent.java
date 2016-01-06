package com.zalas.traffic.simulator.model;

import com.zalas.traffic.domain.TrafficDirection;

public class TrafficEvent {

    TrafficDirection trafficDirection;
    int vehiclesAdded;

    public TrafficEvent(TrafficDirection trafficDirection, int vehiclesAdded) {
        this.trafficDirection = trafficDirection;
        this.vehiclesAdded = vehiclesAdded;
    }

    public TrafficDirection getTrafficDirection() {
        return trafficDirection;
    }

    public int getVehiclesAdded() {
        return vehiclesAdded;
    }

    @Override
    public String toString() {
        return "TrafficEvent{" +
                "trafficDirection=" + trafficDirection +
                ", vehiclesAdded=" + vehiclesAdded +
                '}';
    }
}
