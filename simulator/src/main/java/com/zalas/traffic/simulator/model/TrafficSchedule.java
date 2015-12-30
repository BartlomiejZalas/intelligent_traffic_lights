package com.zalas.traffic.simulator.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

public class TrafficSchedule {

    Map<Integer, List<TrafficEvent>> eventsInIterations = new HashMap();

    public void registerEvent(int iteration, TrafficEvent event) {
        if (eventsInIterations.containsKey(iteration)) {
            eventsInIterations.get(iteration).add(event);
        } else {
            eventsInIterations.put(iteration, newArrayList(event));
        }
    }

    public List<TrafficEvent> getEventsForIteration(int iteration) {
        List<TrafficEvent> trafficEvents = eventsInIterations.get(iteration);
        if (trafficEvents == null) {
            return newArrayList();
        }
        return trafficEvents;
    }

}
