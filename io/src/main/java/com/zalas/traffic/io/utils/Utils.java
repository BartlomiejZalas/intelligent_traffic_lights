package com.zalas.traffic.io.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.apache.commons.lang3.ArrayUtils.toPrimitive;

public class Utils {

    public static final String TIME_MORNING = "morning";
    public static final String TIME_AFTERNOON = "afternoon";
    public static final String TIME_EVENING = "evening";
    public static final String[] TIMES_OF_DAY = new String[]{TIME_MORNING, TIME_AFTERNOON, TIME_EVENING};


    public static final String STREET_NORTH = "broniewskiegoN";
    public static final String STREET_EAST = "glowna";
    public static final String STREET_SOUTH = "broniewskiegoS";
    public static final String STREET_WEST = "kusocinskiego";
    public static final String[] STREETS = new String[]{STREET_NORTH, STREET_EAST, STREET_SOUTH, STREET_WEST};
    public static final int COLUMN_WITH_VALUES = 1;

    public static String getNetworksOutputDirectory() throws IOException {
        Properties p = loadProperties();
        return p.getProperty("target") + File.separator + "networks" + File.separator;
    }

    public static String getNetworksAccuracyOutputDirectory() throws IOException {
        Properties p = loadProperties();
        return p.getProperty("target") + File.separator + p.getProperty("network_accuracy_output_dirname") + File.separator;
    }

    public static String getDynamicOutputDirectory() throws IOException {
        Properties p = loadProperties();
        return p.getProperty("target") + File.separator + p.getProperty("dynamic_output_dirname") + File.separator;
    }


    private static Properties loadProperties() throws IOException {
        InputStream is = Utils.class.getResourceAsStream("/config.properties");
        Properties p = new Properties();
        p.load(is);
        return p;
    }

    public static List<String> getDataFileNamesForTime(String time) {
        List<String> files = new ArrayList<>();
        for (String street : STREETS) {
            files.add(street + "-" + time);
        }
        return files;
    }

    public static double[] convertListToArray(List<Double> subHistoricalData) {
        Double[] subHistoricalDataArr = new Double[subHistoricalData.size()];
        subHistoricalData.toArray(subHistoricalDataArr);
        return toPrimitive(subHistoricalDataArr);
    }

}
