package com.zalas.traffic.simulator.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StatsTable extends JTable {

    private static final String[] COLUMN_NAMES = {"ITEM", "VALUE"};

    private static final int ROW_ITERATION = 0;
    private static final int ROW_TRAFFIC_NORTH = 1;
    private static final int ROW_TRAFFIC_EAST = 2;
    private static final int ROW_TRAFFIC_SOUTH = 3;
    private static final int ROW_TRAFFIC_WEST = 4;

    private static final int COLUMN_ITEM = 0;
    private static final int COLUMN_VALUE = 1;

    public StatsTable() {
        super(new DefaultTableModel(COLUMN_NAMES, 5));
        tableSetup();
        setVisible(true);
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    private void tableSetup() {
        this.setValueAt("Iteration: ", ROW_ITERATION, COLUMN_ITEM);
        this.setValueAt("Traffic N: ", ROW_TRAFFIC_NORTH, COLUMN_ITEM);
        this.setValueAt("Traffic E: ", ROW_TRAFFIC_EAST, COLUMN_ITEM);
        this.setValueAt("Traffic S: ", ROW_TRAFFIC_SOUTH, COLUMN_ITEM);
        this.setValueAt("Traffic W: ", ROW_TRAFFIC_WEST, COLUMN_ITEM);

        this.getTableHeader().setReorderingAllowed(false);
    }

    public void setIteration(int iteration) {
        this.setValueAt(iteration, ROW_ITERATION, COLUMN_VALUE);
    }

    public void setTrafficNorth(int traffic) {
        this.setValueAt(traffic, ROW_TRAFFIC_NORTH, COLUMN_VALUE);
    }

    public void setTrafficEast(int traffic) {
        this.setValueAt(traffic, ROW_TRAFFIC_EAST, COLUMN_VALUE);
    }

    public void setTrafficSouth(int traffic) {
        this.setValueAt(traffic, ROW_TRAFFIC_SOUTH, COLUMN_VALUE);
    }

    public void setTrafficWest(int traffic) {
        this.setValueAt(traffic, ROW_TRAFFIC_WEST, COLUMN_VALUE);
    }


}
