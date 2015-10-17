package com.zalas.traffic.prediction;

import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.SupervisedLearning;

public class DebugEventListener implements LearningEventListener {

    public void handleLearningEvent(LearningEvent learningEvent) {
        SupervisedLearning rule = (SupervisedLearning) learningEvent.getSource();
        System.out.println("Network error for interation " + rule.getCurrentIteration() + ": " + rule.getTotalNetworkError());
    }
}
