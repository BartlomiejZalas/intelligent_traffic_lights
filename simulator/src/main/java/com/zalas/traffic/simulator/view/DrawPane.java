package com.zalas.traffic.simulator.view;

import com.zalas.traffic.simulator.model.TrafficModel;

import javax.swing.*;
import java.awt.*;

public class DrawPane extends JPanel {

    private final TrafficModel trafficModel;

    public DrawPane(TrafficModel trafficModel) {
        this.trafficModel = trafficModel;
        setPreferredSize(new Dimension(400, 400));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d =  (Graphics2D)g;
        int width = getWidth();
        int height = getHeight();

        g.setColor(getRoadColor());
        g.fillRect(width / 3, 0, width / 3, height);
        g.fillRect(0, height / 3, width, height / 3);

        String s = "" + trafficModel.getTrafficNorth();
        g2d.setFont(new Font("Arial", Font.BOLD, getFontSize(width, height)));
        g2d.setColor(Color.WHITE);
        int stringLen = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        int stringHeight = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight();
        int start = width/2 - stringLen/2;
        g2d.drawString(s, start , stringHeight);

    }

    private int getFontSize(int width, int height) {
        return (int)(Math.min(width, height)*0.2);
    }

    private Color getRoadColor() {
        return Color.DARK_GRAY;
    }
}
