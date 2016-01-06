package com.zalas.traffic.simulator.view;

import com.zalas.traffic.domain.LightCycle;
import com.zalas.traffic.domain.TrafficDirection;
import com.zalas.traffic.domain.TrafficFlow;
import com.zalas.traffic.domain.TrafficModel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import static com.zalas.traffic.domain.TrafficDirection.*;

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

        drawTrafficFlows(g2d, width, height);

    }

    private void drawTrafficFlows(Graphics2D g2d, int width, int height) {
        int marginX = width / 3;
        int marginY = height / 3;

        int midWidthX = width / 2;
        int midWidthY = height / 2;

        HashMap<TrafficDirection, int[]> directionStaringPoints = new HashMap<>();
        directionStaringPoints.put(NORTH, new int[]{midWidthX, marginY});
        directionStaringPoints.put(EAST, new int[]{width - marginX, midWidthY});
        directionStaringPoints.put(SOUTH, new int[]{midWidthX, height - marginY});
        directionStaringPoints.put(WEST, new int[]{marginX, midWidthY});

        LightCycle lc = trafficModel.getLightCycle();

        if (lc != null) {
            for (TrafficFlow tf : lc.getTrafficFlows()) {

                int x1 = directionStaringPoints.get(tf.getFrom())[0];
                int y1 = directionStaringPoints.get(tf.getFrom())[1];
                int x2 = directionStaringPoints.get(tf.getTo())[0];
                int y2 = directionStaringPoints.get(tf.getTo())[1];
                drawArrowLine(g2d, x1, y1, x2, y2, 20, 10);
            }
        }
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

    private void drawArrowLine(Graphics2D g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.setColor(Color.GREEN);
        g.setStroke(new BasicStroke(2));
        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }
}
