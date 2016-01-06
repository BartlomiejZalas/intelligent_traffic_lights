package com.zalas.traffic.simulator.main;

import com.zalas.traffic.controller.TrafficController;
import com.zalas.traffic.domain.TrafficModel;
import com.zalas.traffic.dynamic.controller.DynamicTrafficController;
import com.zalas.traffic.dynamic.controller.StaticTrafficController;
import com.zalas.traffic.dynamic.controller.scaler.FourIntervalTrafficScaler;
import com.zalas.traffic.dynamic.light_controller.IterationToTimeMapper;
import com.zalas.traffic.dynamic.light_controller.PredictionTrafficController;
import com.zalas.traffic.dynamic.network.NeuralNetwork;

public class ControllerFactory {

    private static final String DYNAMIC_NN_NAME = "dynamicNN77.nnet";

    public TrafficController create(String controllerType, String realTrafficDataFilePath, TrafficModel trafficModel) throws Exception {

        if (controllerType.equals("dynamic")) {
            return new DynamicTrafficController(NeuralNetwork.load(DYNAMIC_NN_NAME), new FourIntervalTrafficScaler());

        } else if (controllerType.equals("static")) {
            return new StaticTrafficController();

        } else if (controllerType.equals("prediction")) {
            IterationToTimeMapper iterationMapper = new IterationToTimeMapper();
            DynamicTrafficController dynamicTrafficController = new DynamicTrafficController(
                    NeuralNetwork.load(DYNAMIC_NN_NAME), new FourIntervalTrafficScaler());

            return new PredictionTrafficController(iterationMapper, trafficModel, dynamicTrafficController);
        }
        throw new RuntimeException("No such controller type: " + controllerType);
    }
}
