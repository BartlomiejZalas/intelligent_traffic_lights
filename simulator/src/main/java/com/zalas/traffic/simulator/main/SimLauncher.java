package com.zalas.traffic.simulator.main;

import com.zalas.traffic.controller.TrafficController;
import com.zalas.traffic.io.csv.CsvLineReader;
import com.zalas.traffic.simulator.business.Simulator;
import com.zalas.traffic.simulator.model.*;
import com.zalas.traffic.simulator.view.SimulatorGUI;
import org.apache.commons.cli.HelpFormatter;

public class SimLauncher {

    private final CmdArgsParser argsParser = new CmdArgsParser();
    private final ControllerFactory controllerFactory = new ControllerFactory();
    private final TrafficScheduleFromCSVCreator scheduleCreator = new TrafficScheduleFromCSVCreator(new CsvLineReader());

    public static void main(String[] args) throws Exception {
        new SimLauncher().run(args);
    }

    public void run(String[] args) throws Exception {
        try {
            CmdArguments cmdArguments = argsParser.parse(args);

            TrafficController controller = controllerFactory.create(cmdArguments.getControllerType());
            TrafficSchedule trafficSchedule = scheduleCreator.createSchedule(cmdArguments.getScenarioPath());
            TrafficModel trafficModel = new TrafficModel();

            Simulator simulator = new Simulator(controller, trafficSchedule, trafficModel);
            new SimulatorGUI(simulator).lunch();
        } catch (CmdArgumentsException e) {
            System.out.println(e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("simulator", argsParser.getOptions());
        }
    }

}
