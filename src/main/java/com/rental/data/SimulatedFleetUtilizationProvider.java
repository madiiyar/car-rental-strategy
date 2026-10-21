package com.rental.data;

public class SimulatedFleetUtilizationProvider implements FleetUtilizationProvider {

    private static final double[] READINGS = {0.40, 0.65, 0.85, 0.95};

    private int next = 0;

    @Override
    public double currentUtilization() {
        double reading = READINGS[next];
        next = (next + 1) % READINGS.length;
        return reading;
    }
}