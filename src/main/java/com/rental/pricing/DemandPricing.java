package com.rental.pricing;

import com.rental.data.FleetUtilizationProvider;
import com.rental.model.RentalRequest;

public class DemandPricing implements PricingStrategy {

    private static final double DEMAND_FACTOR = 0.5;

    private final FleetUtilizationProvider utilizationProvider;

    public DemandPricing(FleetUtilizationProvider utilizationProvider) {
        this.utilizationProvider = utilizationProvider;
    }

    @Override
    public double calculate(RentalRequest request) {
        double utilization = Math.max(0.0, Math.min(1.0, utilizationProvider.currentUtilization()));
        double basePrice = request.days() * request.car().dailyRate();
        return basePrice * (1.0 + DEMAND_FACTOR * utilization);
    }
}