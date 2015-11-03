package com.zalas.traffic.io;

public class ReportRow {

    private final double expectedValue;
    private final double prediction;
    private final double percentageAccuracy;

    public ReportRow(double expectedValue, double prediction, double percentageAccuracy) {

        this.expectedValue = expectedValue;
        this.prediction = prediction;
        this.percentageAccuracy = percentageAccuracy;
    }

    public double getExpectedValue() {
        return expectedValue;
    }

    public double getPrediction() {
        return prediction;
    }

    public double getPercentageAccuracy() {
        return percentageAccuracy;
    }
}
