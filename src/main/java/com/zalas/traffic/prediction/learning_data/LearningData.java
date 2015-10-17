package com.zalas.traffic.prediction.learning_data;

import java.util.ArrayList;

public class LearningData {

    private static final String INCONSISTENT_EXCEPTION_MESSAGE = "Inconsistent number of learning data - defined: %d actual: %d";

    private ArrayList<LearningDataRow> learningDataRows = new ArrayList<LearningDataRow>();
    private int numberOfLearningElementsInRow;

    public LearningData(int numberOfLearningElementsInRow) {
        this.numberOfLearningElementsInRow = numberOfLearningElementsInRow;
    }

    public void addRow(double[] dataToLearn, double expectedResult) {
        checkDataConsistency(dataToLearn);
        learningDataRows.add(new LearningDataRow(dataToLearn, expectedResult));
    }

    private void checkDataConsistency(double[] dataToLearn) {
        if (dataToLearn.length != numberOfLearningElementsInRow) {
            String message = String.format(INCONSISTENT_EXCEPTION_MESSAGE, numberOfLearningElementsInRow, dataToLearn.length);
            throw new LearningDataException(message);
        }
    }

    public int countRows() {
        return learningDataRows.size();
    }

    public LearningDataRow getRow(int row) {
        return learningDataRows.get(row);
    }
}
