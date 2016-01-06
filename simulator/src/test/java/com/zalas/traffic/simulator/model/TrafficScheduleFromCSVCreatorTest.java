package com.zalas.traffic.simulator.model;

import com.zalas.traffic.domain.TrafficDirection;
import com.zalas.traffic.io.csv.CsvLineReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.zalas.traffic.domain.TrafficDirection.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TrafficScheduleFromCSVCreatorTest {

    private static final File FILE = new File("FILE");
    private static final ArrayList<Double> ITERATION_1_TRAFFIC = newArrayList(1., 2., 3., 4.);
    private static final ArrayList<Double> ITERATION_2_TRAFFIC = newArrayList(5., 6., 7., 8.);
    private TrafficScheduleFromCSVCreator trafficScheduleFromCSVCreator;

    @Mock
    private CsvLineReader csvLineReader;

    @Before
    public void setUp() throws Exception {
        trafficScheduleFromCSVCreator = new TrafficScheduleFromCSVCreator(csvLineReader);
    }

    @Test
    public void createSchedule_shouldReturnProperSchedule_whenFileGiven() throws Exception {
        List<List<Double>> dataFromCvsFile = newArrayList(ITERATION_1_TRAFFIC, ITERATION_2_TRAFFIC);
        given(csvLineReader.getRows(FILE)).willReturn(dataFromCvsFile);

        TrafficSchedule result = trafficScheduleFromCSVCreator.createSchedule(FILE.getName());

        assertIteration(ITERATION_1_TRAFFIC, result.getEventsForIteration(0));
        assertIteration(ITERATION_2_TRAFFIC, result.getEventsForIteration(1));
    }

    private void assertIteration(ArrayList<Double> expecterTrafficInIteration, List<TrafficEvent> eventsForIteration) {
        assertTrafficEvent(NORTH, expecterTrafficInIteration.get(0), eventsForIteration.get(0));
        assertTrafficEvent(EAST, expecterTrafficInIteration.get(1), eventsForIteration.get(1));
        assertTrafficEvent(SOUTH, expecterTrafficInIteration.get(2), eventsForIteration.get(2));
        assertTrafficEvent(WEST, expecterTrafficInIteration.get(3), eventsForIteration.get(3));
    }

    private void assertTrafficEvent(TrafficDirection direction, Double traffic, TrafficEvent trafficEvent) {
        assertEquals(direction, trafficEvent.getTrafficDirection());
        assertEquals(traffic, trafficEvent.getVehiclesAdded(), 0.01);
    }
}