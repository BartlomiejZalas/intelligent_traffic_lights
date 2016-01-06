package com.zalas.traffic.dynamic.light_controller;

import com.zalas.traffic.io.utils.Utils;

public class IterationToTimeMapper {
    public String map(int iteration) {
        iteration = iteration % 32;

        if (iteration <= 15) return Utils.TIME_MORNING;
        if (iteration <= 23) return Utils.TIME_AFTERNOON;
        if (iteration <= 31) return Utils.TIME_EVENING;

        throw new RuntimeException("Cannot assign time for iteration:" + iteration);
    }
}
