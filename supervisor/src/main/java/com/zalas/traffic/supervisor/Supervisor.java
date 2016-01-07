package com.zalas.traffic.supervisor;

import com.zalas.traffic.simulator.business.GreenFlowMoveVehiclesStrategy;
import com.zalas.traffic.simulator.main.SimLauncher;

import static com.zalas.traffic.domain.TrafficDirection.EAST;
import static com.zalas.traffic.domain.TrafficDirection.WEST;

public class Supervisor {

    public static void main(String[] args) throws Exception {
        String[] simArgs1 = {"-c", "dynamic", "-s", "/home/bartek/AITraffic/trafficScenarios/initTrafficScenario.csv"};
        String[] simArgs2 = {"-c", "dynamic", "-s", "/home/bartek/AITraffic/trafficScenarios/emptyScenario.csv"};
        SimLauncher sim1 = new SimLauncher(simArgs1);
        SimLauncher sim2 = new SimLauncher(simArgs2);

        GreenFlowMoveVehiclesStrategy greenFlowMoveVehiclesStrategy1 =
                new GreenFlowMoveVehiclesStrategy(sim2.getController(), EAST, WEST);
        GreenFlowMoveVehiclesStrategy greenFlowMoveVehiclesStrategy2 =
                new GreenFlowMoveVehiclesStrategy(sim1.getController(), WEST, EAST);

        sim1.setMoveVehiclesStrategy(greenFlowMoveVehiclesStrategy1);
        sim2.setMoveVehiclesStrategy(greenFlowMoveVehiclesStrategy2);

        sim1.run();
        sim2.run();

    }
}
