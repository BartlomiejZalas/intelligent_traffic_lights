package com.zalas.traffic.io;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CsvLineReaderTest {

    private CsvLineReader csvLineReader;

    @Before
    public void setUp() throws Exception {
        csvLineReader = new CsvLineReader();
    }

    @Test
    public void getLine_shouldReturnLines_whenFileGiven() throws Exception {
        File testCsvFile = new File(getClass().getResource("/testRawTestData.csv").toURI());
        ArrayList<Double> values = csvLineReader.getValuesFromColumn(testCsvFile, 1);

        assertEquals(values.get(0), 1419.04, 0.01);
        assertEquals(values.get(1), 1413.38, 0.01);
        assertEquals(values.get(2), 1398.96, 0.01);
        assertEquals(values.get(3), 1398.08, 0.01);
        assertEquals(values.get(4), 1382.2, 0.01);

    }
}