package com.zalas.traffic.io.report;

import java.util.List;

import static com.google.inject.internal.util.$Lists.newArrayList;
import static java.util.stream.Collectors.toList;

public class DynamicTrafficReportData implements ReportData {

    private List<List<Integer>> trafficStatuses = newArrayList();
    private List<Integer> lightCycles = newArrayList();

    public DynamicTrafficReportData(List<List<Double>> trafficStatuses, List<Double> lightCycles) {
        this.trafficStatuses = trafficStatuses.stream()
                .map(doubles ->
                                doubles.stream()
                                        .map(number -> (int) Math.round(number))
                                        .collect(toList())
                ).collect(toList());


        this.lightCycles = lightCycles.stream()
                .map(number -> (int) Math.round(number))
                .collect(toList());
    }

    @Override
    public List<List<Integer>> getTrafficStatuses() {
        return trafficStatuses;
    }

    @Override
    public List<Integer> getLightCycles() {
        return lightCycles;
    }

    @Override
    public String summaryColumn(int row) {
        return "";
    }
}
