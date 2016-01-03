package com.zalas.traffic.simulator.main;

import org.apache.commons.cli.*;

public class CmdArgsParser {

    private final Options options = new Options();

    public CmdArgsParser() {
        Option controller = new Option("c", "controller", true, "type of controller");
        controller.setArgName("type");

        Option scenario = new Option("s", "scenario", true, "file with traffic scenario");
        controller.setArgName("file");

        options.addOption(controller);
        options.addOption(scenario);

    }

    public CmdArguments parse(String[] args) {
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            String controller = cmd.getOptionValue("c");
            String scenario = cmd.getOptionValue("s");

            validateRequiredOption(controller, "controller");
            validateRequiredOption(scenario, "scenario");

            return new CmdArguments(controller, scenario);
        } catch (ParseException e) {
            throw new CmdArgumentsException(e.getMessage());
        }
    }

    private void validateRequiredOption(String option, String optName) {
        if (option == null) {
            throw new CmdArgumentsException(optName + " option is required");
        }
    }

    public Options getOptions() {
        return options;
    }
}
