package com.zalas.traffic.simulator.controller.movevehicles;

import com.zalas.traffic.simulator.controller.movevehicles.strategy.*;

public class MoveVehiclesStrategyFactory {
    public MoveVehiclesStrategy getStrategy(int lightCycle) {
        switch(lightCycle){
            case 0:
                return new SouthStrategy();
            case 1:
                return new EastStrategy();
            case 2:
                return new NorthStrategy();
            case 3:
                return new WestStrategy();
            case 4:
                return new EastWestLeftStrategy();
            case 5:
                return new NorthSouthLeftStrategy();
            case 6:
                return new NorthSouthStrategy();
            case 7:
                return new EastWestStrategy();
            case 8:
                return new NorthSouthWestStrategy();
            case 9:
                return new NorthEastWestStrategy();
            case 10:
                return new NorthEastSouthStrategy();
            case 11:
                return new EastSouthWestStrategy();
            case 12:
                return new SouthWestStrategy();
            case 13:
                return new NorthWestStrategy();
            case 14:
                return new NorthEastStrategy();
            case 15:
                return new EastSouthStrategy();

        }
        throw new RuntimeException("There is no traffic strategy with traffic cycle: " + lightCycle);
    }
}
