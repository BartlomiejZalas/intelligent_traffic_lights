package com.zalas.traffic.dynamic.light_controller;

import org.junit.Before;
import org.junit.Test;

import static com.zalas.traffic.io.utils.Utils.TIME_AFTERNOON;
import static com.zalas.traffic.io.utils.Utils.TIME_EVENING;
import static com.zalas.traffic.io.utils.Utils.TIME_MORNING;
import static org.junit.Assert.assertEquals;

public class IterationToTimeMapperTest {

    private IterationToTimeMapper iterationToTimeMapper;
    private static final int NEXT_CYCLE = 32;

    @Before
    public void setUp() throws Exception {
        iterationToTimeMapper = new IterationToTimeMapper();
    }

    @Test
    public void map_shouldReturnMorning_whenIterationIsIn0And15() throws Exception {
        assertEquals(TIME_MORNING, iterationToTimeMapper.map(0));
        assertEquals(TIME_MORNING, iterationToTimeMapper.map(10));
        assertEquals(TIME_MORNING, iterationToTimeMapper.map(15));
        assertEquals(TIME_MORNING, iterationToTimeMapper.map(0 + NEXT_CYCLE));
        assertEquals(TIME_MORNING, iterationToTimeMapper.map(10 + NEXT_CYCLE));
        assertEquals(TIME_MORNING, iterationToTimeMapper.map(15 + NEXT_CYCLE));
    }

    @Test
    public void map_shouldReturnAfternoon_whenIterationIsIn16And23() throws Exception {
        assertEquals(TIME_AFTERNOON, iterationToTimeMapper.map(16));
        assertEquals(TIME_AFTERNOON, iterationToTimeMapper.map(20));
        assertEquals(TIME_AFTERNOON, iterationToTimeMapper.map(23));
        assertEquals(TIME_AFTERNOON, iterationToTimeMapper.map(16 + NEXT_CYCLE));
        assertEquals(TIME_AFTERNOON, iterationToTimeMapper.map(20 + NEXT_CYCLE));
        assertEquals(TIME_AFTERNOON, iterationToTimeMapper.map(23 + NEXT_CYCLE));
    }

    @Test
    public void map_shouldReturnAfternoon_whenIterationIsIn24And31() throws Exception {
        assertEquals(TIME_EVENING, iterationToTimeMapper.map(24));
        assertEquals(TIME_EVENING, iterationToTimeMapper.map(28));
        assertEquals(TIME_EVENING, iterationToTimeMapper.map(31));
        assertEquals(TIME_EVENING, iterationToTimeMapper.map(24 + NEXT_CYCLE));
        assertEquals(TIME_EVENING, iterationToTimeMapper.map(28 + NEXT_CYCLE));
        assertEquals(TIME_EVENING, iterationToTimeMapper.map(31 + NEXT_CYCLE));
    }
}