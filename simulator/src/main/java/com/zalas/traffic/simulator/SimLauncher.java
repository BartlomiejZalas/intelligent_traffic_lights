package com.zalas.traffic.simulator;

import com.zalas.traffic.dynamic.controller.DynamicTrafficController;
import com.zalas.traffic.dynamic.network.NeuralNetwork;

public class SimLauncher {

    public static void main(String[] args) throws Exception {

        DynamicTrafficController controller = new DynamicTrafficController(NeuralNetwork.load("dynamicNN.nnet"));
        TrafficSchedule trafficSchedule = new TrafficSchedule();
        TrafficModel trafficModel = new TrafficModel();
        Simulator simulator = new Simulator(controller, trafficSchedule, trafficModel);

        new SimulatorWindow().lunch();
    }

}
