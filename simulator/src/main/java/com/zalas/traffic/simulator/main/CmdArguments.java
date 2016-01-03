package com.zalas.traffic.simulator.main;

public class CmdArguments {

    private String controllerType;
    private String scenarioPath;

    public CmdArguments(String controllerType, String scenarioPath) {
        this.controllerType = controllerType;
        this.scenarioPath = scenarioPath;
    }

    public String getControllerType() {
        return controllerType;
    }

    public String getScenarioPath() {
        return scenarioPath;
    }
}
