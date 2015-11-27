package com.zalas.traffic.prediction.learning_data;

public class LearningDataRow {
    private double[] dataToLearn;
    private double expectedValue;

    public LearningDataRow(double[] dataToLearn, double expectedValue) {
        this.dataToLearn = dataToLearn;
        this.expectedValue = expectedValue;
    }

    public double[] getDataToLearn() {
        return dataToLearn;
    }

    public double getExpectedValue() {
        return expectedValue;
    }
}
