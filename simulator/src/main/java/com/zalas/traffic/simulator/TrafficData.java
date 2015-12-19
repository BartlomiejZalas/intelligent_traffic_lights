package com.zalas.traffic.simulator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

public class TrafficData {

    Map<Integer, List<TrafficEvent>> eventsInIterations = new HashMap();

    public void registerEvent(int iteration, TrafficEvent event) {
        if (eventsInIterations.containsKey(iteration)) {
            eventsInIterations.get(iteration).add(event);
        }
        eventsInIterations.put(iteration, newArrayList(event));
    }

    public List<TrafficEvent> getEventsForIteration(int iteration) {
        return eventsInIterations.get(iteration);
    }

}
