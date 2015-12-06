package com.zalas.traffic.io.report;

import org.junit.Test;

import java.util.List;

import static com.google.inject.internal.util.$Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class DynamicTrafficReportDataTest {

    @Test
    public void getTrafficStatuses_shouldReturnIntegerValuesOfTrafficStatuses() throws Exception {
        List<List<Double>> trafficStatus = newArrayList(newArrayList(1.0, 2.0, 3.0, 4.0), newArrayList(1.0, 2.0, 3.0, 4.0));
        List<Double> lightCycles = newArrayList(3.0, 7.0);
        DynamicTrafficReportData dynamicTrafficReportData = new DynamicTrafficReportData(trafficStatus, lightCycles);

        List<List<Integer>> trafficStatusesReult = dynamicTrafficReportData.getTrafficStatuses();

        assertEquals(newArrayList(newArrayList(1, 2, 3, 4), newArrayList(1, 2, 3, 4)), trafficStatusesReult);
    }

    @Test
    public void getLightCycles_shouldReturnIntegerValuesOfTrafficStatuses() throws Exception {
        List<List<Double>> trafficStatus = newArrayList(newArrayList(1.0, 2.0, 3.0, 4.0), newArrayList(1.0, 2.0, 3.0, 4.0));
        List<Double> lightCycles = newArrayList(3.0, 7.0);
        DynamicTrafficReportData dynamicTrafficReportData = new DynamicTrafficReportData(trafficStatus, lightCycles);

        List<Integer> lightCyclesResult = dynamicTrafficReportData.getLightCycles();

        assertEquals(newArrayList(3, 7), lightCyclesResult);
    }

    @Test
    public void getLightCycles_shouldReturnIntegerValuesOfTrafficStatuses_whenOutputsAreNotRounded() throws Exception {
        List<Double> lightCycles = newArrayList(3.5, 7.6, 2.1);
        DynamicTrafficReportData dynamicTrafficReportData = new DynamicTrafficReportData(newArrayList(), lightCycles);

        List<Integer> lightCyclesResult = dynamicTrafficReportData.getLightCycles();

        assertEquals(newArrayList(4, 8, 2), lightCyclesResult);
    }

    @Test
    public void summaryColumn_shouldAlwaysReturnEmptyString() throws Exception {
        DynamicTrafficReportData dynamicTrafficReportData = new DynamicTrafficReportData(newArrayList(), newArrayList());

        assertEquals("", dynamicTrafficReportData.summaryColumn(0));
        assertEquals("", dynamicTrafficReportData.summaryColumn(1));
        assertEquals("", dynamicTrafficReportData.summaryColumn(2));
        assertEquals("", dynamicTrafficReportData.summaryColumn(999));
    }
}