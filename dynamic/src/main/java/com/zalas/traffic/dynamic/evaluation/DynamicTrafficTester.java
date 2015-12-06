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

    public List<Boolean> test() {
        List<Boolean> results = newArrayList();
        for (DataRow row : dataSet.getDataRows()) {
            List<Integer> trafficStatus = convertToIntList(row.getInputs());
            results.add(evaluate(row.getOutput(), controller.getLightCycle(trafficStatus)));
        }
        return results;
    }

    private boolean evaluate(double output, int lightCycle) {
        System.out.println(lightCycle + "==" + output);
        return lightCycle == output;
    }

    private List<Integer> convertToIntList(double[] inputs) {
        return Arrays.stream(inputs)
                .boxed()
                .map(Double::intValue)
                .collect(Collectors.toList());
    }
}
