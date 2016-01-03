package com.zalas.traffic.dynamic.controller;

import com.zalas.traffic.dynamic.controller.scaler.FourIntervalTrafficScaler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FourIntervalTrafficScalerTest {

    private FourIntervalTrafficScaler trafficScaler;

    @Before
    public void setUp() throws Exception {
        trafficScaler = new FourIntervalTrafficScaler();
    }

    @Test
    public void scale_shouldReturn1_whenTrafficIsInFirstInterval() throws Exception {
        assertEquals(1, trafficScaler.scale(0));
        assertEquals(1, trafficScaler.scale(25));
        assertEquals(1, trafficScaler.scale(49));
    }

    @Test
    public void scale_shouldReturn2_whenTrafficIsInSecondInterval() throws Exception {
        assertEquals(2, trafficScaler.scale(50));
        assertEquals(2, trafficScaler.scale(75));
        assertEquals(2, trafficScaler.scale(99));
    }

    @Test
    public void scale_shouldReturn3_whenTrafficIsInThirdInterval() throws Exception {
        assertEquals(3, trafficScaler.scale(100));
        assertEquals(3, trafficScaler.scale(125));
        assertEquals(3, trafficScaler.scale(149));
    }

    @Test
    public void scale_shouldReturn4_whenTrafficIsInAboveInterval() throws Exception {
        assertEquals(4, trafficScaler.scale(150));
        assertEquals(4, trafficScaler.scale(999));
    }

    @Test(expected = RuntimeException.class)
    public void scale_shouldThrowRuntimeException_whenTrafficIsNotInAnyInterval() throws Exception {
        trafficScaler.scale(-1);
    }
}