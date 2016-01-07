package com.zalas.traffic.simulator.business;

import com.zalas.traffic.controller.TrafficController;
import com.zalas.traffic.domain.TrafficDirection;
import com.zalas.traffic.domain.TrafficFlow;
import com.zalas.traffic.domain.TrafficModel;

public class GreenFlowMoveVehiclesStrategy implements MoveVehiclesStrategy {

    private TrafficController neighbourController;
    private TrafficDirection directionToNeighbour;
    private TrafficDirection directionInNeighbourToThis;

    public GreenFlowMoveVehiclesStrategy(TrafficController neighbourController,
                                         TrafficDirection directionToNeighbour,
                                         TrafficDirection directionFromNeighbour) {
        this.neighbourController = neighbourController;
        this.directionToNeighbour = directionToNeighbour;
        this.directionInNeighbourToThis = directionFromNeighbour;
    }

    @Override
    public void moveVehicles(TrafficModel trafficModel, TrafficFlow flow, int vehiclesMoved) {
        if (flow.getTo() == directionToNeighbour) {
            neighbourController.amplify(trafficModel.getIteration() + 1, directionInNeighbourToThis);
            System.out.println("Amplifying neighbour direction: "+directionInNeighbourToThis);
        }
        trafficModel.decreaseDirection(flow.getFrom(), vehiclesMoved);
    }

}
