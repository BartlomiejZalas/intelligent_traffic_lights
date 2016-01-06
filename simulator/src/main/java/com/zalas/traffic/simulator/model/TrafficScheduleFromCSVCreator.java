package com.zalas.traffic.simulator.model;

import com.zalas.traffic.io.csv.CsvLineReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.zalas.traffic.domain.TrafficDirection.*;


public class TrafficScheduleFromCSVCreator {

    private CsvLineReader csvLineReader;

    public TrafficScheduleFromCSVCreator(CsvLineReader csvLineReader) {
        this.csvLineReader = csvLineReader;
    }

    public TrafficSchedule createSchedule(String scenarioPath) throws IOException {
        List<List<Double>> rows = csvLineReader.getRows(new File(scenarioPath));

        TrafficSchedule trafficSchedule = new TrafficSchedule();
        for (int i = 0; i < rows.size(); i++) {
            createTrafficEventsForIteration(rows.get(i), trafficSchedule, i);
        }
        return trafficSchedule;
    }

    private void createTrafficEventsForIteration(List<Double> rows, TrafficSchedule trafficSchedule, int i) {
        trafficSchedule.registerEvent(i, new TrafficEvent(NORTH, rows.get(0).intValue()));
        trafficSchedule.registerEvent(i, new TrafficEvent(EAST, rows.get(1).intValue()));
        trafficSchedule.registerEvent(i, new TrafficEvent(SOUTH, rows.get(2).intValue()));
        trafficSchedule.registerEvent(i, new TrafficEvent(WEST, rows.get(3).intValue()));
    }
}
