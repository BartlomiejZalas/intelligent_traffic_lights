package com.zalas.traffic.simulator.view;


import com.zalas.traffic.simulator.Simulator;

import javax.swing.*;
import java.awt.*;

public class SimulatorGUI extends JFrame {

    private DrawPane drawPane;
    private JButton nextLightsButton = new JButton("Change Light Cycle");
    private JButton nextMoveVehiclesButton = new JButton("Move Vehicles");
    private JButton nextIncomingTrafficButton = new JButton("Add Incoming Traffic");
    private Simulator simulator;

    public SimulatorGUI(Simulator simulator) {
        this.simulator = simulator;
        this.drawPane = new DrawPane(simulator.getTrafficModel());
    }

    public void lunch() {

        JFrame frame = new JFrame("FrameDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(drawPane, BorderLayout.CENTER);
        frame.getContentPane().add(createButtonsPanel(), BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        nextLightsButton.addActionListener(e -> changeLightCycle());
        nextMoveVehiclesButton.addActionListener(e -> moveVehicles());
        nextIncomingTrafficButton.addActionListener(e -> handleIncomingTraffic());

    }

    private JPanel createButtonsPanel() {
        JPanel buttons = new JPanel();
        buttons.add(nextLightsButton);
        buttons.add(nextMoveVehiclesButton);
        buttons.add(nextIncomingTrafficButton);
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
}
