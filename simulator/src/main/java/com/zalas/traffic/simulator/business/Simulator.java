package com.zalas.traffic.simulator.business;

import com.zalas.traffic.controller.TrafficController;
import com.zalas.traffic.domain.LightCycle;
import com.zalas.traffic.domain.TrafficFlow;
import com.zalas.traffic.domain.TrafficModel;
import com.zalas.traffic.simulator.model.TrafficEvent;
import com.zalas.traffic.simulator.model.TrafficSchedule;

import java.util.List;

public class Simulator {
    private static final int VEHICLES_MOVED = 250;
    private final TrafficController controller;
    private final TrafficSchedule trafficSchedule;
    private final TrafficModel trafficModel;
    private final MoveVehiclesStrategy moveVehiclesStrategy;

    public Simulator(TrafficController controller, TrafficSchedule trafficSchedule, TrafficModel trafficModel, MoveVehiclesStrategy moveVehiclesStrategy) {
        this.controller = controller;
        this.trafficSchedule = trafficSchedule;
        this.trafficModel = trafficModel;
        this.moveVehiclesStrategy = moveVehiclesStrategy;
    }

    public void changeLightCycle() {
        LightCycle lc = LightCycle.getByNumber(controller.getLightCycle(
                trafficModel.getTrafficNorth(),
                trafficModel.getTrafficEast(),
                trafficModel.getTrafficSouth(),
                trafficModel.getTrafficWest(),
                trafficModel.getIteration()
        ));
        trafficModel.setLightCycle(lc);

    }

    public void moveVehicles() {
        for (TrafficFlow flow : trafficModel.getLightCycle().getTrafficFlows()) {
            moveVehiclesStrategy.moveVehicles(trafficModel, flow, VEHICLES_MOVED);
        }
    }

    public void handleTraffic() {
        List<TrafficEvent> trafficEvents = trafficSchedule.getEventsForIteration(trafficModel.getIteration());
        trafficEvents.stream().forEach(te -> trafficModel.increaseDirection(te.getTrafficDirection(), te.getVehiclesAdded()));
    }

    public void nextIteration() {
        trafficModel.nextIteration();
        trafficModel.setLightCycle(null);
    }

    public TrafficModel getTrafficModel() {
        return trafficModel;
    }

    public String getControllerType() {
        return controller.getClass().getSimpleName();
    }
}
