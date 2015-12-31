package com.zalas.traffic.dynamic.controller;

public class TrafficScaler {

    private static final int SCALE_INTERVAL_MAX_1 = 50;
    private static final int SCALE_INTERVAL_MAX_2 = 100;
    private static final int SCALE_INTERVAL_MAX_3 = 150;

    public int scale(int traffic) {
        if (traffic >= 0 && traffic <= SCALE_INTERVAL_MAX_1) return 1;
        if (traffic > SCALE_INTERVAL_MAX_1 && traffic <= SCALE_INTERVAL_MAX_2) return 2;
        if (traffic > SCALE_INTERVAL_MAX_2 && traffic <= SCALE_INTERVAL_MAX_3) return 3;
        if (traffic > SCALE_INTERVAL_MAX_3) return 4;
        throw new RuntimeException("Traffic value out of scale!");
    }


}
