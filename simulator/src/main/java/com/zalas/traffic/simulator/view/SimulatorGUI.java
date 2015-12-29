package com.zalas.traffic.simulator.view;


import com.zalas.traffic.simulator.Simulator;

import javax.swing.*;
import java.awt.*;

public class SimulatorGUI extends JFrame {

    private JButton nextLightsButton = new JButton("Change Light Cycle");
    private JButton nextMoveVehiclesButton = new JButton("Move Vehicles");
    private JButton nextIncomingTrafficButton = new JButton("Add Incoming Traffic");
    private JButton nextIteration = new JButton("Next Iteration");

    private DrawPane drawPane;
    private StatsTable statsTable = new StatsTable();

    private Simulator simulator;

    public SimulatorGUI(Simulator simulator) {
        this.simulator = simulator;
        this.drawPane = new DrawPane(simulator.getTrafficModel());
    }

    public void lunch() {

        JFrame frame = new JFrame("Traffic Simulator");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(drawPane, BorderLayout.CENTER);
        frame.getContentPane().add(createButtonsPanel(), BorderLayout.SOUTH);
        frame.getContentPane().add(createStatsPanel(), BorderLayout.EAST);
        frame.pack();
        frame.setVisible(true);

        addActionToButtons();

    }

    private void addActionToButtons() {
        nextLightsButton.addActionListener(e -> changeLightCycle());
        nextMoveVehiclesButton.addActionListener(e -> moveVehicles());
        nextIncomingTrafficButton.addActionListener(e -> handleIncomingTraffic());
        nextIteration.addActionListener(e -> startNextIteration());
    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.add(statsTable.getTableHeader(), BorderLayout.NORTH);
        statsPanel.add(statsTable, BorderLayout.CENTER);
        updateStatsTable();
        return statsPanel;
    }

    private void startNextIteration() {
        simulator.nextIteration();
        updateStatsTable();
        drawPane.repaint();
    }

    private JPanel createButtonsPanel() {
        JPanel buttons = new JPanel();
        buttons.add(nextLightsButton);
        buttons.add(nextMoveVehiclesButton);
        buttons.add(nextIncomingTrafficButton);
        buttons.add(nextIteration);
        return buttons;
    }

    private void handleIncomingTraffic() {
        simulator.handleTraffic();
        drawPane.repaint();
    }

    private void moveVehicles() {
        simulator.moveVehicles();
        drawPane.repaint();
    }

    private void changeLightCycle() {
        simulator.changeLightCycle();
        drawPane.repaint();
    }

    private void updateStatsTable() {
        statsTable.setIteration(simulator.getTrafficModel().getIteration());
        statsTable.setTrafficNorth(simulator.getTrafficModel().getTrafficNorth());
        statsTable.setTrafficEast(simulator.getTrafficModel().getTrafficEast());
        statsTable.setTrafficSouth(simulator.getTrafficModel().getTrafficSouth());
        statsTable.setTrafficWest(simulator.getTrafficModel().getTrafficWest());
    }
}
