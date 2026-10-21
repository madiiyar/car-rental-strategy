package com.rental.pricing;

import com.rental.model.RentalRequest;

public class StandardPricing implements PricingStrategy {

    @Override
    public double calculate(RentalRequest request) {
        return request.days() * request.car().dailyRate();
    }
}