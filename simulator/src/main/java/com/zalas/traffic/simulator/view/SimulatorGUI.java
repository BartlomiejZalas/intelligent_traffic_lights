package com.zalas.traffic.simulator.view;


import com.zalas.traffic.simulator.business.Simulator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SimulatorGUI extends JFrame {

    private JTextField autoIterationsNumberField = new JTextField("0");
    private JButton quickIterationButton = new JButton("Go");
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
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Traffic Simulator :: " + simulator.getControllerType());
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.getContentPane().add(drawPane, BorderLayout.CENTER);
            frame.getContentPane().add(createButtonsPanel(), BorderLayout.SOUTH);
            frame.getContentPane().add(createEastPanel(), BorderLayout.EAST);
            frame.pack();
            frame.setVisible(true);

            addActionToButtons();
            enableButton(nextIncomingTrafficButton);
        });

    }

    private JPanel createEastPanel() {
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());
        eastPanel.add(createStatsPanel(), BorderLayout.CENTER);
        eastPanel.add(createQuickIterationPanel(), BorderLayout.SOUTH);
        return eastPanel;
    }

    private JPanel createQuickIterationPanel() {
        JPanel quickIteration = new JPanel();
        quickIteration.setBackground(Color.white);
        quickIteration.setBorder(new EmptyBorder(10, 10, 10, 10));
        quickIteration.setLayout(new GridLayout(3, 1));
        quickIteration.add(new JLabel("Go to next iterations:"));
        quickIteration.add(autoIterationsNumberField);
        quickIteration.add(quickIterationButton);
        return quickIteration;
    }

    private void addActionToButtons() {
        nextIncomingTrafficButton.addActionListener(e -> handleIncomingTraffic());
        nextLightsButton.addActionListener(e -> changeLightCycle());
        nextMoveVehiclesButton.addActionListener(e -> moveVehicles());
        nextIteration.addActionListener(e -> startNextIteration());
        quickIterationButton.addActionListener(e -> doAutoIterations());
    }

    private void doAutoIterations() {
        int iterationsToDo = Integer.parseInt(autoIterationsNumberField.getText());
        for (int i = 0; i < iterationsToDo; i++) {
            handleIncomingTraffic();
            changeLightCycle();
            moveVehicles();
            startNextIteration();
        }

    }

    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.add(statsTable.getTableHeader(), BorderLayout.NORTH);
        statsPanel.add(statsTable, BorderLayout.CENTER);
        updateStatsTable();
        return statsPanel;
    }

    private JPanel createButtonsPanel() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout());
        buttons.add(nextIncomingTrafficButton);
        buttons.add(nextLightsButton);
        buttons.add(nextMoveVehiclesButton);
        buttons.add(nextIteration);
        return buttons;
    }

    private void changeLightCycle() {
        simulator.changeLightCycle();
        enableButton(nextMoveVehiclesButton);
        drawPane.repaint();
    }

    private void moveVehicles() {
        simulator.moveVehicles();
        enableButton(nextIteration);
        drawPane.repaint();
    }

    private void handleIncomingTraffic() {
        simulator.handleTraffic();
        enableButton(nextLightsButton);
        drawPane.repaint();
    }

    private void startNextIteration() {
        simulator.nextIteration();
        updateStatsTable();
        enableButton(nextIncomingTrafficButton);
        drawPane.repaint();
    }

    private void enableButton(JButton activeButton) {
        nextLightsButton.setEnabled(false);
        nextMoveVehiclesButton.setEnabled(false);
        nextIncomingTrafficButton.setEnabled(false);
        nextIteration.setEnabled(false);

        activeButton.setEnabled(true);
    }

    private void updateStatsTable() {
        statsTable.setIteration(simulator.getTrafficModel().getIteration());
        statsTable.setTrafficNorth(simulator.getTrafficModel().getTrafficNorth());
        statsTable.setTrafficEast(simulator.getTrafficModel().getTrafficEast());
        statsTable.setTrafficSouth(simulator.getTrafficModel().getTrafficSouth());
        statsTable.setTrafficWest(simulator.getTrafficModel().getTrafficWest());
    }
}
