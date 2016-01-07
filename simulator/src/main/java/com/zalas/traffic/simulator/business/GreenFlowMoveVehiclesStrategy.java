package com.zalas.traffic.simulator.business;

import com.zalas.traffic.domain.TrafficDirection;
import com.zalas.traffic.domain.TrafficFlow;
import com.zalas.traffic.domain.TrafficModel;
import com.zalas.traffic.simulator.model.TrafficEvent;
import com.zalas.traffic.simulator.model.TrafficSchedule;

public class GreenFlowMoveVehiclesStrategy implements MoveVehiclesStrategy {

    private TrafficSchedule neighbourTrafficSchedule;
    private TrafficDirection directionToNeighbour;
    private TrafficDirection directionFromNeighbour;

    public GreenFlowMoveVehiclesStrategy(TrafficSchedule neighbourTrafficSchedule,
                                         TrafficDirection directionToNeighbour,
                                         TrafficDirection directionFromNeighbour) {
        this.neighbourTrafficSchedule = neighbourTrafficSchedule;
        this.directionToNeighbour = directionToNeighbour;
        this.directionFromNeighbour = directionFromNeighbour;
    }

    @Override
    public void moveVehicles(TrafficModel trafficModel, TrafficFlow flow, int vehiclesMoved) {
        if (flow.getTo() == directionToNeighbour) {
            int trafficMoved = getTrafficMoved(trafficModel.getTrafficForDirection(flow.getFrom()), vehiclesMoved);
            TrafficEvent event = new TrafficEvent(directionFromNeighbour, 50);
            neighbourTrafficSchedule.registerEvent(trafficModel.getIteration() + 1, event);
            System.out.println("Registering in  neighbour schedule for iteration: "+(trafficModel.getIteration() + 1)+"event: "+event );
        }
        trafficModel.decreaseDirection(flow.getFrom(), vehiclesMoved);
    }

    private int getTrafficMoved(int currentTraffic, int vehiclesMoved) {
        if (currentTraffic > vehiclesMoved) {
            return vehiclesMoved;
        }
        return currentTraffic;
    }
}
