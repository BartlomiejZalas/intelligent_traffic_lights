package com.zalas.traffic.simulator.controller.movevehicles;

import com.zalas.traffic.simulator.controller.movevehicles.strategy.EastStrategy;

public class MoveVehiclesStrategyFactory {
    public MoveVehiclesStrategy getStrategy(int lightCycle) {
        switch(lightCycle){
            case 0:
                return new SouthStrategy();
            case 1:
                return new EastStrategy();
        }
        throw new RuntimeException("There is no traffic strategy with traffic cycle: " + lightCycle);
    }
}
