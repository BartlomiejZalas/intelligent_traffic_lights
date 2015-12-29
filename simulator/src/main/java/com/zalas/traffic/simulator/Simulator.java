package com.zalas.traffic.simulator;

import com.zalas.traffic.dynamic.controller.DynamicTrafficController;
import com.zalas.traffic.simulator.model.*;

import java.util.List;

public class Simulator {
    private final DynamicTrafficController controller;
    private final TrafficSchedule trafficSchedule;
    private final TrafficModel trafficModel;

    public Simulator(DynamicTrafficController controller, TrafficSchedule trafficSchedule, TrafficModel trafficModel) {

        this.controller = controller;
        this.trafficSchedule = trafficSchedule;
        this.trafficModel = trafficModel;
    }

    public void changeLightCycle() {
        LightCycle lc = LightCycle.getByNumber(controller.getLightCycle(
                trafficModel.getTrafficNorth(),
                trafficModel.getTrafficEast(),
                trafficModel.getTrafficSouth(),
                trafficModel.getTrafficWest()
        ));
        trafficModel.lightCycle = lc;
    }

    public void moveVehicles() {
        trafficModel.getLightCycle().getTrafficFlows().stream()
                .forEach(flow -> trafficModel.decreaseDirection(flow.getFrom()));
    }

    public void handleTraffic() {
        List<TrafficEvent> trafficEvents = trafficSchedule.getEventsForIteration(trafficModel.getIteration());
        trafficEvents.stream().forEach(te -> trafficModel.increaseDirection(te.getTrafficDirection()));
    }

    public TrafficModel getTrafficModel() {
        return trafficModel;
    }

    public void nextIteration() {
        trafficModel.nextIteration();
    }
}
