package com.zalas.traffic.dynamic.evaluation;

public class TestResult {

    private final boolean wasCorrect;
    private final int expectedLightCycle;
    private final int computedLightCycle;

    public TestResult(boolean wasCorrect, int expectedLightCycle, int computedLightCycle) {
        this.wasCorrect = wasCorrect;
        this.expectedLightCycle = expectedLightCycle;
        this.computedLightCycle = computedLightCycle;
    }

    public boolean getWasCorrect() {
        return wasCorrect;
    }

    public int getExpectedLightCycle() {
        return expectedLightCycle;
    }

    public int getComputedLightCycle() {
        return computedLightCycle;
    }
}
