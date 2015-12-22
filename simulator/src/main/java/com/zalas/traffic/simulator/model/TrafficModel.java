package com.zalas.traffic.simulator.model;

public class TrafficModel {

    private int iteration;

    private int trafficNorth;
    private int trafficEast;
    private int trafficSouth;
    private int trafficWest;
    public int lightCycle;

    public int getLightCycle() {
        return lightCycle;
    }

    public void nextIteration() {
        this.iteration++;
    }

    public void increaseNorth() {
        this.trafficNorth++;
    }

    public void decreaseNorth() {
        this.trafficNorth--;
    }

    public void increaseEast() {
        this.trafficEast++;
    }

    public void decreaseEast() {
        this.trafficEast--;
    }

    public void increaseSouth() {
        this.trafficSouth++;
    }

    public void decreaseSouth() {
        this.trafficSouth--;
    }

    public void increaseWest() {
        this.trafficWest++;
    }

    public void decreaseWest() {
        this.trafficWest--;
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

    public void increaseDirection(TrafficDirection trafficDirection) {
        switch (trafficDirection) {
            case EAST:
                increaseEast();
                break;
            case WEST:
                increaseWest();
                break;
            case NORTH:
                increaseNorth();
                break;
            case SOUTH:
                increaseSouth();
                break;
        }
    }
}
