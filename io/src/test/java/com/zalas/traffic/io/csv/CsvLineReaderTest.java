package com.zalas.traffic.io.csv;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class CsvLineReaderTest {

    private CsvLineReader csvLineReader;
    private File testCsvFile;


    @Before
    public void setUp() throws Exception {
        csvLineReader = new CsvLineReader();
        testCsvFile = new File(getClass().getResource("/testRawTestData.csv").toURI());
    }

    @Test
    public void getLine_shouldReturnLines_whenFileGiven() throws Exception {
        List<Double> values = csvLineReader.getValuesFromColumn(testCsvFile, 1);

        assertEquals(values.get(0), 1419.04, 0.01);
        assertEquals(values.get(1), 1413.38, 0.01);
        assertEquals(values.get(2), 1398.96, 0.01);
        assertEquals(values.get(3), 1398.08, 0.01);
        assertEquals(values.get(4), 1382.2, 0.01);

    }

    @Test
    public void getRows_shouldReturDataInListFormat() throws Exception {
        List<List<Double>> rows = csvLineReader.getRows(testCsvFile);

        assertEquals(rows.get(0), (List) newArrayList(1.0, 1419.04));
        assertEquals(rows.get(1), (List) newArrayList(2.0, 1413.38));
        assertEquals(rows.get(2), (List) newArrayList(3.0, 1398.96));
        assertEquals(rows.get(3), (List) newArrayList(4.0, 1398.08));
        assertEquals(rows.get(4), (List) newArrayList(5.0, 1382.2));
    }
}