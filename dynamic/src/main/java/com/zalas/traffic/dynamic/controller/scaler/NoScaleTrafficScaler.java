package com.zalas.traffic.dynamic.controller.scaler;

public class NoScaleTrafficScaler implements TrafficScaler {

    public int scale(int traffic) {
        return traffic;
    }

}
