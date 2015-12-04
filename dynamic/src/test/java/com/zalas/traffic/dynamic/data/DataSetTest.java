package com.zalas.traffic.dynamic.data;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DataSetTest {

    private DataSet dataSet;
    private static final double[] INPUTS_1 = new double[]{0, 2, 4};
    private static final double[] INPUTS_2 = new double[]{0, 1, 2};
    private static final double OUTPUT_1 = 0;
    private static final double OUTPUT_2 = 15;


    @Test
    public void getInputsAsArray_shouldReturnInputInProperType() throws Exception {
        List<DataRow> rows = creteListOfRows(new double[][]{INPUTS_1, INPUTS_2}, new double[]{OUTPUT_1, OUTPUT_2});
        dataSet = new DataSet(rows);

        double[][] result = dataSet.getInputsAsArray();

        assertArrayEquals(new double[]{0, 0.5, 1} ,result[0], 0.1);
        assertArrayEquals(new double[]{0, 0.25, 0.5}, result[1], 0.1);
    }

    @Test
    public void getOutputAsArray_shouldReturnInputInProperType() throws Exception {
        List<DataRow> rows = creteListOfRows(new double[][]{INPUTS_1, INPUTS_2}, new double[]{OUTPUT_1, OUTPUT_2});
        dataSet = new DataSet(rows);

        double[][] result = dataSet.getOutputAsArray();

        assertEquals(0, result[0][0], 0.1);
        assertEquals(1, result[1][0], 0.1);
    }

    @Test
    public void getDataRows() throws Exception {
        List<DataRow> dataRows = new ArrayList<>();
        dataSet = new DataSet(dataRows);

        assertEquals(dataRows, dataSet.getDataRows());

    }

    private List<DataRow> creteListOfRows(double[][] inputs, double[] outputs) {
        ArrayList<DataRow> list = new ArrayList<>();
        for(int i = 0; i < inputs.length; i++) {
            list.add(new DataRow(inputs[i], outputs[i]));
        }
        return list;
    }


}