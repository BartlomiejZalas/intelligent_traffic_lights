package com.zalas.traffic.dynamic.normalization;

public class TrafficLevelsNormalizator {

    private static final int TRAFFIC_LEVELS_COUNT = 4;
    private static final int LIGHT_CYCLES_COUNT = 15;

    public double normalizeTrafficStatus(int number) {
        return (double)number / TRAFFIC_LEVELS_COUNT;
    }

    public double normalizeLightCycle(int number) {
        return (double)number / LIGHT_CYCLES_COUNT;
    }

    public int deNormalizeTrafficStatus(double number) {
        return (int) Math.round(number * TRAFFIC_LEVELS_COUNT);
    }

    public int deNormalizeLightCycle(double number) {
        return (int) Math.round(number * LIGHT_CYCLES_COUNT);
    }

}
