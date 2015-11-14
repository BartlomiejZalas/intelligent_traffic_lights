package com.zalas.traffic.utils;


import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static final String TIME_EVENING = "evening";
    public static final String TIME_MORNING = "morning";
    public static final String TIME_AFTERNOON = "afternoon";
    public static final String[] TIMES_OF_DAY = new String[]{TIME_MORNING, TIME_AFTERNOON, TIME_EVENING};

    public static final String[] STREETS = new String[]{"broniewskiegoN", "broniewskiegoS", "glowna", "kusocinskiego"};
    public static final String PREDICTORS_OUTPUT_DIRECTORY = "/home/bartek/AITraffic/networks/";
    public static final int COLUMN_WITH_VALUES = 1;
    public static final String REPORT_OUTPUT_DIR = "/home/bartek/AITraffic/networks_accuracy/";


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
