package com.rental.pricing;

import com.rental.data.FleetUtilizationProvider;
import com.rental.model.RentalRequest;

public class PricingSelector {

    private static final double HIGH_DEMAND_THRESHOLD = 0.8;
    private static final int LONG_TERM_MIN_DAYS = 7;

    private final FleetUtilizationProvider utilizationProvider;
    private final PricingStrategy standard = new StandardPricing();
    private final PricingStrategy longTerm = new LongTermPricing();
    private final PricingStrategy demand;

    public PricingSelector(FleetUtilizationProvider utilizationProvider) {
        this.utilizationProvider = utilizationProvider;
        this.demand = new DemandPricing(utilizationProvider);
    }

    public PricingStrategy select(RentalRequest request) {
        if (utilizationProvider.currentUtilization() > HIGH_DEMAND_THRESHOLD) {
            return demand;
        }
        if (request.days() >= LONG_TERM_MIN_DAYS) {
            return longTerm;
        }
        return standard;
    }
}