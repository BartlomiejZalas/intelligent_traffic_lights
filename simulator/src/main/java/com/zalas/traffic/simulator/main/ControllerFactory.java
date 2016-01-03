package com.zalas.traffic.simulator.main;

import com.zalas.traffic.controller.TrafficController;
import com.zalas.traffic.dynamic.controller.DynamicTrafficController;
import com.zalas.traffic.dynamic.controller.StaticTrafficController;
import com.zalas.traffic.dynamic.controller.scaler.FourIntervalTrafficScaler;
import com.zalas.traffic.dynamic.network.NeuralNetwork;

import java.io.IOException;

public class ControllerFactory {
    public TrafficController create(String controllerType) throws IOException, ClassNotFoundException {

        if (controllerType.equals("dynamic")) {
            return new DynamicTrafficController(NeuralNetwork.load("dynamicNN77.nnet"), new FourIntervalTrafficScaler());

        } else if (controllerType.equals("static")) {
            return new StaticTrafficController();

        } else if (controllerType.equals("prediction")) {
            throw new IllegalStateException("Not implemented yet!");
        }
        throw new RuntimeException("No such controller type: " + controllerType);
    }
}
