package com.zalas.traffic.io.report;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.google.inject.internal.util.$Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DynamicTrafficTestReportDataTest {

    @Mock
    private DynamicTrafficReportData dynamicTrafficReportData;
    private List<Boolean> testResults = newArrayList(true, false, true);

    private DynamicTrafficTestReportData dynamicTrafficTestReportData;

    @Before
    public void setUp() throws Exception {
        dynamicTrafficTestReportData = new DynamicTrafficTestReportData(dynamicTrafficReportData, testResults);

    }

    @Test
    public void getTrafficStatuses_shouldDelegate() throws Exception {
        dynamicTrafficTestReportData.getTrafficStatuses();
        verify(dynamicTrafficReportData).getTrafficStatuses();
    }

    @Test
    public void getLightCycles_shouldDelegate() throws Exception {
        dynamicTrafficTestReportData.getLightCycles();
        verify(dynamicTrafficReportData).getLightCycles();
    }

    @Test
    public void summaryColumn_shouldReturnCorrespondingElementFromResultList() throws Exception {
        assertEquals("OK", dynamicTrafficTestReportData.summaryColumn(0));
        assertEquals("ERROR", dynamicTrafficTestReportData.summaryColumn(1));
        assertEquals("OK", dynamicTrafficTestReportData.summaryColumn(2));
    }
}