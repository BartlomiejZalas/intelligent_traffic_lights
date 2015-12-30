package com.zalas.traffic.simulator;

import com.zalas.traffic.dynamic.controller.DynamicTrafficController;
import com.zalas.traffic.dynamic.controller.StaticTrafficController;
import com.zalas.traffic.dynamic.network.NeuralNetwork;
import com.zalas.traffic.simulator.model.TrafficDirection;
import com.zalas.traffic.simulator.model.TrafficEvent;
import com.zalas.traffic.simulator.model.TrafficModel;
import com.zalas.traffic.simulator.model.TrafficSchedule;
import com.zalas.traffic.simulator.view.SimulatorGUI;

public class SimLauncher {

    public static void main(String[] args) throws Exception {

        DynamicTrafficController dynamicTrafficController = new DynamicTrafficController(NeuralNetwork.load("dynamicNN.nnet"));
        StaticTrafficController staticTrafficController = new StaticTrafficController();

        TrafficSchedule trafficSchedule = createSchedule();
        TrafficModel trafficModel = new TrafficModel();
        Simulator simulator = new Simulator(dynamicTrafficController, trafficSchedule, trafficModel);

        new SimulatorGUI(simulator).lunch();
    }

    private static TrafficSchedule createSchedule() {
        TrafficSchedule trafficSchedule = new TrafficSchedule();
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.NORTH, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.NORTH, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.NORTH, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.NORTH, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.NORTH, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.EAST, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.EAST, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.EAST, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.EAST, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.EAST, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.SOUTH, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.SOUTH, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.SOUTH, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.SOUTH, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.SOUTH, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.WEST, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.WEST, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.WEST, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.WEST, 10));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.WEST, 10));

        return trafficSchedule;
    }

}
