package com.zalas.traffic.simulator.main;

import com.zalas.traffic.controller.TrafficController;
import com.zalas.traffic.dynamic.controller.DynamicTrafficController;
import com.zalas.traffic.dynamic.controller.StaticTrafficController;
import com.zalas.traffic.dynamic.controller.scaler.FourIntervalTrafficScaler;
import com.zalas.traffic.dynamic.network.NeuralNetwork;
import com.zalas.traffic.simulator.controller.Simulator;
import com.zalas.traffic.simulator.model.TrafficDirection;
import com.zalas.traffic.simulator.model.TrafficEvent;
import com.zalas.traffic.simulator.model.TrafficModel;
import com.zalas.traffic.simulator.model.TrafficSchedule;
import com.zalas.traffic.simulator.view.SimulatorGUI;
import org.apache.commons.cli.HelpFormatter;

public class SimLauncher {

    private final CmdArgsParser argsParser = new CmdArgsParser();

    public static void main(String[] args) throws Exception {
        new SimLauncher().run(args);
    }

    public void run(String[] args) throws Exception {
        try {
            CmdArguments cmdArguments = argsParser.parse(args);

            TrafficController controller = null;
            if (args[0].equals("dynamic")) {
                controller = new DynamicTrafficController(NeuralNetwork.load("dynamicNN77.nnet"), new FourIntervalTrafficScaler());
            } else {
                controller = new StaticTrafficController();
            }

            TrafficSchedule trafficSchedule = createSchedule();
            TrafficModel trafficModel = new TrafficModel();
            Simulator simulator = new Simulator(controller, trafficSchedule, trafficModel);

            new SimulatorGUI(simulator).lunch();
        } catch (CmdArgumentsException e) {
            System.out.println(e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("simulator", argsParser.getOptions());
        }
    }

    private static TrafficSchedule createSchedule() {
        TrafficSchedule trafficSchedule = new TrafficSchedule();
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.NORTH, 201));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.EAST, 201));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.SOUTH, 201));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.WEST, 201));
        return trafficSchedule;
    }

}
