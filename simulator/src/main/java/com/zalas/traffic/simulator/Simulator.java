package com.zalas.traffic.simulator;

import com.zalas.traffic.dynamic.controller.TrafficController;
import com.zalas.traffic.simulator.model.LightCycle;
import com.zalas.traffic.simulator.model.TrafficEvent;
import com.zalas.traffic.simulator.model.TrafficModel;
import com.zalas.traffic.simulator.model.TrafficSchedule;

import java.util.List;

public class Simulator {
    private final TrafficController controller;
    private final TrafficSchedule trafficSchedule;
    private final TrafficModel trafficModel;

    public Simulator(TrafficController controller, TrafficSchedule trafficSchedule, TrafficModel trafficModel) {

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
        trafficModel.setLightCycle(lc);
    }

    public void moveVehicles() {
        trafficModel.getLightCycle().getTrafficFlows().stream()
                .forEach(flow -> trafficModel.decreaseDirection(flow.getFrom()));
    }

    public void handleTraffic() {
        List<TrafficEvent> trafficEvents = trafficSchedule.getEventsForIteration(trafficModel.getIteration());
        trafficEvents.stream().forEach(te -> trafficModel.increaseDirection(te.getTrafficDirection()));
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
