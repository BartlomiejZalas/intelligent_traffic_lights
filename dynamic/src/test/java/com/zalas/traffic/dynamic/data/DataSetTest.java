package com.zalas.traffic.dynamic.data;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.inject.internal.util.$Lists.newArrayList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DataSetTest {

    private DataSet dataSet;
    private static final double[] INPUTS_1 = new double[]{0, 2, 4};
    private static final double[] INPUTS_2 = new double[]{0, 1, 2};
    private static final double OUTPUT_1 = 0;
    private static final double OUTPUT_2 = 15;
    private static final double OUTPUT_3 = 8;


    @Test
    public void getInputsAsNormalizedArray_shouldReturnInputInProperType() throws Exception {
        List<DataRow> rows = creteListOfRows(new double[][]{INPUTS_1, INPUTS_2}, new double[]{OUTPUT_1, OUTPUT_2});
        dataSet = new DataSet(rows);

        double[][] result = dataSet.getInputsAsNormalizedArray();

        assertArrayEquals(new double[]{0, 0.5, 1}, result[0], 0.1);
        assertArrayEquals(new double[]{0, 0.25, 0.5}, result[1], 0.1);
    }

    @Test
    public void getOutputAsNormalizedArray_shouldReturnInputInProperType() throws Exception {
        List<DataRow> rows = creteListOfRows(new double[][]{INPUTS_1, INPUTS_1, INPUTS_1}, new double[]{OUTPUT_1, OUTPUT_2, OUTPUT_3});
        dataSet = new DataSet(rows);

        double[][] result = dataSet.getOutputAsNormalizedArray();

        assertEquals(0, result[0][0], 0.1);
        assertEquals(1, result[1][0], 0.1);
        assertEquals(0.5, result[2][0], 0.1);
    }

    @Test
    public void getDataRows() throws Exception {
        dataSet = new DataSet(newArrayList());

        assertEquals(new ArrayList<DataRow>(), dataSet.getDataRows());
    }

    @Test
    public void getInputsAsList() throws Exception {
        dataSet = new DataSet(newArrayList(new DataRow(new double[]{1.0, 2.0}, OUTPUT_3)));

        List<List<Double>> inputsAsList = dataSet.getInputsAsList();

        List<List<Double>> expected = Lists.<List<Double>>newArrayList(newArrayList(1.0, 2.0));
        assertEquals(expected, inputsAsList);
    }

    @Test
    public void getOutputsAsList() throws Exception {
        dataSet = new DataSet(newArrayList(new DataRow(new double[]{1.0, 2.0}, OUTPUT_3)));

        List<Double> outputAsList = dataSet.getOutputAsList();

        assertEquals(OUTPUT_3, outputAsList.get(0), 0.1);
    }

    private List<DataRow> creteListOfRows(double[][] inputs, double[] outputs) {
        ArrayList<DataRow> list = new ArrayList<>();
        for (int i = 0; i < inputs.length; i++) {
            list.add(new DataRow(inputs[i], outputs[i]));
        }
        return list;
    }


}