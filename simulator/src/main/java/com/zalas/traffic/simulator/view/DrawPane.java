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
        Graphics2D g2d = (Graphics2D) g;

        int width = getBounds().width;
        int height = getBounds().height;
        int mediumFontSize = getFontSize(width, height, 0.15);

        g.setColor(getRoadColor());
        g.fillRect(width / 3, 0, width / 3, height);
        g.fillRect(0, height / 3, width, height / 3);

        drawTrafficStatus(g2d, width, height, mediumFontSize);

    }

    private void drawTrafficStatus(Graphics2D g2d, int width, int height, int fontSize) {
        drawString(g2d, width / 2, 0, fontSize, "" + trafficModel.getTrafficNorth());
        drawString(g2d, width - fontSize, (height / 2) - (int) (fontSize * 0.75), fontSize, "" + trafficModel.getTrafficEast());
        drawString(g2d, width / 2, height - (int) (1.75 * fontSize), fontSize, "" + trafficModel.getTrafficSouth());
        drawString(g2d, fontSize, (height / 2) - (int) (fontSize * 0.75), fontSize, "" + trafficModel.getTrafficWest());
    }

    private void drawString(Graphics2D g2d, int startPointX, int startPointY, int fontSize, String s) {
        g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
        g2d.setColor(Color.WHITE);
        int stringLen = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getWidth();
        int stringHeight = (int) g2d.getFontMetrics().getStringBounds(s, g2d).getHeight();
        int start = startPointX - stringLen / 2;
        g2d.drawString(s, start, startPointY + stringHeight);
    }

    private int getFontSize(int width, int height, double fraction) {
        return (int) (Math.min(width, height) * fraction);
    }

    private Color getRoadColor() {
        return Color.DARK_GRAY;
    }
}
