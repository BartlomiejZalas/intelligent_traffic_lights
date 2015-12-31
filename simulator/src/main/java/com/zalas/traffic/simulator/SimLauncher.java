package com.zalas.traffic.simulator;

import com.zalas.traffic.dynamic.controller.DynamicTrafficController;
import com.zalas.traffic.dynamic.controller.StaticTrafficController;
import com.zalas.traffic.dynamic.controller.TrafficController;
import com.zalas.traffic.dynamic.network.NeuralNetwork;
import com.zalas.traffic.simulator.model.TrafficDirection;
import com.zalas.traffic.simulator.model.TrafficEvent;
import com.zalas.traffic.simulator.model.TrafficModel;
import com.zalas.traffic.simulator.model.TrafficSchedule;
import com.zalas.traffic.simulator.view.SimulatorGUI;

public class SimLauncher {

    public static void main(String[] args) throws Exception {

        TrafficController controller = null;
        if (args[0].equals("dynamic")) {
            controller = new DynamicTrafficController(NeuralNetwork.load("dynamicNN.nnet"));
        } else {
            controller = new StaticTrafficController();
        }

        TrafficSchedule trafficSchedule = createSchedule();
        TrafficModel trafficModel = new TrafficModel();
        Simulator simulator = new Simulator(controller, trafficSchedule, trafficModel);

        new SimulatorGUI(simulator).lunch();
    }

    private static TrafficSchedule createSchedule() {
        TrafficSchedule trafficSchedule = new TrafficSchedule();
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.NORTH, 100));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.EAST, 100));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.SOUTH, 100));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.WEST, 100));

        return trafficSchedule;
    }

}
