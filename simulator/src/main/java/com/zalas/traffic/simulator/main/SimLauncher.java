package com.zalas.traffic.simulator.main;

import com.zalas.traffic.controller.TrafficController;
import com.zalas.traffic.simulator.controller.Simulator;
import com.zalas.traffic.simulator.model.TrafficDirection;
import com.zalas.traffic.simulator.model.TrafficEvent;
import com.zalas.traffic.simulator.model.TrafficModel;
import com.zalas.traffic.simulator.model.TrafficSchedule;
import com.zalas.traffic.simulator.view.SimulatorGUI;
import org.apache.commons.cli.HelpFormatter;

public class SimLauncher {

    private final CmdArgsParser argsParser = new CmdArgsParser();
    private final ControllerFactory controllerFactory = new ControllerFactory();

    public static void main(String[] args) throws Exception {
        new SimLauncher().run(args);
    }

    public void run(String[] args) throws Exception {
        try {
            CmdArguments cmdArguments = argsParser.parse(args);

            TrafficController controller = controllerFactory.create(cmdArguments.getControllerType());
            TrafficSchedule trafficSchedule = createSchedule(cmdArguments.getScenarioPath());
            TrafficModel trafficModel = new TrafficModel();

            Simulator simulator = new Simulator(controller, trafficSchedule, trafficModel);
            new SimulatorGUI(simulator).lunch();
        } catch (CmdArgumentsException e) {
            System.out.println(e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("simulator", argsParser.getOptions());
        }
    }

    private static TrafficSchedule createSchedule(String scenarioPath) {
        TrafficSchedule trafficSchedule = new TrafficSchedule();
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.NORTH, 201));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.EAST, 201));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.SOUTH, 201));
        trafficSchedule.registerEvent(0, new TrafficEvent(TrafficDirection.WEST, 201));
        return trafficSchedule;
    }

}
