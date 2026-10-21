package com.rental.data;

public class SimulatedFleetUtilizationProvider implements FleetUtilizationProvider {

    private double utilization;

    public SimulatedFleetUtilizationProvider(double initialUtilization) {
        this.utilization = initialUtilization;
    }

    public void setUtilization(double utilization) {
        this.utilization = utilization;
    }

    @Override
    public double currentUtilization() {
        return utilization;
    }
}