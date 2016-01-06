package com.zalas.traffic.domain;

public class TrafficFlow {

    private TrafficDirection from;
    private TrafficDirection to;

    public TrafficFlow(TrafficDirection from, TrafficDirection to) {
        this.from = from;
        this.to = to;
    }

    public TrafficDirection getFrom() {
        return from;
    }

    public TrafficDirection getTo() {
        return to;
    }
}
