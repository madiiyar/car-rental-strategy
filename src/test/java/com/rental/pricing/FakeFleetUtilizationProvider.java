package com.rental.pricing;

import com.rental.data.FleetUtilizationProvider;

class FakeFleetUtilizationProvider implements FleetUtilizationProvider {

    private double utilization;

    FakeFleetUtilizationProvider(double utilization) {
        this.utilization = utilization;
    }

    void setUtilization(double utilization) {
        this.utilization = utilization;
    }

    @Override
    public double currentUtilization() {
        return utilization;
    }
}