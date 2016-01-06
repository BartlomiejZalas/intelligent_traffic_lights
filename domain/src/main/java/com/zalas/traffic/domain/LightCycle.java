package com.zalas.traffic.domain;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;


public enum LightCycle {

    SOUTH_LC(newHashSet(new TrafficFlow(TrafficDirection.SOUTH, TrafficDirection.NORTH), new TrafficFlow(TrafficDirection.SOUTH, TrafficDirection.WEST), new TrafficFlow(TrafficDirection.SOUTH, TrafficDirection.EAST)), 0),

    WEST_LC(newHashSet(new TrafficFlow(TrafficDirection.WEST, TrafficDirection.EAST), new TrafficFlow(TrafficDirection.WEST, TrafficDirection.NORTH), new TrafficFlow(TrafficDirection.WEST, TrafficDirection.SOUTH)), 1),

    NORTH_LC(newHashSet(new TrafficFlow(TrafficDirection.NORTH, TrafficDirection.SOUTH), new TrafficFlow(TrafficDirection.NORTH, TrafficDirection.EAST), new TrafficFlow(TrafficDirection.NORTH, TrafficDirection.WEST)), 2),

    EAST_LC(newHashSet(new TrafficFlow(TrafficDirection.EAST, TrafficDirection.WEST), new TrafficFlow(TrafficDirection.EAST, TrafficDirection.SOUTH), new TrafficFlow(TrafficDirection.EAST, TrafficDirection.NORTH)), 3),


    EAST_WEST_LEFT_LC(newHashSet(new TrafficFlow(TrafficDirection.WEST, TrafficDirection.NORTH), new TrafficFlow(TrafficDirection.EAST, TrafficDirection.SOUTH)), 4),

    NORTH_SOUTH_LEFT_LC(newHashSet(new TrafficFlow(TrafficDirection.NORTH, TrafficDirection.EAST), new TrafficFlow(TrafficDirection.SOUTH, TrafficDirection.WEST)), 5),


    NORTH_SOUTH_LC(newHashSet(new TrafficFlow(TrafficDirection.NORTH, TrafficDirection.SOUTH), new TrafficFlow(TrafficDirection.NORTH, TrafficDirection.WEST),
            new TrafficFlow(TrafficDirection.SOUTH, TrafficDirection.NORTH), new TrafficFlow(TrafficDirection.SOUTH, TrafficDirection.EAST)), 6),

    EAST_WEST_LC(newHashSet(new TrafficFlow(TrafficDirection.EAST, TrafficDirection.WEST), new TrafficFlow(TrafficDirection.EAST, TrafficDirection.SOUTH),
            new TrafficFlow(TrafficDirection.WEST, TrafficDirection.EAST), new TrafficFlow(TrafficDirection.WEST, TrafficDirection.NORTH)), 7),


    NORTH_SOUTH_WEST_LC(newHashSet(new TrafficFlow(TrafficDirection.NORTH, TrafficDirection.WEST), new TrafficFlow(TrafficDirection.SOUTH, TrafficDirection.NORTH),
            new TrafficFlow(TrafficDirection.SOUTH, TrafficDirection.EAST), new TrafficFlow(TrafficDirection.WEST, TrafficDirection.SOUTH)), 8),

    NORTH_EAST_WEST_LC(newHashSet(new TrafficFlow(TrafficDirection.NORTH, TrafficDirection.WEST), new TrafficFlow(TrafficDirection.EAST, TrafficDirection.NORTH),
            new TrafficFlow(TrafficDirection.WEST, TrafficDirection.EAST), new TrafficFlow(TrafficDirection.WEST, TrafficDirection.SOUTH)), 9),

    NORTH_EAST_SOUTH_LC(newHashSet(new TrafficFlow(TrafficDirection.NORTH, TrafficDirection.SOUTH), new TrafficFlow(TrafficDirection.NORTH, TrafficDirection.WEST),
            new TrafficFlow(TrafficDirection.EAST, TrafficDirection.NORTH), new TrafficFlow(TrafficDirection.SOUTH, TrafficDirection.EAST)), 10),

    EAST_SOUTH_WEST_LC(newHashSet(new TrafficFlow(TrafficDirection.EAST, TrafficDirection.WEST), new TrafficFlow(TrafficDirection.EAST, TrafficDirection.NORTH),
            new TrafficFlow(TrafficDirection.SOUTH, TrafficDirection.EAST), new TrafficFlow(TrafficDirection.WEST, TrafficDirection.SOUTH)), 11),


    SOUTH_WEST_LC(newHashSet(new TrafficFlow(TrafficDirection.SOUTH, TrafficDirection.WEST), new TrafficFlow(TrafficDirection.WEST, TrafficDirection.SOUTH)), 12),

    NORTH_WEST_LC(newHashSet(new TrafficFlow(TrafficDirection.NORTH, TrafficDirection.WEST), new TrafficFlow(TrafficDirection.WEST, TrafficDirection.NORTH)), 13),

    NORTH_EAST_LC(newHashSet(new TrafficFlow(TrafficDirection.NORTH, TrafficDirection.EAST), new TrafficFlow(TrafficDirection.EAST, TrafficDirection.NORTH)), 14),

    EAST_SOUTH_LC(newHashSet(new TrafficFlow(TrafficDirection.EAST, TrafficDirection.SOUTH), new TrafficFlow(TrafficDirection.SOUTH, TrafficDirection.EAST)), 15);


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
