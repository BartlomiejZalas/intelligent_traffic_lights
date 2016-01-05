package com.zalas.traffic.dynamic.evaluation;

import com.zalas.traffic.dynamic.controller.DynamicTrafficController;
import com.zalas.traffic.dynamic.data.DataRow;
import com.zalas.traffic.dynamic.data.DataSet;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.inject.internal.util.$Lists.newArrayList;

public class DynamicTrafficTester {
    private final DataSet dataSet;
    private final DynamicTrafficController controller;

    public DynamicTrafficTester(DataSet dataSet, DynamicTrafficController controller) {
        this.dataSet = dataSet;
        this.controller = controller;
    }

    public List<TestResult> test() {
        List<TestResult> results = newArrayList();

        for (DataRow row : dataSet.getDataRows()) {

            List<Integer> trafficStatus = convertToIntList(row.getInputs());
            int expectedValue = (int) row.getOutput();
            int computedValue = controller.getLightCycle(trafficStatus.get(0),trafficStatus.get(1),trafficStatus.get(2),trafficStatus.get(3), 1);
            boolean wasCorrect = evaluate(expectedValue, computedValue);

            results.add(new TestResult(wasCorrect, expectedValue, computedValue));
        }
        return results;
    }

    private boolean evaluate(double expectedLightCycle, int lightCycle) {
        System.out.println(lightCycle + "==" + expectedLightCycle + " is " +(lightCycle == expectedLightCycle));
        return lightCycle == expectedLightCycle;
    }

    private List<Integer> convertToIntList(double[] inputs) {
        return Arrays.stream(inputs)
                .boxed()
                .map(Double::intValue)
                .collect(Collectors.toList());
    }
}
