package com.zalas.traffic.simulator;

import com.zalas.traffic.dynamic.controller.DynamicTrafficController;
import com.zalas.traffic.simulator.controller.movevehicles.MoveVehiclesStrategy;
import com.zalas.traffic.simulator.controller.movevehicles.MoveVehiclesStrategyFactory;
import com.zalas.traffic.simulator.model.TrafficEvent;
import com.zalas.traffic.simulator.model.TrafficModel;
import com.zalas.traffic.simulator.model.TrafficSchedule;

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
        trafficModel.lightCycle =  controller.getLightCycle(
                trafficModel.getTrafficNorth(),
                trafficModel.getTrafficEast(),
                trafficModel.getTrafficSouth(),
                trafficModel.getTrafficWest()
        );
    }

    public void moveVehicles() {
        MoveVehiclesStrategy strategy = new MoveVehiclesStrategyFactory().getStrategy(trafficModel.getLightCycle());
        strategy.moveVehicles(trafficModel);
    }

    public void handleTraffic() {
        List<TrafficEvent> trafficEvents = trafficSchedule.getEventsForIteration(trafficModel.getIteration());
        trafficEvents.stream()
                .forEach(te -> trafficModel.increaseDirection(te.getTrafficDirection()));
    }

    public TrafficModel getTrafficModel() {
        return trafficModel;
    }

    public void nextIteration() {
        trafficModel.nextIteration();
    }
}
