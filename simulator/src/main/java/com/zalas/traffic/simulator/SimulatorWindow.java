package com.zalas.traffic.simulator;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulatorWindow extends JFrame {

    private final DrawPane drawPane = new DrawPane();
    private JButton nextIterationButton = new JButton("Next iteration >>");

    public void lunch() {

        JFrame frame = new JFrame("FrameDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(drawPane, BorderLayout.CENTER);
        frame.getContentPane().add(nextIterationButton, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        nextIterationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPane.repaint();
            }
        });

    }
}
