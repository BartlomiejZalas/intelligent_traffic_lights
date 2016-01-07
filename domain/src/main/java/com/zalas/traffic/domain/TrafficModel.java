package com.zalas.traffic.domain;

public class TrafficModel {

    private int iteration;

    private int trafficNorth;
    private int trafficEast;
    private int trafficSouth;
    private int trafficWest;
    private LightCycle lightCycle;

    public LightCycle getLightCycle() {
        return lightCycle;
    }

    public void nextIteration() {
        this.iteration++;
    }

    public void increaseNorth(int vehiclesAdded) {
        this.trafficNorth += vehiclesAdded;
    }

    public void decreaseNorth(int vehiclesMoved) {
        this.trafficNorth = decreaseTraffic(trafficNorth, vehiclesMoved);
    }

    public void increaseEast(int vehiclesAdded) {
        this.trafficEast += vehiclesAdded;
    }

    public void decreaseEast(int vehiclesMoved) {
        this.trafficEast = decreaseTraffic(trafficEast, vehiclesMoved);
    }

    public void increaseSouth(int vehiclesAdded) {
        this.trafficSouth += vehiclesAdded;
    }

    public void decreaseSouth(int vehiclesMoved) {
        this.trafficSouth = decreaseTraffic(trafficSouth, vehiclesMoved);
    }

    public void increaseWest(int vehiclesAdded) {
        this.trafficWest += vehiclesAdded;
    }

    public void decreaseWest(int vehiclesMoved) {
        this.trafficWest = decreaseTraffic(trafficWest, vehiclesMoved);
    }

    public int getIteration() {
        return iteration;
    }

    public int getTrafficNorth() {
        return trafficNorth;
    }

    public int getTrafficEast() {
        return trafficEast;
    }

    public int getTrafficSouth() {
        return trafficSouth;
    }

    public int getTrafficWest() {
        return trafficWest;
    }

    public void increaseDirection(TrafficDirection trafficDirection, int vehiclesAdded) {
        switch (trafficDirection) {
            case EAST:
                increaseEast(vehiclesAdded);
                break;
            case WEST:
                increaseWest(vehiclesAdded);
                break;
            case NORTH:
                increaseNorth(vehiclesAdded);
                break;
            case SOUTH:
                increaseSouth(vehiclesAdded);
                break;
        }
    }

    public void decreaseDirection(TrafficDirection trafficDirection, int vehiclesMoved) {
        switch (trafficDirection) {
            case EAST:
                decreaseEast(vehiclesMoved);
                break;
            case WEST:
                decreaseWest(vehiclesMoved);
                break;
            case NORTH:
                decreaseNorth(vehiclesMoved);
                break;
            case SOUTH:
                decreaseSouth(vehiclesMoved);
                break;
        }
    }

    public void setLightCycle(LightCycle lightCycle) {
        this.lightCycle = lightCycle;
    }

    private int decreaseTraffic(int currentTraffic, int vehiclesMoved) {
        if (currentTraffic < vehiclesMoved) {
            return 0;
        }
        return currentTraffic - vehiclesMoved;
    }

    public int getTrafficForDirection(TrafficDirection trafficDirection) {
        switch (trafficDirection) {
            case EAST:
                return trafficEast;
            case WEST:
                return trafficWest;
            case NORTH:
                return trafficNorth;
            case SOUTH:
                return trafficSouth;
        }
        throw new RuntimeException("Cannot find current traffic for: " + trafficDirection);
    }
}
