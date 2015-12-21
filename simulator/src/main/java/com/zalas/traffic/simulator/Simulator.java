package com.zalas.traffic.simulator;

import com.zalas.traffic.dynamic.controller.DynamicTrafficController;

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

    public int getLightCycle() {
        return controller.getLightCycle(
                trafficModel.getTrafficNorth(),
                trafficModel.getTrafficEast(),
                trafficModel.getTrafficSouth(),
                trafficModel.getTrafficWest()
        );
    }

    public void moveVehicles(int lightCycle) {
        new MoveVehiclesStrategyFactory().getStrategy(lightCycle).moveVehicles(trafficModel);
    }

    public void handleTraffic() {
        List<TrafficEvent> trafficEvents = trafficSchedule.getEventsForIteration(trafficModel.getIteration());
    }

}