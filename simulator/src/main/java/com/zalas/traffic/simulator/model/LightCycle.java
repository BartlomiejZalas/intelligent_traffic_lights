package com.zalas.traffic.simulator.model;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static com.zalas.traffic.simulator.model.TrafficDirection.*;

public enum LightCycle {

    SOUTH_LC(newHashSet(new TrafficFlow(SOUTH, NORTH), new TrafficFlow(SOUTH, WEST), new TrafficFlow(SOUTH, EAST)), 0),

    WEST_LC(newHashSet(new TrafficFlow(WEST, EAST), new TrafficFlow(WEST, NORTH), new TrafficFlow(WEST, SOUTH)), 1),

    NORTH_LC(newHashSet(new TrafficFlow(NORTH, SOUTH), new TrafficFlow(NORTH, EAST), new TrafficFlow(NORTH, WEST)), 2),

    EAST_LC(newHashSet(new TrafficFlow(EAST, WEST), new TrafficFlow(EAST, SOUTH), new TrafficFlow(EAST, NORTH)), 3),


    EAST_WEST_LEFT_LC(newHashSet(new TrafficFlow(WEST, NORTH), new TrafficFlow(EAST, SOUTH)), 4),

    NORTH_SOUTH_LEFT_LC(newHashSet(new TrafficFlow(NORTH, EAST), new TrafficFlow(SOUTH, WEST)), 5),


    NORTH_SOUTH_LC(newHashSet(new TrafficFlow(NORTH, SOUTH), new TrafficFlow(NORTH, WEST),
            new TrafficFlow(SOUTH, NORTH), new TrafficFlow(SOUTH, EAST)), 6),

    EAST_WEST_LC(newHashSet(new TrafficFlow(EAST, WEST), new TrafficFlow(EAST, SOUTH),
            new TrafficFlow(WEST, EAST), new TrafficFlow(WEST, NORTH)), 7),


    NORTH_SOUTH_WEST_LC(newHashSet(new TrafficFlow(NORTH, WEST), new TrafficFlow(SOUTH, NORTH),
            new TrafficFlow(SOUTH, EAST), new TrafficFlow(WEST, SOUTH)), 8),

    NORTH_EAST_WEST_LC(newHashSet(new TrafficFlow(NORTH, WEST), new TrafficFlow(EAST, NORTH),
            new TrafficFlow(WEST, EAST), new TrafficFlow(WEST, SOUTH)), 9),

    NORTH_EAST_SOUTH_LC(newHashSet(new TrafficFlow(NORTH, SOUTH), new TrafficFlow(NORTH, WEST),
            new TrafficFlow(EAST, NORTH), new TrafficFlow(SOUTH, EAST)), 10),

    EAST_SOUTH_WEST_LC(newHashSet(new TrafficFlow(EAST, WEST), new TrafficFlow(EAST, NORTH),
            new TrafficFlow(SOUTH, EAST), new TrafficFlow(WEST, SOUTH)), 11),


    SOUTH_WEST_LC(newHashSet(new TrafficFlow(SOUTH, WEST), new TrafficFlow(WEST, SOUTH)), 12),

    NORTH_WEST_LC(newHashSet(new TrafficFlow(NORTH, WEST), new TrafficFlow(WEST, NORTH)), 13),

    NORTH_EAST_LC(newHashSet(new TrafficFlow(NORTH, EAST), new TrafficFlow(EAST, NORTH)), 14),

    EAST_SOUTH_LC(newHashSet(new TrafficFlow(EAST, SOUTH), new TrafficFlow(SOUTH, EAST)), 15);


    private Set<TrafficFlow> trafficFlows;
    private int number;

    LightCycle(Set<TrafficFlow> trafficFlows, int number) {
        this.trafficFlows = trafficFlows;
        this.number = number;
    }

    public Set<TrafficFlow> getTrafficFlows() {
        return trafficFlows;
    }

    public int getNumber() {
        return number;
    }

    public static LightCycle getByNumber(int number) {
        for (LightCycle lc : values()) {
            if (lc.number == number) {
                return lc;
            }
        }
        throw new RuntimeException("There is no light cycle with number: " + number);
    }

}
