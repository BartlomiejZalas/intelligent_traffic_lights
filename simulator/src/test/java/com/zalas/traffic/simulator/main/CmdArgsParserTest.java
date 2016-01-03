package com.zalas.traffic.simulator.main;

import org.apache.commons.cli.Options;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CmdArgsParserTest {

    private static final String DYNAMIC_CTRL_TYPE = "dynamic";
    private static final String SCENARIO_FILE = "file";
    private CmdArgsParser cmdArgsParser;

    @Before
    public void setUp() throws Exception {
        this.cmdArgsParser = new CmdArgsParser();
    }

    @Test
    public void parse_shouldReturnProperObject_whenCorrectArgsAreGiven() throws Exception {
        CmdArguments cmdArguments = cmdArgsParser.parse(new String[]{"-c", DYNAMIC_CTRL_TYPE, "-s", SCENARIO_FILE});

        assertEquals(cmdArguments.getControllerType(), DYNAMIC_CTRL_TYPE);
        assertEquals(cmdArguments.getScenarioPath(), SCENARIO_FILE);
    }

    @Test(expected = CmdArgumentsException.class)
    public void parse_shouldTrowException_whenStrategyArgIsMissing() throws Exception {
        cmdArgsParser.parse(new String[]{"-c", DYNAMIC_CTRL_TYPE});
    }

    @Test(expected = CmdArgumentsException.class)
    public void parse_shouldTrowException_whenControllerArgIsMissing() throws Exception {
        cmdArgsParser.parse(new String[]{"-s", SCENARIO_FILE});
    }

    @Test(expected = CmdArgumentsException.class)
    public void parse_shouldTrowException_whenNoArgGivenToScenario() throws Exception {
        cmdArgsParser.parse(new String[]{"-c", DYNAMIC_CTRL_TYPE, "-s"});
    }

    @Test
    public void getOptions_shouldReturnOptionsObject() throws Exception {
        assertEquals(Options.class, cmdArgsParser.getOptions().getClass());
    }
}