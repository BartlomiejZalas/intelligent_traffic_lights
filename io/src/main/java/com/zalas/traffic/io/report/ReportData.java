package com.zalas.traffic.io.report;


import java.util.List;

public interface ReportData {
    List<List<Integer>> getTrafficStatuses();
    List<Integer> getLightCycles() ;
    String summaryColumn(int row);
}
