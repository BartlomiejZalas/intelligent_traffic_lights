package com.zalas.traffic.dynamic.evaluation;

import com.zalas.traffic.dynamic.controller.DynamicTrafficController;
import com.zalas.traffic.dynamic.data.DataRow;
import com.zalas.traffic.dynamic.data.DataSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DynamicTrafficTesterTest {

    @Mock
    DataSet dataset;
    @Mock
    DynamicTrafficController dynamicTrafficController;

    private DynamicTrafficTester dynamicTrafficTester;

    @Before
    public void setUp() throws Exception {
        dynamicTrafficTester = new DynamicTrafficTester(dataset, dynamicTrafficController);
    }

    @Test
    public void test_shouldReturnTrue_whenResultsAreAsExpected() throws Exception {
        given(dataset.getDataRows()).willReturn(newArrayList(new DataRow(new double[]{1.0, 2.0, 3.0, 4.0}, 5.0)));
        given(dynamicTrafficController.getLightCycle(1, 2, 3, 4)).willReturn(5);

        List<TestResult> results = dynamicTrafficTester.test();

        assertEquals(true, results.get(0).getWasCorrect());
        assertEquals(5, results.get(0).getComputedLightCycle());
        assertEquals(5, results.get(0).getExpectedLightCycle());
    }

    @Test
    public void test_shouldReturnFalse_whenResultsAreDifferentThanExpected() throws Exception {
        given(dataset.getDataRows()).willReturn(newArrayList(new DataRow(new double[]{1.0, 2.0, 3.0, 4.0}, 5.0)));
        given(dynamicTrafficController.getLightCycle(1, 2, 3, 4)).willReturn(8);

        List<TestResult> results = dynamicTrafficTester.test();

        assertEquals(false, results.get(0).getWasCorrect());
        assertEquals(8, results.get(0).getComputedLightCycle());
        assertEquals(5, results.get(0).getExpectedLightCycle());
    }

    @Test
    public void test_shouldReturnProperOrderOfResults() throws Exception {
        DataRow dataRow1 = new DataRow(new double[]{1.0, 2.0, 3.0, 4.0}, 5.0);
        DataRow dataRow2 = new DataRow(new double[]{2.0, 2.0, 2.0, 2.0}, 2.0);
        DataRow dataRow3 = new DataRow(new double[]{4.0, 3.0, 2.0, 1.0}, 10.0);
        given(dataset.getDataRows()).willReturn(newArrayList(dataRow1, dataRow2, dataRow3));

        given(dynamicTrafficController.getLightCycle(1, 2, 3, 4)).willReturn(5);
        given(dynamicTrafficController.getLightCycle(2, 2, 2, 2)).willReturn(-2);
        given(dynamicTrafficController.getLightCycle(4, 3, 2, 1)).willReturn(10);

        List<TestResult> results = dynamicTrafficTester.test();

        assertEquals(true, results.get(0).getWasCorrect());
        assertEquals(false, results.get(1).getWasCorrect());
        assertEquals(true, results.get(2).getWasCorrect());
    }
}