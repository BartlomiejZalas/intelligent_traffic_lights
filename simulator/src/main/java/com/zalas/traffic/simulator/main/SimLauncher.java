package com.zalas.traffic.simulator.main;

import com.zalas.traffic.controller.TrafficController;
import com.zalas.traffic.domain.TrafficModel;
import com.zalas.traffic.io.csv.CsvLineReader;
import com.zalas.traffic.simulator.business.MoveVehiclesStrategy;
import com.zalas.traffic.simulator.business.RegularMoveVehiclesStrategy;
import com.zalas.traffic.simulator.business.Simulator;
import com.zalas.traffic.simulator.model.TrafficSchedule;
import com.zalas.traffic.simulator.model.TrafficScheduleFromCSVCreator;
import com.zalas.traffic.simulator.view.SimulatorGUI;
import org.apache.commons.cli.HelpFormatter;

public class SimLauncher {

    private final CmdArgsParser argsParser = new CmdArgsParser();
    private final ControllerFactory controllerFactory = new ControllerFactory();
    private final TrafficScheduleFromCSVCreator scheduleCreator = new TrafficScheduleFromCSVCreator(new CsvLineReader());

    private TrafficSchedule trafficSchedule;
    private TrafficController controller;
    private TrafficModel trafficModel;
    private MoveVehiclesStrategy moveVehiclesStrategy;

    public SimLauncher(String[] args) throws Exception {
        try {
            CmdArguments cmdArguments = argsParser.parse(args);
            trafficModel = new TrafficModel();
            controller = controllerFactory.create(cmdArguments.getControllerType(), cmdArguments.getScenarioPath(), trafficModel);
            trafficSchedule = scheduleCreator.createSchedule(cmdArguments.getScenarioPath());
            moveVehiclesStrategy = new RegularMoveVehiclesStrategy();
        } catch (CmdArgumentsException e) {
            System.out.println(e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("simulator", argsParser.getOptions());
        }
    }

    public static void main(String[] args) throws Exception {
        new SimLauncher(args).run();
    }

    public void run() throws Exception {
        Simulator simulator = new Simulator(controller, trafficSchedule, trafficModel, moveVehiclesStrategy);
        new SimulatorGUI(simulator).lunch();
    }

    public TrafficSchedule getTrafficSchedule() {
        return trafficSchedule;
    }

    public void setMoveVehiclesStrategy(MoveVehiclesStrategy moveVehiclesStrategy) {
        this.moveVehiclesStrategy = moveVehiclesStrategy;
    }
}
