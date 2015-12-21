package com.zalas.traffic.simulator;

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
}
