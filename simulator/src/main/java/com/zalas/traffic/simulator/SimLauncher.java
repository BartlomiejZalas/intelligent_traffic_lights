package com.zalas.traffic.simulator;

import com.zalas.traffic.dynamic.controller.DynamicTrafficController;
import com.zalas.traffic.dynamic.network.NeuralNetwork;

public class SimLauncher {

    public static void main(String[] args) throws Exception {

        DynamicTrafficController controller = new DynamicTrafficController(NeuralNetwork.load("dynamicNN.nnet"));
        TrafficData trafficData = new TrafficData();


        new SimulatorWindow().lunch();
    }

}
