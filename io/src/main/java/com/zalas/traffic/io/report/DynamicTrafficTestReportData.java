package com.zalas.traffic.io.report;

import java.util.List;

public class DynamicTrafficTestReportData implements ReportData {

    private final DynamicTrafficReportData reportData;
    private final List<Boolean> testResults;

    public DynamicTrafficTestReportData(DynamicTrafficReportData reportData, List<Boolean> testResults) {

        this.reportData = reportData;
        this.testResults = testResults;
    }

    @Override
    public List<List<Integer>> getTrafficStatuses() {
        return reportData.getTrafficStatuses();
    }

    @Override
    public List<Integer> getLightCycles() {
        return reportData.getLightCycles();
    }

    @Override
    public String summaryColumn(int row) {
        return testResults.get(row) ? "OK": "ERROR";
    }
}
