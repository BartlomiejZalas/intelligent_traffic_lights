package com.zalas.traffic.simulator;

import com.zalas.traffic.dynamic.controller.DynamicTrafficController;

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
        return 0;
    }

    public void moveVehicles() {

    }

    public void handleTraffic() {

    }

}
