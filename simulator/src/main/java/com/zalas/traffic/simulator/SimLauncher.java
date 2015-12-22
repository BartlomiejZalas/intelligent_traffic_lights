package com.zalas.traffic.simulator;

import com.zalas.traffic.dynamic.controller.DynamicTrafficController;
import com.zalas.traffic.dynamic.network.NeuralNetwork;
import com.zalas.traffic.simulator.model.TrafficDirection;
import com.zalas.traffic.simulator.model.TrafficEvent;
import com.zalas.traffic.simulator.model.TrafficModel;
import com.zalas.traffic.simulator.model.TrafficSchedule;
import com.zalas.traffic.simulator.view.SimulatorGUI;

public class SimLauncher {

    public static void main(String[] args) throws Exception {

        DynamicTrafficController controller = new DynamicTrafficController(NeuralNetwork.load("dynamicNN.nnet"));
        TrafficSchedule trafficSchedule = new TrafficSchedule();
        trafficSchedule.registerEvent(1, new TrafficEvent(TrafficDirection.NORTH, 1));
        trafficSchedule.registerEvent(2, new TrafficEvent(TrafficDirection.NORTH, 1));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.NORTH, 1));
        TrafficModel trafficModel = new TrafficModel();
        Simulator simulator = new Simulator(controller, trafficSchedule, trafficModel);

        new SimulatorGUI(simulator).lunch();
    }

}
