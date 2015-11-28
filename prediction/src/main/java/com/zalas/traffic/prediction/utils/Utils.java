package com.zalas.traffic.prediction.utils;


import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Utils {

    public static final String TIME_EVENING = "evening";
    public static final String TIME_MORNING = "morning";
    public static final String TIME_AFTERNOON = "afternoon";
    public static final String[] TIMES_OF_DAY = new String[]{TIME_MORNING, TIME_AFTERNOON, TIME_EVENING};

    public static final String[] STREETS = new String[]{"broniewskiegoN", "broniewskiegoS", "glowna", "kusocinskiego"};
    public static final int COLUMN_WITH_VALUES = 1;

    public static String getNetworksOutputDirectory() throws IOException {
        Properties p = loadProperties();
        return p.getProperty("target") + File.separator + p.getProperty("network_output_dirname") + File.separator;
    }

    public static String getNetworksAccuracyOutputDirectory() throws IOException {
        Properties p = loadProperties();
        return p.getProperty("target") + File.separator + p.getProperty("network_accuracy_output_dirname") + File.separator;
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
        return ArrayUtils.toPrimitive(subHistoricalDataArr);
    }

}
